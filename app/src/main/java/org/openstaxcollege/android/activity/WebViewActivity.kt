/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.*
import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.handlers.MenuHandler
import org.openstaxcollege.android.logic.WebviewLogic
import org.openstaxcollege.android.tasks.FetchPdfUrlTask
import org.openstaxcollege.android.utils.MenuUtil
import org.openstaxcollege.android.utils.OSCUtil

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings.LayoutAlgorithm
import android.widget.Toast
import org.openstaxcollege.android.fragment.WebviewFragment

import java.io.File


/**
 * Activity to view selected content in a web browser.
 *
 * @author Ed Woodward
 */
class WebViewActivity : AppCompatActivity()
{
    private var content: Content? = null
    private val REQUEST = 1336

    private var saveLocation = true

    private val DEVELOPER_MODE = true




    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if(Build.VERSION.SDK_INT >= 24)
        {
            supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html), Html.FROM_HTML_MODE_LEGACY)
        }
        else
        {
            supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html))
        }

        if (savedInstanceState == null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = WebviewFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean
//    {
//        val inflater = menuInflater
//        if(content == null)
//        {
//            return false
//        }
//
//        menu.clear()
//        inflater.inflate(R.menu.web_options_menu, menu)
//
//        return true
//    }
//
//    override fun onPrepareOptionsMenu(menu: Menu): Boolean
//    {
//        super.onPrepareOptionsMenu(menu)
//        //handle changing menu based on URL
//        return onCreateOptionsMenu(menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean
//    {
//        val activity = this
//        if(item.itemId == android.R.id.home)
//        {
//            val mainIntent = Intent(applicationContext, BookshelfActivity::class.java)
//            mainIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//            startActivity(mainIntent)
//            return true
//        }
//        else if(item.itemId == R.id.download)
//        {
//            if(Build.VERSION.SDK_INT < 23)
//            {
//                displayDownloadAlert(content)
//
//            }
//            else
//            {
//                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
//                {
//                    displayDownloadAlert(content)
//                }
//                else if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                {
//                    Snackbar.make(webView!!, getString(R.string.pdf_download_request), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.ok_button)) { ActivityCompat.requestPermissions(activity, STORAGE_PERMS, REQUEST) }.show()
//                }
//                else
//                {
//                    ActivityCompat.requestPermissions(this, STORAGE_PERMS, REQUEST)
//
//                }
//            }
//        }
//        else if(item.itemId == R.id.remove_location)
//        {
//            //Log.d("menuItem", "removing location: " + content.getIcon());
//            val sharedPref = getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
//            val ed = sharedPref.edit()
//            ed.remove(content!!.icon)
//            ed.commit()
//            saveLocation = false
//            Toast.makeText(this, "Saved location removed", Toast.LENGTH_LONG).show()
//        }
//        else
//        {
//            try
//            {
//
//                val wl = WebviewLogic()
//                content!!.bookTitle = wl.getBookTitle(webView!!.title)
//                content!!.title = webView!!.title.replace(" - " + content!!.bookTitle + getString(R.string.cnx_title_snippet), "")
//                content!!.url = webView!!.url
//                val bookUrlContent = OSCUtil.getTitle(content!!.bookTitle, this)
//                content!!.bookUrl = bookUrlContent.bookUrl
//                //Log.d("webview2", "book url: " + content.getBookUrl());
//
//            } catch(mue: Exception)
//            {
//                Log.d("WVMenu", "Error: $mue")
//            }
//
//            val mh = MenuHandler()
//            return mh.handleContextMenu(item, this, content)
//        }
//        return true
//
//    }

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

    override fun onConfigurationChanged(newConfig: Configuration)
    {
        super.onConfigurationChanged(newConfig)
    }


    override fun onResume()
    {
        super.onResume()
        saveLocation = true

    }

//    override fun onPause()
//    {
//        //Log.d("onPause", "savedLocation: " + saveLocation);
//        super.onPause()
//        val sharedPref = getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
//        val ed = sharedPref.edit()
//        //Log.d("WVA.onPause()","URL saved: " + content.getUrl().toString());
//        if(webView != null && content != null)
//        {
//            if(webView!!.url != null)
//            {
//                if(saveLocation && webView!!.url.contains("cnx.org"))
//                {
//                    //Log.d("onPause()", "saving data");
//                    val url = webView!!.url.replace(getString(R.string.bookmarks_url_snippet), "")
//                    ed.putString(content!!.icon, url)
//                    ed.apply()
//                }
//
//            }
//        }
//    }


//    override fun onSaveInstanceState(outState: Bundle)
//    {
//        super.onSaveInstanceState(outState)
//        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
//        outState.putSerializable(getString(R.string.webcontent), content)
//        val sharedPref = getSharedPreferences(getString(R.string.osc_package), Context.MODE_PRIVATE)
//        val ed = sharedPref.edit()
//        if(webView != null && content != null && webView!!.url != null)
//        {
//            if(webView!!.url.contains("cnx.org") && saveLocation)
//            {
//                //Log.d("onSaveInstanceState()", "saving data");
//                val url = webView!!.url.replace(getString(R.string.bookmarks_url_snippet), "")
//                ed.putString(content!!.icon, url)
//                ed.apply()
//            }
//
//        }
//
//    }



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

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.download))
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, which ->
            if(OSCUtil.isConnected(context))
            {

                val cnxDir = File(Environment.getExternalStorageDirectory(), "OpenStax/")
                if(!cnxDir.exists())
                {
                    cnxDir.mkdir()
                }

                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
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
                        createDialog(context)
                    }

                }

            }
            else
            {
                OSCUtil.makeNoDataToast(context)
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
        Toast.makeText(this, getString(R.string.pdf_url_missing), Toast.LENGTH_LONG).show()
    }

    companion object
    {

        val STORAGE_PERMS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

}
