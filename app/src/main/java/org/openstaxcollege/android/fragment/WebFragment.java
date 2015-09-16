package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.NoteEditorActivity;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.utils.OSCUtil;
import org.openstaxcollege.android.views.ObservableWebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by ew2 on 9/2/15.
 */
public class WebFragment extends Fragment
{
    /** Web browser view for Activity */
    private ObservableWebView webView;
    /** Variable for serialized Content object */
    private Content content;

    private float yPosition = 0f;

    private boolean progressBarRunning;

    View view;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        activity.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        view = inflater.inflate(R.layout.webview, container, false);

        activity.setProgressBarIndeterminateVisibility(true);
        //Intent intent = activity.getIntent();
        content = (Content) getArguments().getSerializable(getString(R.string.webcontent));

        String contentURL = content.getUrl().toString();
        //url from home screen
        if(contentURL.contains("/content/col"))
        {

            SharedPreferences sharedPref = activity.getSharedPreferences(getString(R.string.osc_package), activity.MODE_PRIVATE);
            String url = sharedPref.getString(content.getIcon(), "");
            if(!url.equals(""))
            {
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
        if(content != null && content.getUrl() != null)
        {
            setLayout(content.getUrl().toString());
        }
        else
        {
            setLayout(getString(R.string.mobile_url));
        }

        if(OSCUtil.isConnected(getActivity()))
        {
            setUpViews();

        }
        else
        {
            webView = (ObservableWebView)view.findViewById(R.id.web_view);
            OSCUtil.makeNoDataToast(activity);
        }
        return view;
    }

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
                //activity.setProgressBarIndeterminateVisibility(true);
            }
            view.loadUrl(url);
            try
            {
                content.setUrl(new URL(url));

            }
            catch (MalformedURLException e)
            {
                Log.d("WVA.shouldOverrideUr...", e.toString(), e);
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

            String newURL = fixURL(url);
            content.setTitle(view.getTitle());
            try
            {
                content.setUrl(new URL(newURL));

            }
            catch (MalformedURLException e)
            {
                Log.d("WVA.onPageFinished()", e.toString(),e);
            }

            setLayout(newURL);
            //activity.setProgressBarIndeterminateVisibility(false);
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
                Log.d("WVClient.onPageFinished", "cookie: " + cookie);
                String[] cookieArray = Pattern.compile(";").split(cookie);
                for(int i = 0;i < cookieArray.length;i++)
                {
                    String c = cookieArray[i];
                    if(c.contains("viewed_cols"))
                    {
                        int colIndex = c.indexOf('"');
                        int plus = c.indexOf("+");
                        col = c.substring(colIndex + 1,plus);
                        int versionIndex = c.indexOf("+",plus + 1);
                        version = c.substring(plus + 1,versionIndex);
                        //Log.d("WebViewClient.fixURL()", "collection: " + col);
                        //Log.d("WebViewClient.fixURL()", "version: " + version);
                        newURL = url + "?collection=" + col + "/" + version;

                    }

                }

            }

            //Log.d("WebViewClient.fixURL()", "newURL: " + newURL);
            return newURL;
        }

    };

    /** sets properties on WebView and loads selected content into browser. */
    private void setUpViews()
    {
        if(content == null || content.url == null)
        {
            return;
        }

        //Log.d("WebViewView.setupViews()", "Called");
        webView = (ObservableWebView)view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultFontSize(17);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback(){
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
                Toast.makeText(getActivity(), getString(R.string.gingerbread_copy_msg), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), getString(R.string.froyo_copy_msg), Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e)
        {
            Log.e("dd", "Exception in emulateShiftHeld()", e);
        }

    }

    private void hideToolbar()
    {
        RelativeLayout relLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout1);
        int visibility = relLayout.getVisibility();
        if(visibility == View.VISIBLE)
        {
            relLayout.setVisibility(View.GONE);
        }
    }

    private void showToolbar()
    {
        RelativeLayout relLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout1);
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
        RelativeLayout relLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout1);
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


            ImageButton noteButton = (ImageButton)view.findViewById(R.id.noteButton);
            noteButton.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View v)
                {
                    Intent noteintent = new Intent(activity, NoteEditorActivity.class);
                    noteintent.putExtra(getString(R.string.webcontent),content);
                    startActivity(noteintent);
                }
            });

            ImageButton shareButton = (ImageButton)view.findViewById(R.id.shareButton);
            shareButton.setOnClickListener(new View.OnClickListener()
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
                        Toast.makeText(activity, getString(R.string.no_data_msg),  Toast.LENGTH_LONG).show();
                    }

                }
            });

            ImageButton copyButton = (ImageButton)view.findViewById(R.id.copyButton);
            if(Build.VERSION.SDK_INT < 11)
            {
                copyButton.setOnClickListener(new View.OnClickListener()
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
