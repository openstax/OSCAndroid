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
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.text.Html;
import android.view.*;
import android.widget.*;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.fragment.GridFragment;
import org.openstaxcollege.android.fragment.WebFragment;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.utils.OSCUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;

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
    
    private ActionBar aBar;

    public static final String GRIDFRAG = "GridFragment";

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        aBar = getActionBar();
        aBar.setTitle(Html.fromHtml(getString(R.string.app_name_html)));

        aBar.setDisplayHomeAsUpEnabled(false);
        if(getFragmentManager().findFragmentByTag(GRIDFRAG) == null)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            GridFragment fragment = new GridFragment();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        }
    }

    public void onBookSelected(Content content)
    {
        Bundle bundles = new Bundle();
        bundles.putSerializable("webcontent", content);
        WebFragment wf = new WebFragment();
        wf.setArguments(bundles);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFragment,wf);
        transaction.addToBackStack(null);
        transaction.commit();


    }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gridview);
//        createList();
//        aBar = getActionBar();
//        aBar.setTitle(Html.fromHtml(getString(R.string.app_name_html)));
//
//        aBar.setDisplayHomeAsUpEnabled(false);
//        GridView gridView = (GridView) findViewById(R.id.gridView);
//        int orient = getResources().getConfiguration().orientation;
//        Display d = getWindowManager().getDefaultDisplay();
//        boolean isTablet = OSCUtil.isTabletDevice(this);
//        if(orient == Configuration.ORIENTATION_LANDSCAPE && isTablet)
//        {
//            if(OSCUtil.isXLarge(this))
//            {
//                gridView.setNumColumns(5);
//            }
//            else
//            {
//        	    gridView.setNumColumns(4);
//            }
//        }
//        else if(orient == Configuration.ORIENTATION_LANDSCAPE)
//        {
//        	gridView.setNumColumns(3);
//        }
//        else if(orient == Configuration.ORIENTATION_PORTRAIT && isTablet)
//        {
//
//            if(OSCUtil.isXLarge(this))
//            {
//                gridView.setNumColumns(4);
//            }
//            else
//            {
//                gridView.setNumColumns(3);
//            }
//        }
//
//        gridView.setAdapter(new ImageAdapter(this));
//        gridView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
//                Log.d("LandingActivity","position: " + position);
//
//            	Content c = content.get(position);
//                Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
//                i.putExtra("webcontent",c);
//            	startActivity(i);
//
//            }
//        });
    //}
    
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
    
    /**
     * Create objects for Lens type list
     */
