/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.activity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.BooksAdapter;
import org.openstaxcollege.android.adapters.ImageAdapter;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.utils.ContentCache;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

//import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity to view list of available Lens types. 
 * 
 * @author Ed Woodward
 *
 */
//public class LandingActivity extends SherlockListActivity 
public class LandingActivity extends SherlockActivity 
{
   
   /** Adaptor for Lens list display */ 
    BooksAdapter adapter;
    /** list of lenses as Content objects */ 
    ArrayList<Content> content;
    
    private ActionBar aBar;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.list_view);
        setContentView(R.layout.gridview);
        //registerForContextMenu(getListView());
        createList();
        aBar = this.getSupportActionBar();
        aBar.setTitle("OpenStax College");
        aBar.setDisplayHomeAsUpEnabled(false);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
            	if(position > 4)
            	{
            		Toast.makeText(LandingActivity.this, "Coming Soon!",  Toast.LENGTH_SHORT).show();
            		return;
            	}
            	Content c = content.get(position);
            	ContentCache.setObject(getString(R.string.webcontent), c);
            	startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
                
            }
        });
        //get already retrieved feed and reuse if it is there
//        content = (ArrayList<Content>)getLastNonConfigurationInstance();
//        if(content==null && savedInstanceState != null)
//        {
//            //Log.d("ViewLenses.onCreate()", "getting saved data");
//            content = (ArrayList<Content>)savedInstanceState.getSerializable(getString(R.string.cache_lenstypes));
//        }
//        if(content == null)
//        {
//            createList();
//        }
//       
//        fillData(content);
//        finishedLoadingList();
//            
//        setListAdapter(adapter);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.lenses_options_menu, menu);
        return true;
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        MenuHandler mh = new MenuHandler();
        boolean returnVal = mh.handleContextMenu(item, this, null);
        if(returnVal)
        {
            return returnVal;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
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
    
    /* (non-Javadoc)
     * Handles selection of an item in the Lenses list
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    //@Override
//    protected void onListItemClick(ListView l, View v, int position, long id) 
//    {
//    	
//        Content content = (Content)getListView().getItemAtPosition(position);
//        if(content.getUrl().toString().equals(getString(R.string.lenses_fake_url)))
//        {
//        	return;
//        }
//        ContentCache.setObject(getString(R.string.webcontent), content);
//        startActivity(new Intent(this, WebViewActivity.class));
//    }
    
    /** Actions after list is loaded in View*/
//    protected void finishedLoadingList() 
//    {
//        setListAdapter(adapter);
//        getListView().setSelection(0);
//        getListView().setSaveEnabled(true);
//        getListView().setClickable(true);
//    }
    
    /**
     * Loads data into list view
     * @param contentList ArrayList<Content>
     */
    private void fillData(ArrayList<Content> contentList) 
    {
        //Log.d("LensViewer", "fillData() called");
        adapter = new BooksAdapter(LandingActivity.this, contentList);
    }
    
    /**
     * Create objects for Lens type list
     */
    private void createList()
    {
        String fakeURL = getString(R.string.lenses_fake_url);
        try
        {
            Content c = new Content();
            c.setTitle(getString(R.string.physics));
            c.setContentString(getString(R.string.physics_desc));
            c.setUrl(new URL("http://m.cnx.org/content/col11406/latest"));
            c.setIconDrawable(R.drawable.physics_lg);
            c.setIcon(Integer.toString(R.drawable.physics_lg));
            
            Content c2 = new Content();
            c2.setTitle(getString(R.string.sociology));
            c2.setContentString(getString(R.string.sociology_desc));
            c2.setUrl(new URL("http://m.cnx.org/content/col11407/latest/"));
            c2.setIconDrawable(R.drawable.sociology_lg);
            c2.setIcon(Integer.toString(R.drawable.sociology_lg));
            
            Content c3 = new Content();
            c3.setTitle(getString(R.string.biology));
            c3.setContentString(getString(R.string.biology_desc));
            c3.setUrl(new URL("http://m.cnx.org/content/col11448/latest/"));
            c3.setIconDrawable(R.drawable.biology_lg);
            c3.setIcon(Integer.toString(R.drawable.biology_lg));
            
            Content c4 = new Content();
            c4.setTitle(getString(R.string.concepts_biology));
            c4.setContentString(getString(R.string.concepts_biology_desc));
            c4.setUrl(new URL("http://m.cnx.org/content/col11487/latest/"));
            c4.setIconDrawable(R.drawable.concepts_biology_lg);
            c4.setIcon(Integer.toString(R.drawable.concepts_biology_lg));
            
            Content c5 = new Content();
            c5.setTitle(getString(R.string.anatomy));
            c5.setContentString(getString(R.string.anatomy_desc));
            c5.setUrl(new URL("http://m.cnx.org/content/col11496/latest/"));
            c5.setIconDrawable(R.drawable.anatomy_lg);
            c5.setIcon(Integer.toString(R.drawable.anatomy_lg));
            
            Content c6 = new Content();
            c6.setTitle(getString(R.string.statistics));
            c6.setContentString(getString(R.string.coming_soon));
            c6.setUrl(new URL(fakeURL));
            c6.setIconDrawable(R.drawable.statistics_lg);
            
            Content c7 = new Content();
            c7.setTitle(getString(R.string.econ));
            c7.setContentString(getString(R.string.coming_soon));
            c7.setUrl(new URL(fakeURL));
            c7.setIconDrawable(R.drawable.econ_lg);
            
            Content c8 = new Content();
            c8.setTitle(getString(R.string.precalculus));
            c8.setContentString(getString(R.string.coming_soon));
            c8.setUrl(new URL(fakeURL));
            c8.setIconDrawable(R.drawable.precalculus_lg);
            
            Content c9 = new Content();
            c9.setTitle(getString(R.string.chemistry));
            c9.setContentString(getString(R.string.coming_soon));
            c9.setUrl(new URL(fakeURL));
            c9.setIconDrawable(R.drawable.chemistry_lg);
            
            Content c10 = new Content();
            c10.setTitle(getString(R.string.history));
            c10.setContentString(getString(R.string.coming_soon));
            c10.setUrl(new URL(fakeURL));
            c10.setIconDrawable(R.drawable.history_lg);
            
            Content c11 = new Content();
            c11.setTitle(getString(R.string.macro_econ));
            c11.setContentString(getString(R.string.coming_soon));
            c11.setUrl(new URL(fakeURL));
            c11.setIconDrawable(R.drawable.macro_econ_lg);
            
            Content c12 = new Content();
            c12.setTitle(getString(R.string.micro_econ));
            c12.setContentString(getString(R.string.coming_soon));
            c12.setUrl(new URL(fakeURL));
            c12.setIconDrawable(R.drawable.micro_econ_lg);
            
            Content c13 = new Content();
            c13.setTitle(getString(R.string.psychology));
            c13.setContentString(getString(R.string.coming_soon));
            c13.setUrl(new URL(fakeURL));
            c13.setIconDrawable(R.drawable.psychology_lg);
            
            if(content == null)
            {
                content = new ArrayList<Content>();
            }
            
            content.add(c);
            content.add(c2);
            content.add(c3);
            content.add(c4);
            content.add(c5);
            content.add(c6);
            content.add(c7);
            content.add(c8);
            content.add(c9);
            content.add(c10);
            content.add(c11);
            content.add(c12);
            content.add(c13);
            
        }
        catch (MalformedURLException e)
        {
            Log.d("LandingActivity.createList()", "Error: " + e.toString(),e);
        }
        
    }
    
}
