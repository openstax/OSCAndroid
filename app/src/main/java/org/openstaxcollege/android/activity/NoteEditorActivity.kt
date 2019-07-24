/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity

import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.fragment.NoteEditorFragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast

/**
 * Note editor.
 * Based on sample Android Notepad app: http://developer.android.com/resources/samples/NotePad/index.html
 * @author Ed Woodward
 */
class NoteEditorActivity : AppCompatActivity()
{

    private var content: Content? = null


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        content = intent.getSerializableExtra(getString(R.string.webcontent)) as Content

        if(content == null)
        {
            Toast.makeText(this@NoteEditorActivity, "Cannot create note.  Please try again.", Toast.LENGTH_SHORT).show()
            return
        }

        setContentView(R.layout.activity_noteeditor)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val aBar = supportActionBar

        if(content == null)
        {
            aBar!!.title = "Note not created correctly."
        }
        else
        {
            aBar!!.title = content!!.bookTitle + " Note"
        }

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = NoteEditorFragment.newInstance(content!!)
        transaction.replace(R.id.contentFragment, fragment)
        transaction.commit()

        val activity = this
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            fragment.saveNote()
            activity.finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        if(content == null)
        {
            return false
        }

        menu.clear()
        inflater.inflate(R.menu.noteeditor_menu, menu)

        return true
    }


}


