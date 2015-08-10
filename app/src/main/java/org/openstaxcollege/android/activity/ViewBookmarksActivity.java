/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.Window;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.BookmarkListAdapter;
import org.openstaxcollege.android.adapters.BookmarkRecyclerViewAdapter;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.handlers.MenuHandler;
import org.openstaxcollege.android.providers.Bookmarks;
import org.openstaxcollege.android.providers.utils.DBUtils;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener;
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;

/**
 * @author Ed Woodward
 *
 */
public class ViewBookmarksActivity extends Activity implements OnStartDragListener
{
    /** Adaptor for Lens list display */
    BookmarkRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    /** list of lenses as Content objects */ 
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
      
      /* (non-Javadoc)
       * @see android.app.Activity#onCreate(android.os.Bundle)
       * Called when the activity is first created.
       */
      @Override
      public void onCreate(Bundle savedInstanceState) 
      {
          super.onCreate(savedInstanceState);
          requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
          setContentView(R.layout.card_view);

          ActionBar aBar = getActionBar();
          aBar.setTitle(getString(R.string.title_favs));
          aBar.setDisplayHomeAsUpEnabled(true);
          setProgressBarIndeterminateVisibility(true);
          recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
          recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setItemAnimator(new DefaultItemAnimator());


          //get already retrieved feed and reuse if it is there
          content = (ArrayList<Content>)getLastNonConfigurationInstance();
          if(content == null)
          {
              //no previous data, so database must be read
              readDB();
          }
          else
          {
              //reuse existing feed data
              adapter = new BookmarkRecyclerViewAdapter(content, R.layout.card_row, this);
              recyclerView.setAdapter(adapter);
              ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
              itemTouchHelper = new ItemTouchHelper(callback);
              itemTouchHelper.attachToRecyclerView(recyclerView);
              setProgressBarIndeterminateVisibility(false);
             
          }
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
            return true;
        }

    }
      
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }
      
     /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
      protected void onResume()
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
          setProgressBarIndeterminateVisibility(false);
      }
      
      /** reads feed in a separate thread.  Starts progress dialog*/
      private void readDB()
      {
          Thread loadFavsThread = new Thread() 
          {
            public void run() 
            {

                String order = "fav_icon DESC, fav_title ASC";
                
                content = DBUtils.readCursorIntoList(getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, order));
                
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
          adapter = new BookmarkRecyclerViewAdapter(content, R.layout.card_row,this);
      }
      
      /**
       * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private int getDBCount()
    {
          Cursor c = getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, null);
          int count = c.getCount();
          c.close();
          return count;
          
      }

}
