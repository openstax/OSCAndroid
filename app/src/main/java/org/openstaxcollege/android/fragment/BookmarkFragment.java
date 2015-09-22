package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.BookmarkRecyclerViewAdapter;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.Bookmarks;
import org.openstaxcollege.android.providers.utils.DBUtils;

import java.util.ArrayList;
import java.util.Collections;

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener;
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;

/**
 * Created by ew2 on 9/16/15.
 */
public class BookmarkFragment extends Fragment implements OnStartDragListener
{
    /** Adaptor for card display */
    BookmarkRecyclerViewAdapter adapter;
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
        View v = inflater.inflate(R.layout.card_view, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //get already retrieved feed and reuse if it is there
        content = (ArrayList<Content>)activity.getLastNonConfigurationInstance();
        if(content == null)
        {
            //no previous data, so database must be read
            readDB();
        }
        else
        {
            //reuse existing feed data
            adapter = new BookmarkRecyclerViewAdapter(content, R.layout.card_row, activity);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            //setProgressBarIndeterminateVisibility(false);

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

    /** Actions after list is loaded in View*/
    protected void finishedLoadingList()
    {
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //setProgressBarIndeterminateVisibility(false);
    }

    /** reads feed in a separate thread.  Starts progress dialog*/
    private void readDB()
    {
        Thread loadFavsThread = new Thread()
        {
            public void run()
            {

                String order = "fav_icon DESC, fav_title ASC";

                content = DBUtils.readCursorIntoList(activity.getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, order));

                Collections.sort(content);

                fillData(content);
                handler.post(finishedLoadingListTask);
            }
        };
        loadFavsThread.start();

    }
    /**
     * Loads feed data into adapter on initial reading of feed
     * @param contentList ArrayList<Content>
     */
    private void fillData(ArrayList<Content> contentList)
    {
        //Log.d("LensViewer", "fillData() called");
        adapter = new BookmarkRecyclerViewAdapter(contentList, R.layout.card_row,activity);
    }

    /**
     * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private int getDBCount()
    {
        Cursor c = activity.getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;

    }
}
