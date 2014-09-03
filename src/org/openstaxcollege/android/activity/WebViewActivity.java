/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.view.*;
import android.webkit.CookieManager;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.utils.OSCUtil;
//import org.openstaxcollege.android.utils.ContentCache;
import org.openstaxcollege.android.views.ObservableWebView;
import org.openstaxcollege.android.views.ObservableWebView.OnScrollChangedCallback;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle; 
import android.util.Log;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Activity to view selected lens content in a web browser.  
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
    /** Constant for serialized object passed to Activity */
    public static final String WEB_MENU = "web";
    public static final String HELP_MENU = "help";
    
    private ActionBar aBar;
    
    private float yPosition = 0f;
    
    private boolean progressBarRunning;
    
    /**
     * Progress bar when page is loading
     */
    private ProgressDialog progressBar;
    
    /**
     * keeps track of the previous menu for when the back button is used.
     */
    private String previousMenu =  "";
    
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
                Log.d("WebViewActivity.shouldOverrideUrlLoading()", "Error: " + e.toString(),e);
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
            Log.d("WebViewClient.onPageFinished", "url: " + url);

            String newURL = fixURL(url);
            content.setTitle(view.getTitle());
            try
            {
                content.setUrl(new URL(newURL));
                
            }
            catch (MalformedURLException e)
            {
                Log.d("WebViewActivity.onPageFinished()", "Error: " + e.toString(),e);
            }
            
            setLayout(newURL);
            setProgressBarIndeterminateVisibility(false);
            progressBarRunning = false;
            //Log.d("WebViewClient.onPageFinished", "setSupportProgressBarIndeterminateVisibility(false) Called");
            yPosition = 0f;

        }

        private String fixURL(String url)
        {
            //check for collection id
            int index = url.indexOf("/col");
            if(index == -1)
            {
                index = url.indexOf("=col");
            }
            String col;
            String version;
            String newURL = url;

            //if none found, get cookie and add to URL
            if(index == -1)
            {
                String cookie = CookieManager.getInstance().getCookie("m.cnx.org");
                Log.d("WebViewClient.onPageFinished", "cookie: " + cookie);
                String[] cookieArray = Pattern.compile(";").split(cookie);
                for(int i = 0;i < cookieArray.length;i++)
                {
                    String c = cookieArray[i];
                    if(c.indexOf("viewed_cols") > -1)
                    {
                        int colIndex = c.indexOf('"');
                        int plus = c.indexOf("+");
                        col = c.substring(colIndex + 1,plus);
                        int versionIndex = c.indexOf("+",plus + 1);
                        version = c.substring(plus + 1,versionIndex);
                        Log.d("WebViewClient.fixURL()", "collection: " + col);
                        Log.d("WebViewClient.fixURL()", "version: " + version);
                        newURL = url + "?collection=" + col + "/" + version;

                    }

                }

            }

            Log.d("WebViewClient.fixURL()", "newURL: " + newURL);
            return newURL;
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
        aBar = this.getActionBar();
        setProgressBarIndeterminateVisibility(true);
        aBar.setDisplayHomeAsUpEnabled(true);
        //content = (Content)ContentCache.getObject(getString(R.string.webcontent));
        Intent intent = getIntent();
        content = (Content)intent.getSerializableExtra(getString(R.string.webcontent));

//        SharedPreferences sharedPref = getSharedPreferences("org.openstaxcollege.android",MODE_PRIVATE);
//        String url = sharedPref.getString(content.getIcon(),"");
//        if(!url.equals(""))
//        {
//            try {
//                content.setUrl(new URL(url));
//            }
//            catch(MalformedURLException mue)
//            {
//                Log.e("WebViewActivity.onResume","Error: " + mue.toString());
//            }
//        }
        aBar.setTitle(getString(R.string.app_name));
        if(content != null && content.getUrl() != null)
        {
            setLayout(content.getUrl().toString());
        }
        else
        {
            setLayout(getString(R.string.mobile_url));
        }
        
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
        previousMenu = WEB_MENU;

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
	        MenuHandler mh = new MenuHandler();
	        boolean returnVal = mh.handleContextMenu(item, this, content);
//	        if(returnVal)
//	        {
	            return returnVal;
//	        }
//	        else
//	        {
//	            return super.onOptionsItemSelected(item);
//	        }
    	}
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * Handles use of back button on browser 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(webView != null)
        {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;

            }
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
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        SharedPreferences sharedPref = getSharedPreferences("org.openstaxcollege.android",MODE_PRIVATE);
//        String url = sharedPref.getString(content.getIcon(),"");
//        Log.d("WebViewActivity.onResume()","URL retrieved: " + url);
//        if(!url.equals(""))
//        {
//            try {
//                content.setUrl(new URL(url));
//            }
//            catch(MalformedURLException mue)
//            {
//                Log.e("WebViewActivity.onResume","Error: " + mue.toString());
//            }
//        }
//
//    }

