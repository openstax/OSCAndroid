/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.*;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.utils.OSCUtil;
import org.openstaxcollege.android.views.ObservableWebView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

/**
 * Activity to view selected content in a web browser.
 * 
 * @author Ed Woodward
 *
 */
public class WebViewActivity extends Activity
{
    /** Web browser view for Activity */
    private ObservableWebView webView;
    /** Variable for serialized Content object */
    private Content content;

    private boolean progressBarRunning;
    
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
                content.setUrl(new URL(url));

            }
            catch (MalformedURLException e)
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
                //content.setUrl(new URL(url));
                content.setUrl(new URL(view.getUrl()));

            }
            catch (MalformedURLException e)
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

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        //Log.d("LensWebView.onCreate()", "Called");

        setContentView(R.layout.new_web_view);
        ActionBar aBar = this.getActionBar();
        setProgressBarIndeterminateVisibility(true);
        aBar.setDisplayHomeAsUpEnabled(true);
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
                        content.setUrl(new URL(url));
                    }
                    catch(MalformedURLException mue)
                    {
                        Log.e("WViewActivity.onResume", mue.toString());
                    }
                }
            }
            else
            {
                //remove bookmark parameter
                String newURL = content.getUrl().toString().replace("?bookmark=1","");
                content.setUrl(new URL(newURL));

            }
        }
        catch(MalformedURLException mue)
        {
            Log.e("WViewActivity.onResume", mue.toString());
        }

        aBar.setTitle(Html.fromHtml(getString(R.string.app_name_html)));

        if(OSCUtil.isConnected(this))
        {
            setUpViews();

        }
        else
        {
            webView = (ObservableWebView)findViewById(R.id.web_view);
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
    	if(item.getItemId() == android.R.id.home)
        {
            Intent mainIntent = new Intent(getApplicationContext(), LandingActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            return true;
        }
    	else
    	{
            try
            {

                content.setTitle(webView.getTitle().replace(" - " + content.getBookTitle() + " - OpenStax CNX",""));
                content.setUrl(new URL(webView.getUrl()));

            }
            catch(MalformedURLException mue)
            {

            }
	        MenuHandler mh = new MenuHandler();
	        return mh.handleContextMenu(item, this, content);
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
                content.setUrl(new URL(url));
            }
            catch(MalformedURLException mue)
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
        String url = webView.getUrl().replace("?bookmark=1","");
        ed.putString(content.getIcon(), url);
        ed.apply();
    }

    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.webcontent),content);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.osc_package),MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        String url = webView.getUrl().replace("?bookmark=1","");
        ed.putString(content.getIcon(), url);
        ed.apply();
        
    }
    
    /** sets properties on WebView and loads selected content into browser. */
    private void setUpViews() 
    {
        if(content == null || content.url == null)
        {
            return;
        }
        
        //Log.d("WebViewView.setupViews()", "Called");
        webView = (ObservableWebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultFontSize(17);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS); 

        webView.setWebChromeClient(new WebChromeClient() 
        {
            

        });
        
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content.url.toString());
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
}
