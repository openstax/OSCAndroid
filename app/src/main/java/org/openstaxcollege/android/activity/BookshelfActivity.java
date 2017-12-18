/**
 * Copyright (c) 2017 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */

package org.openstaxcollege.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.fragment.ShelfFragment;
import org.openstaxcollege.android.handlers.MenuHandler;

/**
 * Activity for displaying cards of book on bookshelf
 * @author Ed Woodward
 */

public class BookshelfActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.app_name_html)));;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ShelfFragment fragment = new ShelfFragment();
        transaction.add(R.id.sample_content_fragment, fragment);
        transaction.commit();

        final Context context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, SelectBookActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            Intent mainIntent = new Intent(getApplicationContext(), BookshelfActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        getMenuInflater().inflate(R.menu.landing_options_menu, menu);
        return true;

    }
}
