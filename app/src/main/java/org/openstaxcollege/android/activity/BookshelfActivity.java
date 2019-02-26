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
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.fragment.BookmarkFragment;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.app_name_html)));
        final TextView title = findViewById(R.id.bookshelf_help);
        Linkify.addLinks(title, Linkify.ALL);
        final Context context = this;
        title.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, SelectBookActivity.class);
                context.startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.bookshelf_nav:
                                selectedFragment = new ShelfFragment();
                                title.setVisibility(View.VISIBLE);
                                break;
                            case R.id.bookmarks_nav:
                                Log.d("Bookshelf","bookmark selected");
                                selectedFragment = new BookmarkFragment();
                                title.setVisibility(View.INVISIBLE);
                                break;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.sample_content_fragment, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sample_content_fragment, new ShelfFragment());
        transaction.commit();

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
