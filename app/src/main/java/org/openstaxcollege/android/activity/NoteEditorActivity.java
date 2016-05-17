/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.fragment.NoteEditorFragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Note editor.  
 * Based on sample Android Notepad app: http://developer.android.com/resources/samples/NotePad/index.html
 * @author Ed Woodward
 *
 */
public class NoteEditorActivity extends Activity
{

    private Content content;


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        content = (Content)getIntent().getSerializableExtra(getString(R.string.webcontent));

        if(content == null)
        {
            Toast.makeText(NoteEditorActivity.this, "Cannot create note.  Please try again.",  Toast.LENGTH_SHORT).show();
            return;
        }

        setContentView(R.layout.main_layout);
        
        ActionBar aBar = getActionBar();
        
        if(content == null)
        {
            aBar.setTitle("Note not created correctly.");
        }
        else
        {
            aBar.setTitle("Note for " + content.getBookTitle());
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        NoteEditorFragment fragment = NoteEditorFragment.newInstance(content);
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
        
    }

}


