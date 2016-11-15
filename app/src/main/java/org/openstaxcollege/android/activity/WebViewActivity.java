/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

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

    private boolean progressBarRunning;

    private static final String[] STORAGE_PERMS={
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int REQUEST = 1336;
    
    /**
     * keeps track of the previous menu for when the back button is used.
     */

    /** inner class for WebViewClient*/
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);

            //Log.d("WebViewClient.onLoadResource()", "Called");
        }

        /** loads URL into view */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
        	//Log.d("WebViewClient.shouldOverrideUrlLo()", "Called");
        	if(!progressBarRunning)
            {
            	setProgressBarIndeterminateVisibility(true);
            }

            view.loadUrl(url);
            try
            {
                content.setUrl(url);

            }
            catch (Exception e)
            {
                Log.d("WVA.shouldOverrideUr...", e.toString(),e);
            }
            return true;
        }

        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
         * Sets title and URL correctly after the page is fully loaded
         */
        @Override
        public void onPageFinished(WebView view, String url)
        {
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient.onPageFinished", "url: " + url);

            content.setTitle(view.getTitle());
            if(!url.contains("?minimal=true"))
            {
                url = url + "?minimal=true";
                view.loadUrl(url);
            }
            try
            {
                content.setUrl(url);

            }
            catch (Exception e)
            {
                Log.d("WVA.onPageFinished()", e.toString(),e);
            }

            setProgressBarIndeterminateVisibility(false);
            progressBarRunning = false;
            //Log.d("WebViewClient.onPageFinished", "setSupportProgressBarIndeterminateVisibility(false) Called");

        }

    };
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.app_name_html)));;

        
        Intent intent = getIntent();
        content = (Content)intent.getSerializableExtra(getString(R.string.webcontent));

        try
        {
            if(!content.getUrl().toString().contains("?bookmark=1"))
            {

                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package), MODE_PRIVATE);
                String url = sharedPref.getString(content.getIcon(), "");

                if(!url.equals(""))
                {
                    url = convertURL(url);
                    try
                    {
                        content.setUrl(url);
                    }
                    catch(Exception mue)
                    {
                        Log.e("WViewActivity.onResume", mue.toString());
                    }
                }
            }
            else
            {
                //remove bookmark parameter
                String newURL = content.getUrl().replace("?bookmark=1","");
                content.setUrl(newURL);

            }
        }
        catch(Exception mue)
        {
            Log.e("WViewActivity.onResume", mue.toString());
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
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     * Creates option menu
     */
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
    
    /* (non-Javadoc)
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) 
    {
        super.onPrepareOptionsMenu(menu);
        //handle changing menu based on URL
        return onCreateOptionsMenu(menu);
    }

    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * Handles selected options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final WebViewActivity activity = this;
    	if(item.getItemId() == android.R.id.home)
        {
            Intent mainIntent = new Intent(getApplicationContext(), LandingActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(mainIntent);
            return true;
        }
        else if(item.getItemId() == R.id.download)
        {
            if((int) Build.VERSION.SDK_INT < 23)
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
                            ActivityCompat.requestPermissions(activity,STORAGE_PERMS, REQUEST);
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
                content.setTitle(webView.getTitle().replace(" - " + content.getBookTitle() + " - OpenStax CNX",""));
                content.setUrl(webView.getUrl());

            }
            catch(Exception mue)
            {

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
    
    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * Handles use of back button on browser 
     */
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
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     * added to handle orientation change.  Not sure why this is needed, but it is.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {        
        super.onConfigurationChanged(newConfig);
    }
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package),MODE_PRIVATE);
        String url = sharedPref.getString(content.getIcon(), "");
        //Log.d("WebViewActivity.onResume()","URL retrieved: " + url);
        if(!url.equals(""))
        {
            url = convertURL(url);
            try
            {
                //content.setUrl(new URL(url));
            }
            catch(Exception mue)
            {
                Log.e("WViewActivity.onResume",mue.toString());
            }
        }

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
            String url = webView.getUrl().replace("?bookmark=1", "");
            ed.putString(content.getIcon(), url);
            ed.apply();
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
        if(webView != null && content != null)
        {
            String url = webView.getUrl().replace("?bookmark=1", "");
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

        webView.setWebChromeClient(new WebChromeClient() 
        {
            

        });
        
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content.getUrl());
    }

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
    private void displayDownloadAlert(final Content currentContent)
    {

        String message = "PDF files are saved in an OpenStax folder on the SDCard or on the device's internal memory.  Press OK to continue.";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Download");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                File cnxDir = new File(Environment.getExternalStorageDirectory(), "OpenStax/");
                if(!cnxDir.exists())
                {
                    cnxDir.mkdir();
                }
                DownloadManager dm = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                WebviewLogic wl = new WebviewLogic();
                Log.d("WeviewLogic", "title: " + currentContent.getBookTitle());
                String pdfUrl = wl.getPDFUrl(currentContent.getBookTitle());

                Uri uri = Uri.parse(pdfUrl);

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDestinationInExternalPublicDir("/" + getString(R.string.folder_name), MenuUtil.getTitle(currentContent.getBookTitle()) + ".pdf");
                request.setTitle(currentContent.getBookTitle() + ".pdf");
                dm.enqueue(request);




            } });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing

            } });
        alertDialog.show();
    }
}
