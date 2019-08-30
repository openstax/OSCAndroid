/**
 * Copyright (c) 2015 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.fragment

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.text.util.Linkify
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import org.openstaxcollege.android.R
import org.openstaxcollege.android.activity.SelectBookActivity
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.handlers.MenuHandler
import org.openstaxcollege.android.providers.Notes
import org.openstaxcollege.android.utils.MenuUtil

import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

/**
 * Fragment for displaying note editor
 * @author Ed Woodward
 */
class NoteEditorFragment : Fragment()
{
    private val REQUEST = 1337

    private var state: Int = 0
    private var cursor: Cursor? = null
    private var editText: EditText? = null
    private var originalContent: String? = null
    private var content: Content? = null
    internal var activity: AppCompatActivity? = null

    /**
     * A custom EditText that draws lines between each line of text that is displayed.
     */
    class LinedEditText // we need this constructor for LayoutInflater
    (context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs)
    {
        private val rect: Rect
        private val paint: Paint

        init
        {

            rect = Rect()
            paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.color = -0x7f000001
        }

        override fun onDraw(canvas: Canvas)
        {
            val count = lineCount
            val r = rect
            val newpaint = paint

            for(i in 0 until count)
            {
                val baseline = getLineBounds(i, r)

                canvas.drawLine(r.left.toFloat(), (baseline + 1).toFloat(), r.right.toFloat(), (baseline + 1).toFloat(), newpaint)
            }

            super.onDraw(canvas)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d("NoteEditor", "**on create called")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        activity = getActivity() as AppCompatActivity
        content = arguments!!.get("content") as Content
        val v = inflater.inflate(R.layout.note_editor, container, false)

        state = STATE_EDIT

        editText = v.findViewById<View>(R.id.note) as EditText
        checkDBForNote()

        if(savedInstanceState != null)
        {
            originalContent = savedInstanceState.getString(ORIGINAL_CONTENT)
        }
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?)
    {
        menu!!.clear()
        inflater!!.inflate(R.menu.noteeditor_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if(item!!.itemId == android.R.id.home)
        {
            val mainIntent = Intent(context, SelectBookActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainIntent)
            return true
        }
        else if(item.itemId == R.id.delete_note)
        {
            deleteNote()
            getActivity()?.finish()
            return true

        }
        else if(item.itemId == R.id.export_note)
        {
            exportNote()
            return true

        }
        else if(item.itemId == R.id.share)
        {
            shareNote()
            return true

        }
        else
        {

            val mh = MenuHandler()
            return mh.handleContextMenu(item, requireContext(), content)
        }

    }

    override fun onResume()
    {
        super.onResume()

        if(originalContent == null && editText != null)
        {
            originalContent = editText!!.text.toString()
        }

    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putString(ORIGINAL_CONTENT, originalContent)
    }

    override fun onPause()
    {
        super.onPause()

        if(editText == null)
        {
            //activity.setResult(activity.RESULT_CANCELED)
            return
        }

        val text = editText!!.text.toString()
        val length = text.length

        if(activity!!.isFinishing && length == 0 && cursor != null)
        {
            //activity.setResult(NoteEditorActivity.RESULT_CANCELED)
            return
        }
        else
        {
            saveNote()
        }
    }

    /**
     * IF the note is empty, it displays a message to the user
     * If the note has text, the title is set and the note placed in the database.
     * Handles updating or inserting a new note based on the flag set in checkDBForNote()
     */
    fun saveNote()
    {

        val values = ContentValues()

        val text = editText!!.text.toString()
        //Log.d("NoteEditorActivity", "note: " + text);
        val length = text.length

        if(length == 0)
        {
            Toast.makeText(activity, getString(R.string.nothing_to_save), Toast.LENGTH_SHORT).show()
            return
        }
        var title = content!!.bookTitle
        if(length > 30)
        {
            val lastSpace = title.lastIndexOf(' ')
            if(lastSpace > 0)
            {
                title = title.substring(0, lastSpace)
            }
        }
        values.put(Notes.TITLE, title)

        values.put(Notes.NOTE, text)
        values.put(Notes.URL, content!!.bookUrl)

        try
        {
            if(state == STATE_UPDATE)
            {
                //Log.d("NoteEditorActivity", "updating note");
                activity!!.contentResolver.update(Notes.CONTENT_URI, values, "notes_url=?", arrayOf(content!!.bookUrl))
            }
            else
            {
                activity!!.contentResolver.insert(Notes.CONTENT_URI, values)
            }
        } catch(e: NullPointerException)
        {
            Log.e("NoteEditorActivity", e.message)
        }

    }

    /**
     * Deletes the note from the database.
     */
    private fun deleteNote()
    {
        activity!!.contentResolver.delete(Notes.CONTENT_URI, "notes_url=?", arrayOf(content!!.bookUrl))
        editText!!.setText("")
        activity!!.finish()
    }

    /**
     * Checks database for an existing note for the URL of the current content
     * If the note exists, it is retrieved and the cursor placed at the end of the text
     */
    private fun checkDBForNote()
    {
        //Log.d("NoteFragment", "bookURL = " + content.getBookUrl());
        if(content != null)
        {
            cursor = activity!!.contentResolver.query(Notes.CONTENT_URI, null, "notes_url='" + content!!.bookUrl + "'", null, null)
            if(cursor!!.count > 0)
            {
                cursor!!.moveToNext()
                //Log.d("NoteEditorActivity.checkDBForNote()", "cursor.count(): " + cursor.getCount());
                val notesColumn = cursor!!.getColumnIndex(Notes.NOTE)
                //Log.d("NoteEditorActivity.checkDBForNote()", "urlColumn: " + urlColumn);
                //Log.d("NoteEditorActivity.checkDBForNote()", "notesColumn: " + notesColumn);
                editText!!.append(cursor!!.getString(notesColumn))
                editText!!.setSelection(editText!!.text.length)
                Linkify.addLinks(editText!!, Linkify.ALL)
                state = STATE_UPDATE
            }
            else
            {
                state = STATE_EDIT
                editText!!.setText("")
            }
        }
        else
        {
            state = STATE_EDIT
            editText!!.setText(getString(R.string.bad_note_msg))
        }
    }

    /**
     * Saves the note as a text file in the Connexions file.
     */
    private fun exportNote()
    {
        if(ContextCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            val cnxDir = File(Environment.getExternalStorageDirectory(), "OpenStax/")
            if(!cnxDir.exists())
            {
                cnxDir.mkdir()
            }
            val fileName = MenuUtil.getTitle(content!!.bookTitle) + ".txt"
            val file = File(cnxDir, fileName)
            val text = editText!!.text.toString()
            var pw: PrintWriter? = null

            try
            {
                pw = PrintWriter(file)
                pw.write(text)
                pw.flush()
                Toast.makeText(activity, fileName + getString(R.string.note_saved_snippet), Toast.LENGTH_LONG).show()
            } catch(e: FileNotFoundException)
            {
                Log.d("NoteEditorActivity", "Error exporting note: $e", e)
            }
            finally
            {
                pw?.close()
            }
        }
        else if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            Snackbar.make(view!!, getString(R.string.external_storage_request), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.ok_button)) { requestPermissions(STORAGE_PERMS, REQUEST) }
                    .show()
        }
        else
        {
            requestPermissions(STORAGE_PERMS, REQUEST)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {

        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            exportNote()
        }
    }

    private fun shareNote()
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = getString(R.string.mimetype_text)

        if(content != null)
        {
            intent.putExtra(Intent.EXTRA_SUBJECT, "Note for " + content!!.bookTitle)
            val text = editText!!.text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, text + "\n\n" + getString(R.string.shared_via))

            val chooser = Intent.createChooser(intent, getString(R.string.tell_friend) + " " + content!!.title)
            startActivity(chooser)

        }
        else
        {
            Toast.makeText(getActivity(), getString(R.string.no_data_msg), Toast.LENGTH_LONG).show()
        }

    }

    companion object
    {
        private val ORIGINAL_CONTENT = "origContent"
        private val STORAGE_PERMS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        private val STATE_EDIT = 0
        private val STATE_UPDATE = 1

        fun newInstance(c: Content): NoteEditorFragment
        {
            Log.d("NoteEditor", "**instance called")
            val nef = NoteEditorFragment()
            val args = Bundle()
            args.putSerializable("content", c)
            nef.arguments = args
            return nef

        }
    }
}
