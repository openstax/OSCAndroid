/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity

import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import org.openstaxcollege.android.R
import org.openstaxcollege.android.fragment.BookmarkFragment
import org.openstaxcollege.android.handlers.MenuHandler

import android.content.Intent
import android.os.Bundle
import android.view.View

/**
 * @author Ed Woodward
 * Used to display cardview of saved Favorites
 */
class ViewBookmarksActivity : AppCompatActivity()
{

    /* (non-Javadoc)
       * @see android.app.Activity#onCreate(android.os.Bundle)
       * Called when the activity is first created.
       */
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val toolbarLayout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        toolbarLayout.title = getString(R.string.title_favs)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = BookmarkFragment()
        transaction.add(R.id.container, fragment)
        transaction.commit()

    }

    
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == android.R.id.home)
        {
            val mainIntent = Intent(applicationContext, BookshelfActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(mainIntent)
            return true
        }
        else
        {
            val mh = MenuHandler()
            return mh.handleContextMenu(item, this, null)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.bookmark_options_menu, menu)
        return true

    }

}
