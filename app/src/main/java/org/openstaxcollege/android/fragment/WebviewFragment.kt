package org.openstaxcollege.android.fragment

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.*
import android.webkit.*
import android.widget.Toast
import org.openstaxcollege.android.R
import org.openstaxcollege.android.activity.BookshelfActivity
import org.openstaxcollege.android.activity.WebViewActivity
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.handlers.MenuHandler
import org.openstaxcollege.android.logic.WebviewLogic
import org.openstaxcollege.android.tasks.FetchPdfUrlTask
import org.openstaxcollege.android.utils.MenuUtil
import org.openstaxcollege.android.utils.OSCUtil
import java.io.File

class WebviewFragment : Fragment(), FetchPdfUrlTask.PdfTaskCallback
{
    /** Web browser view for Activity  */
    private var webView: WebView? = null
    /** Variable for serialized Content object  */
    private var content: Content? = null
    private val REQUEST = 1336

    private var saveLocation = true

    private val DEVELOPER_MODE = true

    /** inner class for WebViewClient */
    private val webViewClient = object : WebViewClient()
    {
        override fun onLoadResource(view: WebView, url: String)
        {
            super.onLoadResource(view, url)

            //Log.d("WebViewClient.onLoadResource()", "Called");
        }

        /** loads URL into view  */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean
        {

            //Log.d("WebviewCliet","url: " + url);
            if(!url.contains("cnx.org"))
            {
                //open url in a  browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
            else
            {

                view.loadUrl(url)

                content!!.url = url
            }

            return true
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean
        {
            val url = request.url.toString()

            //Log.d("WebviewCliet2","url: " + url);
            if(!url.contains("cnx.org"))
            {
                //open url in a  browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
            else
            {

                view.loadUrl(url)

                content!!.url = url
            }

            return true
        }

        override fun onPageFinished(view: WebView, url: String)
        {
            var url = url
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient", "url: " + url);

            content!!.title = view.title
            if(url.contains("cnx.org"))
            {
                if(!url.contains(getString(R.string.minimal_url_snippet)))
                {
                    url = url + getString(R.string.minimal_url_snippet)
                    view.loadUrl(url)
                }

                content!!.url = url
            }

        }

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.web_view)
        //val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //if(Build.VERSION.SDK_INT >= 24)
        //{
        //    supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html), Html.FROM_HTML_MODE_LEGACY)
        //}
        //else
        //{
        //    supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html))
        //}

        val intent = getActivity().getIntent()
        content = intent.getSerializableExtra(getString(R.string.webcontent)) as Content


        if(!content!!.url.contains(getString(R.string.bookmarks_url_snippet)))
        {

            val sharedPref = getActivity().getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
            var url = sharedPref.getString(content!!.icon, "")

            if(url != "")
            {
                url = convertURL(url!!)

                content!!.url = url

            }
        }
        else
        {
            //remove bookmark parameter
            val newURL = content!!.url.replace(getString(R.string.bookmarks_url_snippet), "")
            //Log.d("onCreate","url: " + newURL);
            content!!.url = newURL
            val bookTitle = OSCUtil.getTitle(content!!.bookTitle, getActivity())
            if(bookTitle != null)
            {
                content!!.bookUrl = bookTitle.bookUrl
            }

        }


        if(OSCUtil.isConnected(getActivity()))
        {
            setUpViews()
            FetchPdfUrlTask(content!!.bookTitle, this).execute()

        }
        else
        {
            webView = this.view?.findViewById(R.id.web_view)
            OSCUtil.makeNoDataToast(getActivity())
        }
        return inflater!!.inflate(R.layout.web_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

    }

    fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = activity.menuInflater
        if(content == null)
        {
            return false
        }

        menu.clear()
        inflater.inflate(R.menu.web_options_menu, menu)

        return true
    }

//    override fun onPrepareOptionsMenu(menu: Menu): Unit
//    {
//        super.onPrepareOptionsMenu(menu)
//        //handle changing menu based on URL
//        return onCreateOptionsMenu(menu)
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val activity = this
        if(item.itemId == android.R.id.home)
        {
            val mainIntent = Intent(activity.context, BookshelfActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(mainIntent)
            return true
        }
        else if(item.itemId == R.id.download)
        {
            if(Build.VERSION.SDK_INT < 23)
            {
                displayDownloadAlert(content)

            }
            else
            {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    displayDownloadAlert(content)
                }
                else if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Snackbar.make(webView!!, getString(R.string.pdf_download_request), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.ok_button)) { ActivityCompat.requestPermissions(getActivity(), WebViewActivity.STORAGE_PERMS, REQUEST) }.show()
                }
                else
                {
                    ActivityCompat.requestPermissions(getActivity(), WebViewActivity.STORAGE_PERMS, REQUEST)

                }
            }
        }
        else if(item.itemId == R.id.remove_location)
        {
            //Log.d("menuItem", "removing location: " + content.getIcon());
            val sharedPref = getActivity().getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
            val ed = sharedPref.edit()
            ed.remove(content!!.icon)
            ed.commit()
            saveLocation = false
            Toast.makeText(getActivity(), "Saved location removed", Toast.LENGTH_LONG).show()
        }
        else
        {
            try
            {

                val wl = WebviewLogic()
                content!!.bookTitle = wl.getBookTitle(webView!!.title)
                content!!.title = webView!!.title.replace(" - " + content!!.bookTitle + getString(R.string.cnx_title_snippet), "")
                content!!.url = webView!!.url
                val bookUrlContent = OSCUtil.getTitle(content!!.bookTitle, getActivity())
                content!!.bookUrl = bookUrlContent.bookUrl
                //Log.d("webview2", "book url: " + content.getBookUrl());

            } catch(mue: Exception)
            {
                Log.d("WVMenu", "Error: $mue")
            }

            val mh = MenuHandler()
            return mh.handleContextMenu(item, getActivity(), content)
        }
        return true

    }

    /**
     * callback for PDF URL retrieval
     * @param result
     */
    override fun onResultReceived(result: String)
    {
        //Log.d("PDF TASK RESULT", result);
        content!!.pdfUrl = result
    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {

        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            displayDownloadAlert(content)
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
//    {
//        if(webView != null && keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack())
//        {
//            webView!!.goBack()
//            return true
//
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration)
//    {
//        super.onConfigurationChanged(newConfig)
//    }


    override fun onResume()
    {
        super.onResume()
        saveLocation = true

    }

    override fun onPause()
    {
        //Log.d("onPause", "savedLocation: " + saveLocation);
        super.onPause()
        val sharedPref = getActivity().getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
        val ed = sharedPref.edit()
        //Log.d("WVA.onPause()","URL saved: " + content.getUrl().toString());
        if(webView != null && content != null)
        {
            if(webView!!.url != null)
            {
                if(saveLocation && webView!!.url.contains("cnx.org"))
                {
                    //Log.d("onPause()", "saving data");
                    val url = webView!!.url.replace(getString(R.string.bookmarks_url_snippet), "")
                    ed.putString(content!!.icon, url)
                    ed.apply()
                }

            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.webcontent), content)
        val sharedPref =getActivity(). getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
        val ed = sharedPref.edit()
        if(webView != null && content != null && webView!!.url != null)
        {
            if(webView!!.url.contains("cnx.org") && saveLocation)
            {
                //Log.d("onSaveInstanceState()", "saving data");
                val url = webView!!.url.replace(getString(R.string.bookmarks_url_snippet), "")
                ed.putString(content!!.icon, url)
                ed.apply()
            }

        }

    }

    /** sets properties on WebView and loads selected content into browser.  */
    private fun setUpViews()
    {
        if(content == null || content!!.url == "")
        {
            return
        }

        //Log.d("WebViewView.setupViews()", "Called");
        webView = this.view?.findViewById<View>(R.id.web_view) as WebView
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.defaultFontSize = 17
        webView!!.settings.setSupportZoom(true)
        webView!!.settings.builtInZoomControls = true
        webView!!.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webView!!.webChromeClient = WebChromeClient()
        webView!!.webViewClient = webViewClient
        webView!!.loadUrl(content!!.url)
    }

    /**
     * Converts Legacy style url to rewrite style
     * Removes m. from the url since that no longer exists
     * but could be book marked
     * @param url - String - the url to convert
     */
    private fun convertURL(url: String): String
    {

        return if(url.contains("/content/"))
        {
            url.replace("//m.", "//")
        }
        else
        {
            url
        }
    }

    /**
     * Displays alert telling user where the downloaded files are located, the size of the files to download and confirms download.
     * If download is confirmed, DownloadHandler is called.
     * @param currentContent - Content - current content object
     */
    //@TODO Move download logic to logic class
    private fun displayDownloadAlert(currentContent: Content?)
    {
        val context = this

        val message = getString(R.string.pdf_download_message)

        val alertDialog = AlertDialog.Builder(getContext()).create()
        alertDialog.setTitle(getString(R.string.download))
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, which ->
            if(OSCUtil.isConnected(getContext()))
            {

                val cnxDir = File(Environment.getExternalStorageDirectory(), "OpenStax/")
                if(!cnxDir.exists())
                {
                    cnxDir.mkdir()
                }

                val dm = getActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                //Log.d("WeviewLogic","title: "+currentContent.getBookTitle());
                val pdfUrl = content!!.pdfUrl

                if(pdfUrl == null || pdfUrl == "")
                {
                    makePDFToast()
                }
                else
                {
                    //Log.d("Webview","PDF URL: " + pdfUrl);
                    val uri = Uri.parse(pdfUrl)

                    val request = DownloadManager.Request(uri)
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + MenuUtil.getTitle(currentContent!!.bookTitle) + ".pdf")
                    request.setTitle(currentContent.bookTitle + ".pdf")
                    request.setDescription("Downloading " + currentContent.bookTitle + ".pdf")
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    try
                    {
                        dm.enqueue(request)
                    } catch(iae: IllegalArgumentException)
                    {
                        createDialog(getContext())
                    }

                }

            }
            else
            {
                OSCUtil.makeNoDataToast(getContext())
            }
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)) { dialog, which ->
            //do nothing
        }
        alertDialog.show()
    }

    private fun enableDownloadManager(context: Context)
    {
        try
        {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:com.android.providers.downloads")
            context.startActivity(intent)
        } catch(e: ActivityNotFoundException)
        {
            val intent = Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            context.startActivity(intent)
        }

    }

    private fun createDialog(context: Context)
    {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.download_manager))
                .setMessage(getString(R.string.download_mgr_missing))
                .setPositiveButton(getString(R.string.ok)) { dialog, which -> enableDownloadManager(context) }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    //do nothing
                }
                .setCancelable(true)
                .create()
                .show()
    }

    private fun makePDFToast()
    {
        Toast.makeText(context, getString(R.string.pdf_url_missing), Toast.LENGTH_LONG).show()
    }

    companion object
    {

        private val STORAGE_PERMS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}