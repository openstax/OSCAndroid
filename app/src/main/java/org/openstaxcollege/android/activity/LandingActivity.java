/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.text.Html;
import android.view.*;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.fragment.GridFragment;
import org.openstaxcollege.android.handlers.MenuHandler;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;

/**
 * Activity to view list of OSC books available. 
 * 
 * @author Ed Woodward
 *
 */
public class LandingActivity extends Activity implements GridFragment.OnBookSelectedListener
{
   
    /** list of lenses as Content objects */
    ArrayList<Content> content;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ActionBar aBar = getActionBar();
        aBar.setTitle(Html.fromHtml(getString(R.string.app_name_html)));

        aBar.setDisplayHomeAsUpEnabled(false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        GridFragment fragment = new GridFragment();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    public void onBookSelected(Content content)
    {

        Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
        i.putExtra(getString(R.string.webcontent),content);
        startActivity(i);


    }

    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.lenses_options_menu, menu);
        return true;
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        MenuHandler mh = new MenuHandler();
        return mh.handleContextMenu(item, this, null);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.cache_lenstypes), content);
        
    }
    
}
