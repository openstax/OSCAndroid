/**
 * Copyright (c) 2017 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */

package org.openstaxcollege.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.text.HtmlCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.util.Linkify
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import org.openstaxcollege.android.R
import org.openstaxcollege.android.fragment.BookmarkFragment
import org.openstaxcollege.android.fragment.ShelfFragment
import org.openstaxcollege.android.handlers.MenuHandler

/**
 * Activity for displaying cards of book on bookshelf
 * @author Ed Woodward
 */

class BookshelfActivity : AppCompatActivity()
{
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookshelf)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = HtmlCompat.fromHtml(getString(R.string.app_name_html), HtmlCompat.FROM_HTML_MODE_LEGACY)
        val title = findViewById<TextView>(R.id.bookshelf_help)
        Linkify.addLinks(title, Linkify.ALL)
        val context = this
        title.setOnClickListener {
            val intent = Intent(context, SelectBookActivity::class.java)
            context.startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when(item.itemId)
            {
                R.id.bookshelf_nav ->
                {
                    selectedFragment = ShelfFragment()
                    title.visibility = View.VISIBLE
                }
                R.id.bookmarks_nav ->
                {
                    Log.d("Bookshelf", "bookmark selected")
                    selectedFragment = BookmarkFragment()
                    title.visibility = View.INVISIBLE
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.sample_content_fragment, selectedFragment!!)
            transaction.commit()
            true
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.sample_content_fragment, ShelfFragment())
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
        menuInflater.inflate(R.menu.landing_options_menu, menu)
        return true

    }
}
