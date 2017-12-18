/**
 * Copyright (c) 2017 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */

package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.ShelfRecyclerViewAdapter;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.ShelfBooks;
import org.openstaxcollege.android.providers.utils.DBUtils;

import java.util.ArrayList;
import java.util.Collections;

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener;
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;

/**
 * Fragment for displaying cards on bookshelf
 * @author Ed Woodward
 */
public class ShelfFragment extends Fragment implements OnStartDragListener
{
    /** Adaptor for card display */
    ShelfRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    /** list of bookmarks as Content objects */
    ArrayList<Content> content;

    /**handler */
    final private Handler handler = new Handler();

    /** Inner class for completing load work */
    private Runnable finishedLoadingListTask = new Runnable()
    {
        public void run()
        {
            finishedLoadingList();
        }
    };

    private ItemTouchHelper itemTouchHelper;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.book_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        //get already retrieved feed and reuse if it is there
        content = (ArrayList<Content>)activity.getLastNonConfigurationInstance();
        if(content == null)
        {
            //Log.d("SF","content is null");
            //no previous data, so database must be read
            readDB();
        }
        else
        {
            //Log.d("SF","content is not null");
            //reuse existing feed data
            adapter = new ShelfRecyclerViewAdapter(content, R.layout.fragment_book, activity);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

        }
    }

    /**
     * For OnStartDragListener
     * @param viewHolder The holder of the view to drag.
     */
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Log.d("SF", "onResume called");
        //if database state has changed, reload the display
        if(content != null)
        {
            int dbCount = getDBCount();

            if(dbCount >  content.size())
            {
                readDB();
            }
        }
    }

    protected void finishedLoadingList()
    {
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void readDB()
    {
        Thread loadShelfThread = new Thread()
        {
            public void run()
            {

                String order = "shelf_icon DESC, shelf_title ASC";

                content = DBUtils.readShelfCursorIntoList(activity.getContentResolver().query(ShelfBooks.CONTENT_URI, null, null, null, order));

                Collections.sort(content);

                fillData(content);
                handler.post(finishedLoadingListTask);
            }
        };
        loadShelfThread.start();

    }
    /**
     * Puts data in adapter
     * @param contentList ArrayList<Content>
     */
    private void fillData(ArrayList<Content> contentList)
    {
        //Log.d("LensViewer", "fillData() called");
        adapter = new ShelfRecyclerViewAdapter(contentList, R.layout.fragment_book,activity);
    }

    /**
     * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private int getDBCount()
    {
        Cursor c = activity.getContentResolver().query(ShelfBooks.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;

    }
}
