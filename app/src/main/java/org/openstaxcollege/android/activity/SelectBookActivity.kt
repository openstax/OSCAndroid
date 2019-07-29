/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity

import java.util.ArrayList

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.fragment.SelectBookFragment

import android.content.Intent
import android.os.Bundle

//import android.util.Log;

/**
 * Activity to view list of OSC books available.
 *
 * @author Ed Woodward
 */
class SelectBookActivity : AppCompatActivity()
{

    /** list of books as Content objects  */
    internal var content: ArrayList<Content>? = null

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * Called when the activity is first created.
     */
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectbook)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.select_book)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val transaction = supportFragmentManager.beginTransaction()
        val fragment = SelectBookFragment()
        transaction.replace(R.id.sample_content_fragment, fragment)
        transaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == android.R.id.home)
        {
            val mainIntent = Intent(applicationContext, BookshelfActivity::class.java)
            startActivity(mainIntent)
        }
        return true

    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.cache_lenstypes), content)

    }

}