//    private void createList()
//    {
//        String fakeURL = getString(R.string.lenses_fake_url);
//        try
//        {
//            Content c = new Content();
//            c.setTitle(getString(R.string.physics));
//            c.setContentString(getString(R.string.physics_desc));
//            //c.setUrl(new URL("http://archive.alpha.cnx.org:6543/contents/031da8d3-b525-429c-80cf-6c8ed997733a@7.31.html"));
//            c.setUrl(new URL("http://m.cnx.org/content/col11406/latest"));
//            c.setIconDrawable(R.drawable.physics_lg);
//            c.setIcon("physics");
//
//            Content c2 = new Content();
//            c2.setTitle(getString(R.string.sociology));
//            c2.setContentString(getString(R.string.sociology_desc));
//            c2.setUrl(new URL("http://m.cnx.org/content/col11407/latest/"));
//            c2.setIconDrawable(R.drawable.sociology_lg);
//            c2.setIcon("sociology");
//
//            Content c3 = new Content();
//            c3.setTitle(getString(R.string.biology));
//            c3.setContentString(getString(R.string.biology_desc));
//            c3.setUrl(new URL("http://m.cnx.org/content/col11448/latest/"));
//            c3.setIconDrawable(R.drawable.biology_lg);
//            c3.setIcon("biology");
//
//            Content c4 = new Content();
//            c4.setTitle(getString(R.string.concepts_biology));
//            c4.setContentString(getString(R.string.concepts_biology_desc));
//            c4.setUrl(new URL("http://m.cnx.org/content/col11487/latest/"));
//            c4.setIconDrawable(R.drawable.concepts_biology_lg);
//            c4.setIcon("concepts");
//
//            Content c5 = new Content();
//            c5.setTitle(getString(R.string.anatomy));
//            c5.setContentString(getString(R.string.anatomy_desc));
//            c5.setUrl(new URL("http://m.cnx.org/content/col11496/latest/"));
//            c5.setIconDrawable(R.drawable.anatomy_lg);
//            c5.setIcon("anatomy");
//
//            Content c6 = new Content();
//            c6.setTitle(getString(R.string.statistics));
//            c6.setContentString(getString(R.string.statistics_desc));
//            //c6.setUrl(new URL("http://cnx.org/contents/30189442-6998-4686-ac05-ed152b91b9de@16.5"));
//            c6.setUrl(new URL("http://m.cnx.org/content/col11562/latest/"));
//            c6.setIconDrawable(R.drawable.statistics_lg);
//            c6.setIcon("statistics");
//
//            Content c7 = new Content();
//            c7.setTitle(getString(R.string.econ));
//            c7.setContentString(getString(R.string.coming_soon));
//            c7.setUrl(new URL("http://m.cnx.org/content/col11613/latest/"));
//            c7.setIconDrawable(R.drawable.econ_lg);
//            c7.setIcon("econ");
//
//            Content c11 = new Content();
//            c11.setTitle(getString(R.string.macro_econ));
//            c11.setContentString(getString(R.string.coming_soon));
//            c11.setUrl(new URL("http://m.cnx.org/content/col11626/latest/"));
//            c11.setIconDrawable(R.drawable.macro_econ_lg);
//            c11.setIcon("macro");
//
//            Content c12 = new Content();
//            c12.setTitle(getString(R.string.micro_econ));
//            c12.setContentString(getString(R.string.coming_soon));
//            c12.setUrl(new URL("http://m.cnx.org/content/col11627/latest/"));
//            c12.setIconDrawable(R.drawable.micro_econ_lg);
//            c12.setIcon("micro");
//
//            Content c8 = new Content();
//            c8.setTitle(getString(R.string.precalculus));
//            c8.setContentString(getString(R.string.coming_soon));
//            c8.setUrl(new URL("http://m.cnx.org/content/col11667/latest/"));
//            c8.setIconDrawable(R.drawable.precalculus_lg);
//            c8.setIcon("precalculus");
//
//            Content c13 = new Content();
//            c13.setTitle(getString(R.string.psychology));
//            c13.setContentString(getString(R.string.coming_soon));
//            c13.setUrl(new URL("http://m.cnx.org/content/col11629/latest/"));
//            c13.setIconDrawable(R.drawable.psychology_lg);
//            c13.setIcon("psychology");
//
//            Content c10 = new Content();
//            c10.setTitle(getString(R.string.history));
//            c10.setContentString(getString(R.string.coming_soon));
//            c10.setUrl(new URL("http://m.cnx.org/content/col11740/latest/"));
//            c10.setIconDrawable(R.drawable.history_lg);
//            c10.setIcon("history");
//
//            Content c9 = new Content();
//            c9.setTitle(getString(R.string.chemistry));
//            c9.setContentString(getString(R.string.coming_soon));
//            c9.setUrl(new URL("http://m.cnx.org/content/col11760/latest/"));
//            c9.setIconDrawable(R.drawable.chemistry_lg);
//            c9.setIcon("chemistry");
//
//            Content c14 = new Content();
//            c14.setTitle(getString(R.string.algebra));
//            c14.setContentString(getString(R.string.coming_soon));
//            c14.setUrl(new URL("http://m.cnx.org/content/col11759/latest/"));
//            c14.setIconDrawable(R.drawable.algebra_lg);
//            c14.setIcon("algebra");
//
//            Content c15 = new Content();
//            c15.setTitle(getString(R.string.trig));
//            c15.setContentString(getString(R.string.coming_soon));
//            c15.setUrl(new URL("http://m.cnx.org/content/col11758/latest/"));
//            c15.setIconDrawable(R.drawable.trig_lg);
//            c15.setIcon("trig");
//
//            Content c16 = new Content();
//            c16.setTitle(getString(R.string.ap_physics));
//            c16.setContentString(getString(R.string.coming_soon));
//            c16.setUrl(new URL("http://m.cnx.org/content/col11844/latest/"));
//            c16.setIconDrawable(R.drawable.ap_physics_lg);
//            c16.setIcon("ap-physics");
//
//
//
//            if(content == null)
//            {
//                content = new ArrayList<Content>();
//            }
//
//            content.add(c);
//            content.add(c2);
//            content.add(c3);
//            content.add(c4);
//            content.add(c5);
//            content.add(c6);
//            content.add(c7);
//            content.add(c11);
//            content.add(c12);
//            content.add(c8);
//            content.add(c13);
//            content.add(c10);
//            content.add(c9);
//            content.add(c14);
//            content.add(c15);
//            content.add(c16);
//
//        }
//        catch (MalformedURLException e)
//        {
//            Log.d("Landing.createList()", "Error: " + e.toString(),e);
//        }
//
//    }

