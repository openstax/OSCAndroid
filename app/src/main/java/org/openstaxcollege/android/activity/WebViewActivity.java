/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.*;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.logic.WebviewLogic;
import org.openstaxcollege.android.utils.MenuUtil;
import org.openstaxcollege.android.utils.OSCUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Toast;

import java.io.File;


/**
 * Activity to view selected content in a web browser.
 * 
 * @author Ed Woodward
 *
 */
public class WebViewActivity extends AppCompatActivity
{
    /** Web browser view for Activity */
    private WebView webView;
    /** Variable for serialized Content object */
    private Content content;

    private static final String[] STORAGE_PERMS={
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int REQUEST = 1336;
    

    /** inner class for WebViewClient*/
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);

            //Log.d("WebViewClient.onLoadResource()", "Called");
        }

        /** loads URL into view */
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {

            view.loadUrl(url);

            content.setUrl(url);

            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url=request.getUrl().toString();

            view.loadUrl(url);

            content.setUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient.onPageFinished", "url: " + url);

            content.setTitle(view.getTitle());
            if(!url.contains(getString(R.string.minimal_url_snippet)))
            {
                url = url + getString(R.string.minimal_url_snippet);
                view.loadUrl(url);
            }

            content.setUrl(url);

        }

    };

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 24) {
            getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.app_name_html), Html.FROM_HTML_MODE_LEGACY));
        } else {
            getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.app_name_html)));
        }

        Intent intent = getIntent();
        content = (Content)intent.getSerializableExtra(getString(R.string.webcontent));


        if(!content.getUrl().contains(getString(R.string.bookmarks_url_snippet)))
        {

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package), MODE_PRIVATE);
            String url = sharedPref.getString(content.getIcon(), "");

            if(!url.equals(""))
            {
                url = convertURL(url);

                content.setUrl(url);

            }
        }
        else
        {
            //remove bookmark parameter
            String newURL = content.getUrl().replace(getString(R.string.bookmarks_url_snippet),"");
            //Log.d("onCreate","url: " + newURL);
            content.setUrl(newURL);
            Content bookTitle = OSCUtil.getTitle(content.getBookTitle(), this);
            content.setBookUrl(bookTitle.getBookUrl());

        }

        if(OSCUtil.isConnected(this))
        {
            setUpViews();

        }
        else
        {
            webView = (WebView)findViewById(R.id.web_view);
            OSCUtil.makeNoDataToast(this);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        if(content == null)
        {
            return false;
        }

        menu.clear();
        inflater.inflate(R.menu.web_options_menu, menu);

        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) 
    {
        super.onPrepareOptionsMenu(menu);
        //handle changing menu based on URL
        return onCreateOptionsMenu(menu);
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final WebViewActivity activity = this;
    	if(item.getItemId() == android.R.id.home)
        {
            Intent mainIntent = new Intent(getApplicationContext(), BookshelfActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(mainIntent);
            return true;
        }
        else if(item.getItemId() == R.id.download)
        {
            if(Build.VERSION.SDK_INT < 23)
            {
                displayDownloadAlert(content);

            }
            else
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    displayDownloadAlert(content);
                }
                else if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Snackbar.make(webView, getString(R.string.pdf_download_request), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.ok_button), new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            ActivityCompat.requestPermissions(activity, STORAGE_PERMS, REQUEST);
                        }
                    }).show();
                }
                else
                {
                    ActivityCompat.requestPermissions(this,STORAGE_PERMS, REQUEST);

                }
            }
        }
    	else
    	{
            try
            {

                WebviewLogic wl = new WebviewLogic();
                content.setBookTitle(wl.getBookTitle(webView.getTitle()));
                content.setTitle(webView.getTitle().replace(" - " + content.getBookTitle() + getString(R.string.cnx_title_snippet),""));
                content.setUrl(webView.getUrl());
                Content bookUrlContent = OSCUtil.getTitle(content.getBookTitle(), this);
                content.setBookUrl(bookUrlContent.getBookUrl());
                //Log.d("webviewl", "book url: " + content.getBookUrl());

            }
            catch(Exception mue)
            {
                Log.d("WVMenu","Error: " + mue.toString());
            }
	        MenuHandler mh = new MenuHandler();
	        return mh.handleContextMenu(item, this, content);
    	}
        return true;
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            displayDownloadAlert(content);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(webView != null && ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()))
        {
            webView.goBack();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {        
        super.onConfigurationChanged(newConfig);
    }
    
    
    @Override
    protected void onResume()
    {
        super.onResume();
        //SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package),MODE_PRIVATE);
        //String url = sharedPref.getString(content.getIcon(), "");
        //Log.d("WebViewActivity.onResume()","URL retrieved: " + url);


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package),MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        //Log.d("WVA.onPause()","URL saved: " + content.getUrl().toString());
        if(webView != null && content != null)
        {
            if(webView.getUrl() != null)
            {
                String url = webView.getUrl().replace(getString(R.string.bookmarks_url_snippet), "");
                ed.putString(content.getIcon(), url);
                ed.apply();
            }
        }
    }

    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.webcontent),content);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package),MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        if(webView != null && content != null && webView.getUrl() != null)
        {
            String url = webView.getUrl().replace(getString(R.string.bookmarks_url_snippet), "");
            ed.putString(content.getIcon(), url);
            ed.apply();
        }
        
    }
    
    /** sets properties on WebView and loads selected content into browser. */
    private void setUpViews() 
    {
        if(content == null || content.getUrl().equals(""))
        {
            return;
        }
        
        //Log.d("WebViewView.setupViews()", "Called");
        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultFontSize(17);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS); 
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content.getUrl());
    }

    /**
     * Converts Legacy style url to rewrite style
     * Removes m. from the url since that no longer exists
     * but could be book marked
     * @param url - String - the url to convert
     */
    private String convertURL(String url)
    {

        if(url.contains("/content/"))
        {
            return url.replace("//m.","//");
        }
        else
        {
            return url;
        }
    }

    /**
     * Displays alert telling user where the downloaded files are located, the size of the files to download and confirms download.
     * If download is confirmed, DownloadHandler is called.
     * @param currentContent - Content - current content object
     */
    //@TODO Move download logic to logic class
    private void displayDownloadAlert(final Content currentContent)
    {
        final Context context = this;

        String message = getString(R.string.pdf_download_message);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.download));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

                if(OSCUtil.isConnected(context))
                {

                    File cnxDir = new File(Environment.getExternalStorageDirectory(), "OpenStax/");
                    if(!cnxDir.exists())
                    {
                        cnxDir.mkdir();
                    }
                    DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    WebviewLogic wl = new WebviewLogic();
                    //Log.d("WeviewLogic", "title: " + currentContent.getBookTitle());
                    String pdfUrl = wl.getPDFUrl(currentContent.getBookTitle());

                    if(pdfUrl == null || pdfUrl.equals(""))
                    {
                        Toast.makeText(context, getString(R.string.pdf_url_missing),  Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Log.d("Webview","PDF URL: " + pdfUrl);
                        Uri uri = Uri.parse(pdfUrl);

                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + MenuUtil.getTitle(currentContent.getBookTitle()) + ".pdf");
                        request.setTitle(currentContent.getBookTitle() + ".pdf");
                        try
                        {
                            dm.enqueue(request);
                        }
                        catch(IllegalArgumentException iae)
                        {
                            createDialog(context);
                        }
                    }
                }
                else
                {
                    OSCUtil.makeNoDataToast(context);
                }




            } });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing

            } });
        alertDialog.show();
    }

    private void enableDownloadManager(Context context)
    {
        try
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:com.android.providers.downloads"));
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }
    }

    private void createDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.download_manager))
                .setMessage(getString(R.string.download_mgr_missing))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enableDownloadManager(context);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setCancelable(true)
                .create()
                .show();
    }

}