//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        SharedPreferences sharedPref = getSharedPreferences("org.openstaxcollege.android",MODE_PRIVATE);
//        SharedPreferences.Editor ed = sharedPref.edit();
//        Log.d("WebViewActivity.onPause()","URL saved: " + content.getUrl().toString());
//        ed.putString(content.getIcon(), content.getUrl().toString());
//        ed.commit();
//    }

    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        //ContentCache.setObject(getString(R.string.webcontent), content);
        outState.putSerializable(getString(R.string.webcontent),content);
        SharedPreferences sharedPref = getSharedPreferences("org.openstaxcollege.android",MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(content.getIcon(), content.getUrl().toString());
        ed.commit();
        
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
        webView.setOnScrollChangedCallback(new OnScrollChangedCallback(){
            public void onScroll(int l, int t)
            {
            	
            	String url = content.getUrl().toString();
            	float newY = webView.getScrollY();
                //Log.d("WebViewActivity", "newY: " +newY);
                //Log.d("WebViewActivity", "yPosition: " +yPosition);
            	if(url.contains(getString(R.string.search)) || url.contains(getString(R.string.html_ext)))
                {
            		hideToolbar();
                }
                else if(newY >= yPosition)
               {
              	 //hide layout
              	 hideToolbar();
               }
               else
               {
              	 //show toolbar
              	 showToolbar();
               }
               yPosition = newY;
            }
         });
        
        webView.setWebChromeClient(new WebChromeClient() 
        {
            

        });
        
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content.url.toString());
    }        
    
    private void emulateShiftHeld(WebView view)
    {
        try
        {
            KeyEvent shiftPressEvent = new KeyEvent(0, 0, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT, 0, 0);
            shiftPressEvent.dispatch(view);
            if(Build.VERSION.SDK_INT == 10) 
            {
                Toast.makeText(this, getString(R.string.gingerbread_copy_msg), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, getString(R.string.froyo_copy_msg), Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e)
        {
            Log.e("dd", "Exception in emulateShiftHeld()", e);
        }

    }
    
    private void hideToolbar()
    {
    	RelativeLayout relLayout = (RelativeLayout)findViewById(R.id.relativeLayout1);
        int visibility = relLayout.getVisibility();
        if(visibility == View.VISIBLE)
        {
            relLayout.setVisibility(View.GONE);
        }
    }
    
    private void showToolbar()
    {
    	RelativeLayout relLayout = (RelativeLayout)findViewById(R.id.relativeLayout1);
        int visibility = relLayout.getVisibility();
        if(visibility == View.GONE)
        {
            relLayout.setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * Hides or displays the action bar based on URL.
     * Should be hidden is search or help is displayed.
     * @param url - URL used to determine if action bar should be displayed.
     */
    private void setLayout(String url)
    {
        RelativeLayout relLayout = (RelativeLayout)findViewById(R.id.relativeLayout1);
        int visibility = relLayout.getVisibility();
        if(url.contains(getString(R.string.search)) || url.contains(getString(R.string.html_ext)))
        {
            if(visibility == View.VISIBLE)
            {
                relLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            if(visibility == View.GONE)
            {
                relLayout.setVisibility(View.VISIBLE);
            }
            
                
                ImageButton noteButton = (ImageButton)findViewById(R.id.noteButton);
                noteButton.setOnClickListener(new OnClickListener() 
                {
                          
                      public void onClick(View v) 
                      {
                          Intent noteintent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                          //ContentCache.setObject(getString(R.string.content), content);
                          noteintent.putExtra(getString(R.string.webcontent),content);
                          startActivity(noteintent);
                      }
                  });
                
                ImageButton shareButton = (ImageButton)findViewById(R.id.shareButton);
                shareButton.setOnClickListener(new OnClickListener() 
                {
                          
                      public void onClick(View v) 
                      {
                          Intent intent = new Intent(Intent.ACTION_SEND);
                          intent.setType(getString(R.string.mimetype_text));

                          if(content != null)
                          {
                              intent.putExtra(Intent.EXTRA_SUBJECT, content.getTitle());
                              intent.putExtra(Intent.EXTRA_TEXT, content.getUrl().toString() + " " + getString(R.string.shared_via));
    
                              Intent chooser = Intent.createChooser(intent, getString(R.string.tell_friend) + " "+ content.getTitle());
                              startActivity(chooser);
                          }
                          else
                          {
                              Toast.makeText(WebViewActivity.this, getString(R.string.no_data_msg),  Toast.LENGTH_LONG).show();
                          }

                      }
                  });
                
                ImageButton copyButton = (ImageButton)findViewById(R.id.copyButton);
                if(Build.VERSION.SDK_INT < 11) 
                {
                    copyButton.setOnClickListener(new OnClickListener() 
                    {
                              
                          public void onClick(View v) 
                          {
                              emulateShiftHeld(webView);

                          }
                      });
                }
                else
                {
                    copyButton.setVisibility(View.GONE);
                }

            
        }
    }
    
}
