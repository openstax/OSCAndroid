/**
 * Copyright (c) 2015 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.utils.OSCUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Fragment for displaying grid of book covers
 * @author Ed Woodward
 */
public class GridFragment extends Fragment
{
    /** list of lenses as Content objects */
    ArrayList<Content> content;

    OnBookSelectedListener bookListener;

    public interface OnBookSelectedListener
    {
        void onBookSelected(Content content);
    }


    public GridFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Activity activity = getActivity();
        createList();
        View v = inflater.inflate(R.layout.grid_fragment, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.gridView);
        int orient = getResources().getConfiguration().orientation;
        boolean isTablet = OSCUtil.isTabletDevice(activity);
        if(orient == Configuration.ORIENTATION_LANDSCAPE && isTablet)
        {
            if(OSCUtil.isXLarge(activity))
            {
                gridView.setNumColumns(5);
            }
            else
            {
                gridView.setNumColumns(4);
            }
        }
        else if(orient == Configuration.ORIENTATION_LANDSCAPE)
        {
            gridView.setNumColumns(3);
        }
        else if(orient == Configuration.ORIENTATION_PORTRAIT && isTablet)
        {

            if(OSCUtil.isXLarge(activity))
            {
                gridView.setNumColumns(4);
            }
            else
            {
                gridView.setNumColumns(3);
            }
        }

        gridView.setAdapter(new ImageAdapter(activity));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                //Log.d("GridFragment", "position: " + position);

