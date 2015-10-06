/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.fragment.BookmarkFragment;
import org.openstaxcollege.android.handlers.MenuHandler;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author Ed Woodward
 * Used to display cardview of saved Favorites
 *
 */
public class ViewBookmarksActivity extends Activity
{

      /* (non-Javadoc)
       * @see android.app.Activity#onCreate(android.os.Bundle)
       * Called when the activity is first created.
       */
      @Override
      public void onCreate(Bundle savedInstanceState) 
      {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.bookmark_layout);
          ActionBar aBar = getActionBar();
          aBar.setTitle(getString(R.string.title_favs));

          aBar.setDisplayHomeAsUpEnabled(false);

          FragmentTransaction transaction = getFragmentManager().beginTransaction();
          BookmarkFragment fragment = new BookmarkFragment();
          transaction.add(R.id.container, fragment);
          transaction.commit();

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
            MenuHandler mh = new MenuHandler();
            return mh.handleContextMenu(item, this, null);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bookmark_options_menu, menu);
        return true;

    }

}