//    class ImageAdapter extends BaseAdapter
//    {
//    	private Context context;
//
//    	List<Bookcover> bookcovers = new ArrayList<Bookcover>();
//
//    	public ImageAdapter(Context c)
//    	{
//    		context = c;
//
//            bookcovers.add(new Bookcover("",R.drawable.physics_lg));
//            bookcovers.add(new Bookcover("",R.drawable.sociology_lg));
//            bookcovers.add(new Bookcover("", R.drawable.biology_lg));
//            bookcovers.add(new Bookcover("",R.drawable.concepts_biology_lg));
//            bookcovers.add(new Bookcover("",R.drawable.anatomy_lg));
//            bookcovers.add(new Bookcover("",R.drawable.statistics_lg));
//            bookcovers.add(new Bookcover("",R.drawable.econ_lg));
//            bookcovers.add(new Bookcover("",R.drawable.macro_econ_lg));
//            bookcovers.add(new Bookcover("",R.drawable.micro_econ_lg));
//            bookcovers.add(new Bookcover("",R.drawable.precalculus_lg));
//            bookcovers.add(new Bookcover("",R.drawable.psychology_lg));
//            bookcovers.add(new Bookcover("",R.drawable.history_lg));
//            bookcovers.add(new Bookcover("",R.drawable.chemistry_lg));
//            bookcovers.add(new Bookcover("",R.drawable.algebra_lg));
//            bookcovers.add(new Bookcover("",R.drawable.trig_lg));
//            bookcovers.add(new Bookcover("",R.drawable.ap_physics_lg));
//
//
//
//
//    	}
//
//    	/* (non-Javadoc)
//    	 * @see android.widget.Adapter#getCount()
//    	 */
//    	@Override
//    	public int getCount()
//    	{
//    		return bookcovers.size();
//    	}
//
//    	/* (non-Javadoc)
//    	 * @see android.widget.Adapter#getItem(int)
//    	 */
//    	@Override
//    	public Object getItem(int position)
//    	{
//    		return bookcovers.get(position);
//    	}
//
//    	/* (non-Javadoc)
//    	 * @see android.widget.Adapter#getItemId(int)
//    	 */
//    	@Override
//    	public long getItemId(int position)
//    	{
//    		return 0;
//    	}
//
//    	/* (non-Javadoc)
//    	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
//    	 */
//    	@Override
//    	public View getView(int position, View convertView, ViewGroup parent)
//    	{
//
//    		View v = convertView;
//            ImageView picture;
//
//            if(v == null) {
//
//                v = LayoutInflater.from(context).inflate(R.layout.gridcell, parent, false);
//                v.setTag(R.id.grid_item_image, v.findViewById(R.id.grid_item_image));
//            }
//
//            picture = (ImageView)v.getTag(R.id.grid_item_image);
//
//            Bookcover item = (Bookcover)getItem(position);
//
//            picture.setImageResource(item.drawableId);
//
//            return v;
//    	}
//
//    }
//
//    private class Bookcover
//    {
//
//        final String name;
//        final int drawableId;
//
//        Bookcover(String name, int drawableId)
//        {
//            this.name = name;
//            this.drawableId = drawableId;
//        }
//
//
//    }
    
}
