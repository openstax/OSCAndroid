package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
 * A placeholder fragment containing a simple view.
 */
public class GridFragment extends Fragment
{
    /** list of lenses as Content objects */
    ArrayList<Content> content;

    OnBookSelectedListener bookListener;

    // Container Activity must implement this interface
    public interface OnBookSelectedListener
    {
        public void onBookSelected(Content content);
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
                Log.d("LandingActivity", "position: " + position);

                Content c = content.get(position);
                bookListener.onBookSelected(c);
//                Intent i = new Intent(activity, WebViewActivity.class);
//                i.putExtra("webcontent", c);
//                startActivity(i);

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
            c.setContentString(getString(R.string.physics_desc));
            //c.setUrl(new URL("http://archive.alpha.cnx.org:6543/contents/031da8d3-b525-429c-80cf-6c8ed997733a@7.31.html"));
            c.setUrl(new URL("http://m.cnx.org/content/col11406/latest"));
            c.setIconDrawable(R.drawable.physics_lg);
            c.setIcon("physics");

            Content c2 = new Content();
            c2.setTitle(getString(R.string.sociology));
            c2.setContentString(getString(R.string.sociology_desc));
            c2.setUrl(new URL("http://m.cnx.org/content/col11407/latest/"));
            c2.setIconDrawable(R.drawable.sociology_lg);
            c2.setIcon("sociology");

            Content c3 = new Content();
            c3.setTitle(getString(R.string.biology));
            c3.setContentString(getString(R.string.biology_desc));
            c3.setUrl(new URL("http://m.cnx.org/content/col11448/latest/"));
            c3.setIconDrawable(R.drawable.biology_lg);
            c3.setIcon("biology");

            Content c4 = new Content();
            c4.setTitle(getString(R.string.concepts_biology));
            c4.setContentString(getString(R.string.concepts_biology_desc));
            c4.setUrl(new URL("http://m.cnx.org/content/col11487/latest/"));
            c4.setIconDrawable(R.drawable.concepts_biology_lg);
            c4.setIcon("concepts");

            Content c5 = new Content();
            c5.setTitle(getString(R.string.anatomy));
            c5.setContentString(getString(R.string.anatomy_desc));
            c5.setUrl(new URL("http://m.cnx.org/content/col11496/latest/"));
            c5.setIconDrawable(R.drawable.anatomy_lg);
            c5.setIcon("anatomy");

            Content c6 = new Content();
            c6.setTitle(getString(R.string.statistics));
            c6.setContentString(getString(R.string.statistics_desc));
            //c6.setUrl(new URL("http://cnx.org/contents/30189442-6998-4686-ac05-ed152b91b9de@16.5"));
            c6.setUrl(new URL("http://m.cnx.org/content/col11562/latest/"));
            c6.setIconDrawable(R.drawable.statistics_lg);
            c6.setIcon("statistics");

            Content c7 = new Content();
            c7.setTitle(getString(R.string.econ));
            c7.setContentString(getString(R.string.coming_soon));
            c7.setUrl(new URL("http://m.cnx.org/content/col11613/latest/"));
            c7.setIconDrawable(R.drawable.econ_lg);
            c7.setIcon("econ");

            Content c11 = new Content();
            c11.setTitle(getString(R.string.macro_econ));
            c11.setContentString(getString(R.string.coming_soon));
            c11.setUrl(new URL("http://m.cnx.org/content/col11626/latest/"));
            c11.setIconDrawable(R.drawable.macro_econ_lg);
            c11.setIcon("macro");

            Content c12 = new Content();
            c12.setTitle(getString(R.string.micro_econ));
            c12.setContentString(getString(R.string.coming_soon));
            c12.setUrl(new URL("http://m.cnx.org/content/col11627/latest/"));
            c12.setIconDrawable(R.drawable.micro_econ_lg);
            c12.setIcon("micro");

            Content c8 = new Content();
            c8.setTitle(getString(R.string.precalculus));
            c8.setContentString(getString(R.string.coming_soon));
            c8.setUrl(new URL("http://m.cnx.org/content/col11667/latest/"));
            c8.setIconDrawable(R.drawable.precalculus_lg);
            c8.setIcon("precalculus");

            Content c13 = new Content();
            c13.setTitle(getString(R.string.psychology));
            c13.setContentString(getString(R.string.coming_soon));
            c13.setUrl(new URL("http://m.cnx.org/content/col11629/latest/"));
            c13.setIconDrawable(R.drawable.psychology_lg);
            c13.setIcon("psychology");

            Content c10 = new Content();
            c10.setTitle(getString(R.string.history));
            c10.setContentString(getString(R.string.coming_soon));
            c10.setUrl(new URL("http://m.cnx.org/content/col11740/latest/"));
            c10.setIconDrawable(R.drawable.history_lg);
            c10.setIcon("history");

            Content c9 = new Content();
            c9.setTitle(getString(R.string.chemistry));
            c9.setContentString(getString(R.string.coming_soon));
            c9.setUrl(new URL("http://m.cnx.org/content/col11760/latest/"));
            c9.setIconDrawable(R.drawable.chemistry_lg);
            c9.setIcon("chemistry");

            Content c14 = new Content();
            c14.setTitle(getString(R.string.algebra));
            c14.setContentString(getString(R.string.coming_soon));
            c14.setUrl(new URL("http://m.cnx.org/content/col11759/latest/"));
            c14.setIconDrawable(R.drawable.algebra_lg);
            c14.setIcon("algebra");

            Content c15 = new Content();
            c15.setTitle(getString(R.string.trig));
            c15.setContentString(getString(R.string.coming_soon));
            c15.setUrl(new URL("http://m.cnx.org/content/col11758/latest/"));
            c15.setIconDrawable(R.drawable.trig_lg);
            c15.setIcon("trig");

            Content c16 = new Content();
            c16.setTitle(getString(R.string.ap_physics));
            c16.setContentString(getString(R.string.coming_soon));
            c16.setUrl(new URL("http://m.cnx.org/content/col11844/latest/"));
            c16.setIconDrawable(R.drawable.ap_physics_lg);
            c16.setIcon("ap-physics");



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