                Content c = content.get(position);
                bookListener.onBookSelected(c);

            }
        });
        //return inflater.inflate(R.layout.grid_fragment, container, false);
        return v;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            bookListener = (OnBookSelectedListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnBookSelectedListener");
        }
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
            c.setBookTitle(getString(R.string.physics));
            c.setContentString(getString(R.string.physics_desc));
            //c.setUrl(new URL("http://archive.alpha.cnx.org:6543/contents/031da8d3-b525-429c-80cf-6c8ed997733a@7.31.html"));
            c.setUrl(new URL("http://cnx.org/contents/Ax2o07Ul:HR_VN3f7?minimal=true"));
            c.setIconDrawable(R.drawable.physics_lg);
            c.setIcon(getString(R.string.physics_icon));
            c.setBookURL("http://cnx.org/contents/Ax2o07Ul:HR_VN3f7?minimal=true");

            Content c2 = new Content();
            c2.setTitle(getString(R.string.sociology));
            c2.setBookTitle(getString(R.string.sociology));
            c2.setContentString(getString(R.string.sociology_desc));
            c2.setUrl(new URL("http://cnx.org/contents/AgQDEnLI:TrIRM88K?minimal=true"));
            c2.setIconDrawable(R.drawable.sociology_lg);
            c2.setIcon(getString(R.string.sociology_icon));
            c2.setBookURL("http://cnx.org/contents/AgQDEnLI:TrIRM88K?minimal=true");

            Content c3 = new Content();
            c3.setTitle(getString(R.string.biology));
            c3.setBookTitle(getString(R.string.biology));
            c3.setContentString(getString(R.string.biology_desc));
            c3.setUrl(new URL("http://cnx.org/contents/GFy_h8cu:rZudN6XP?minimal=true"));
            c3.setIconDrawable(R.drawable.biology_lg);
            c3.setIcon(getString(R.string.biology_icon));
            c3.setBookURL("http://cnx.org/contents/GFy_h8cu:rZudN6XP?minimal=true");

            Content c4 = new Content();
            c4.setTitle(getString(R.string.concepts_biology));
            c4.setBookTitle(getString(R.string.concepts_biology));
            c4.setContentString(getString(R.string.concepts_biology_desc));
            c4.setUrl(new URL("http://cnx.org/contents/s8Hh0oOc:Pj8cW7X1?minimal=true"));
            c4.setIconDrawable(R.drawable.concepts_biology_lg);
            c4.setIcon(getString(R.string.concepts_icon));
            c4.setBookURL("http://cnx.org/contents/s8Hh0oOc:Pj8cW7X1?minimal=true");

            Content c5 = new Content();
            c5.setTitle(getString(R.string.anatomy));
            c5.setBookTitle(getString(R.string.anatomy));
            c5.setContentString(getString(R.string.anatomy_desc));
            c5.setUrl(new URL("http://cnx.org/contents/FPtK1zmh:zMTtFGyH?minimal=true"));
            c5.setIconDrawable(R.drawable.anatomy_lg);
            c5.setIcon(getString(R.string.anatomy_icon));
            c5.setBookURL("http://cnx.org/contents/FPtK1zmh:zMTtFGyH?minimal=true");

            Content c6 = new Content();
            c6.setTitle(getString(R.string.statistics));
            c6.setBookTitle(getString(R.string.statistics));
            c6.setContentString(getString(R.string.statistics_desc));
            //c6.setUrl(new URL("http://cnx.org/contents/30189442-6998-4686-ac05-ed152b91b9de@16.5"));
            c6.setUrl(new URL("http://cnx.org/contents/MBiUQmmY:2T34_25K?minimal=true"));
            c6.setIconDrawable(R.drawable.statistics_lg);
            c6.setIcon(getString(R.string.statistics_icon));
            c6.setBookURL("http://cnx.org/contents/MBiUQmmY:2T34_25K?minimal=true");

            Content c7 = new Content();
            c7.setTitle(getString(R.string.econ));
            c7.setBookTitle(getString(R.string.econ));
            c7.setContentString(getString(R.string.coming_soon));
            c7.setUrl(new URL("http://cnx.org/contents/aWGdK2jw:JgDXaOLP?minimal=true"));
            c7.setIconDrawable(R.drawable.econ_lg);
            c7.setIcon(getString(R.string.econ_icon));
            c7.setBookURL("http://cnx.org/contents/aWGdK2jw:JgDXaOLP?minimal=true");

            Content c11 = new Content();
            c11.setTitle(getString(R.string.macro_econ));
            c11.setBookTitle(getString(R.string.macro_econ));
            c11.setContentString(getString(R.string.coming_soon));
            c11.setUrl(new URL("http://cnx.org/contents/QGHIMgmO:JgDXaOLP?minimal=true"));
            c11.setIconDrawable(R.drawable.macro_econ_lg);
            c11.setIcon(getString(R.string.macro_icon));
            c11.setBookURL("http://cnx.org/contents/QGHIMgmO:JgDXaOLP?minimal=true");

            Content c12 = new Content();
            c12.setTitle(getString(R.string.micro_econ));
            c12.setBookTitle(getString(R.string.micro_econ));
            c12.setContentString(getString(R.string.coming_soon));
            c12.setUrl(new URL("http://cnx.org/contents/6i8iXmBj:JgDXaOLP?minimal=true"));
            c12.setIconDrawable(R.drawable.micro_econ_lg);
            c12.setIcon(getString(R.string.micro_icon));
            c12.setBookURL("http://cnx.org/contents/6i8iXmBj:JgDXaOLP?minimal=true");

            Content c8 = new Content();
            c8.setTitle(getString(R.string.precalculus));
            c8.setBookTitle(getString(R.string.precalculus));
            c8.setContentString(getString(R.string.coming_soon));
            c8.setUrl(new URL("http://cnx.org/contents/_VPq4foj:vEOnJry_?minimal=true"));
            c8.setIconDrawable(R.drawable.precalculus_lg);
            c8.setIcon(getString(R.string.precalculus_icon));
            c8.setBookURL("http://cnx.org/contents/_VPq4foj:vEOnJry_?minimal=true");

            Content c13 = new Content();
            c13.setTitle(getString(R.string.psychology));
            c13.setBookTitle(getString(R.string.psychology));
            c13.setContentString(getString(R.string.coming_soon));
            c13.setUrl(new URL("http://cnx.org/contents/Sr8Ev5Og:6HoLG-TA?minimal=true"));
            c13.setIconDrawable(R.drawable.psychology_lg);
            c13.setIcon(getString(R.string.psychology_icon));
            c13.setBookURL("http://cnx.org/contents/Sr8Ev5Og:6HoLG-TA?minimal=true");

            Content c10 = new Content();
            c10.setTitle(getString(R.string.history));
            c10.setBookTitle(getString(R.string.history));
            c10.setContentString(getString(R.string.coming_soon));
            c10.setUrl(new URL("http://cnx.org/contents/p7ovuIkl:gMXC1GEM?minimal=true"));
            c10.setIconDrawable(R.drawable.history_lg);
            c10.setIcon(getString(R.string.history_icon));
            c10.setBookURL("http://cnx.org/contents/p7ovuIkl:gMXC1GEM?minimal=true");

            Content c9 = new Content();
            c9.setTitle(getString(R.string.chemistry));
            c9.setBookTitle(getString(R.string.chemistry));
            c9.setContentString(getString(R.string.coming_soon));
            c9.setUrl(new URL("http://cnx.org/contents/havxkyvS:uXg0kUa-?minimal=true"));
            c9.setIconDrawable(R.drawable.chemistry_lg);
            c9.setIcon(getString(R.string.chemistry_icon));
            c9.setBookURL("http://cnx.org/contents/havxkyvS:uXg0kUa-?minimal=true");

            Content c14 = new Content();
            c14.setTitle(getString(R.string.algebra));
            c14.setBookTitle(getString(R.string.algebra));
            c14.setContentString(getString(R.string.coming_soon));
            c14.setUrl(new URL("http://cnx.org/contents/mwjClAV_:nU8Qkzwo?minimal=true"));
            c14.setIconDrawable(R.drawable.algebra_lg);
            c14.setIcon(getString(R.string.algebra_icon));
            c14.setBookURL("http://cnx.org/contents/mwjClAV_:nU8Qkzwo?minimal=true");

            Content c15 = new Content();
            c15.setTitle(getString(R.string.trig));
            c15.setBookTitle(getString(R.string.trig));
            c15.setContentString(getString(R.string.coming_soon));
            c15.setUrl(new URL("http://cnx.org/contents/E6wQevFf@:nU8Qkzwo?minimal=true"));
            c15.setIconDrawable(R.drawable.trig_lg);
            c15.setIcon(getString(R.string.trig_icon));
            c15.setBookURL("http://cnx.org/contents/E6wQevFf@:nU8Qkzwo?minimal=true");

            Content c16 = new Content();
            c16.setTitle(getString(R.string.ap_physics));
            c16.setBookTitle(getString(R.string.ap_physics));
            c16.setContentString(getString(R.string.coming_soon));
            c16.setUrl(new URL("http://cnx.org/contents/jQSmhtXo:6pB5TgBD?minimal=true"));
            c16.setIconDrawable(R.drawable.ap_physics_lg);
            c16.setIcon(getString(R.string.ap_physics_icon));
            c16.setBookURL("http://cnx.org/contents/jQSmhtXo:6pB5TgBD?minimal=true");

            Content c17 = new Content();
            c17.setTitle(getString(R.string.ap_macro));
            c17.setBookTitle(getString(R.string.ap_macro));
            c17.setContentString(getString(R.string.coming_soon));
            c17.setUrl(new URL("http://cnx.org/contents/MwdgVOwd:CSCWn8lc?minimal=true"));
            c17.setIconDrawable(R.drawable.ap_macro);
            c17.setIcon(getString(R.string.ap_macro_icon));
            c17.setBookURL("http://cnx.org/contents/MwdgVOwd:CSCWn8lc?minimal=true");

            Content c18 = new Content();
            c18.setTitle(getString(R.string.ap_micro));
            c18.setBookTitle(getString(R.string.ap_micro));
            c18.setContentString(getString(R.string.coming_soon));
            c18.setUrl(new URL("http://cnx.org/contents/yjROLWcx:CSCWn8lc?minimal=true"));
            c18.setIconDrawable(R.drawable.ap_micro);
            c18.setIcon(getString(R.string.ap_micro_icon));
            c18.setBookURL("http://cnx.org/contents/yjROLWcx:CSCWn8lc?minimal=true");

            if(content == null)
            {
                content = new ArrayList<>();
            }

            content.add(c);
            content.add(c2);
            content.add(c3);
            content.add(c4);
            content.add(c5);
            content.add(c6);
            content.add(c7);
            content.add(c11);
            content.add(c12);
            content.add(c8);
            content.add(c13);
            content.add(c10);
            content.add(c9);
            content.add(c14);
            content.add(c15);
            content.add(c16);
            content.add(c17);
            content.add(c18);

        }
        catch (MalformedURLException e)
        {
            Log.d("Landing.createList()", "Error: " + e.toString(), e);
        }

    }

    class ImageAdapter extends BaseAdapter
    {
        private Context context;

        List<Bookcover> bookcovers = new ArrayList<>();

        public ImageAdapter(Context c)
        {
            context = c;

            bookcovers.add(new Bookcover("", R.drawable.physics_lg));
            bookcovers.add(new Bookcover("", R.drawable.sociology_lg));
            bookcovers.add(new Bookcover("", R.drawable.biology_lg));
            bookcovers.add(new Bookcover("", R.drawable.concepts_biology_lg));
            bookcovers.add(new Bookcover("", R.drawable.anatomy_lg));
            bookcovers.add(new Bookcover("", R.drawable.statistics_lg));
            bookcovers.add(new Bookcover("", R.drawable.econ_lg));
            bookcovers.add(new Bookcover("", R.drawable.macro_econ_lg));
            bookcovers.add(new Bookcover("", R.drawable.micro_econ_lg));
            bookcovers.add(new Bookcover("", R.drawable.precalculus_lg));
            bookcovers.add(new Bookcover("", R.drawable.psychology_lg));
            bookcovers.add(new Bookcover("", R.drawable.history_lg));
            bookcovers.add(new Bookcover("", R.drawable.chemistry_lg));
            bookcovers.add(new Bookcover("", R.drawable.algebra_lg));
            bookcovers.add(new Bookcover("", R.drawable.trig_lg));
            bookcovers.add(new Bookcover("", R.drawable.ap_physics_lg));
            bookcovers.add(new Bookcover("", R.drawable.ap_macro));
            bookcovers.add(new Bookcover("", R.drawable.ap_micro));




        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return bookcovers.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position)
        {
            return bookcovers.get(position);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            View v = convertView;
            ImageView picture;

            if(v == null) {

                v = LayoutInflater.from(context).inflate(R.layout.gridcell, parent, false);
                v.setTag(R.id.grid_item_image, v.findViewById(R.id.grid_item_image));
            }

            picture = (ImageView)v.getTag(R.id.grid_item_image);

            Bookcover item = (Bookcover)getItem(position);

            picture.setImageResource(item.drawableId);

            return v;
        }

    }

    private class Bookcover
    {

        final String name;
        final int drawableId;

        Bookcover(String name, int drawableId)
        {
            this.name = name;
            this.drawableId = drawableId;
        }


    }
}
