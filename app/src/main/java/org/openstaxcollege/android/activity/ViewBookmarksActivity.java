/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.view.Window;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.BookmarkListAdapter;
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

/**
 * @author Ed Woodward
 *
 */
public class ViewBookmarksActivity extends ListActivity
{
    /** Adaptor for Lens list display */ 
    BookmarkListAdapter adapter;
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
      
      /* (non-Javadoc)
       * @see android.app.Activity#onCreate(android.os.Bundle)
       * Called when the activity is first created.
       */
      @Override
      public void onCreate(Bundle savedInstanceState) 
      {
          super.onCreate(savedInstanceState);
          requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
          setContentView(R.layout.list_view);
          registerForContextMenu(getListView());
          
          ActionBar aBar = getActionBar();
          aBar.setTitle(getString(R.string.title_favs));
          setProgressBarIndeterminateVisibility(true);
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
                  adapter = new BookmarkListAdapter(ViewBookmarksActivity.this, content);
                  setListAdapter(adapter);
                  setProgressBarIndeterminateVisibility(false);
             
          }
      }
      
      /* (non-Javadoc)
       * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
       * Creates context menu from lenses_context_menu.xml
       */
      @Override
      public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
      {
          //Log.d("ViewLenses.onCreateContextMenu()", "Called");
          AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
          Content content = (Content)getListView().getItemAtPosition(info.position);
          menu.setHeaderTitle(content.getTitle());
          super.onCreateContextMenu(menu, v, menuInfo);
          getMenuInflater().inflate(R.menu.favs_context_menu, menu);
      }
      
      /* (non-Javadoc)
       * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
       * Passes menu selection to MenuHandler
       */
      @Override
      public boolean onContextItemSelected(android.view.MenuItem item) 
      {
          AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
          Content content = (Content)getListView().getItemAtPosition(info.position);
          MenuHandler mh = new MenuHandler();
          boolean returnVal = mh.handleContextMenu(item, this, content);
          if(item.getItemId() == R.id.delete_from__favs)
          {
              adapter.remove(content);
          }
          if(returnVal)
          {
              return returnVal;
          }
          else
          {
              return super.onContextItemSelected(item);
          }
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
      
      /* (non-Javadoc)
       * Handles selection of an item in the Lenses list
       * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
       */
      @Override
      protected void onListItemClick(ListView l, View v, int position, long id) 
      {
          Content content = (Content)getListView().getItemAtPosition(position);
          Log.d("VBkmrksActvty.onLIC()","URL: " + content.getUrl().toString());
          Intent wv = new Intent(this, WebViewActivity.class);
          wv.putExtra(getString(R.string.webcontent), content);

          startActivity(wv);
      }
      
      /** Actions after list is loaded in View*/
      protected void finishedLoadingList() 
      {
          setListAdapter(adapter);
          getListView().setSelection(0);
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
                
               //Collections.sort((List<Content>)content);
                
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
          adapter = new BookmarkListAdapter(ViewBookmarksActivity.this, contentList);
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
