/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.handlers


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import org.openstaxcollege.android.R
import org.openstaxcollege.android.activity.AboutActivity
import org.openstaxcollege.android.activity.NoteEditorActivity
import org.openstaxcollege.android.activity.SelectBookActivity
import org.openstaxcollege.android.activity.ViewBookmarksActivity
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.providers.Bookmarks

import android.content.ContentValues
import android.content.Context
import android.content.Intent

/**
 * Handler for context and other menus
 *
 * @author Ed Woodward
 */
class MenuHandler
{

    fun handleContextMenu(item: MenuItem, context: Context, currentContent: Content?): Boolean
    {
        return handleContextMenu(item.itemId, context, currentContent)
    }

    /**
     * Handles selected menu item actions
     * @param item MenuItem - the selected menu item
     * @param context - Context the current context
     * @param currentContent Content current content object
     * @return true if menu item handled otherwise false
     */
    fun handleContextMenu(item: Int, context: Context, currentContent: Content?): Boolean
    {
        when(item)
        {
            R.id.add_to_favs ->
            {
                val cv = ContentValues()

                //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
                cv.put(Bookmarks.TITLE, currentContent!!.title)
                //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
                if(currentContent.url != null)
                {
                    val url = currentContent.url
                    cv.put(Bookmarks.URL, url!!.replace("@\\d+(\\.\\d+)?".toRegex(), "") + context.getString(R.string.bookmarks_url_snippet))
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.bookmark_failure), Toast.LENGTH_SHORT).show()
                    return true
                }
                cv.put(Bookmarks.ICON, currentContent.icon)
                cv.put(Bookmarks.OTHER, currentContent.bookTitle)
                context.contentResolver.insert(Bookmarks.CONTENT_URI, cv)
                Toast.makeText(context, context.getString(R.string.bookmark_added) + currentContent.title!!, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.go_to_favs ->
            {
                val intent = Intent(context, ViewBookmarksActivity::class.java)
                context.startActivity(intent)
                return true
            }
            R.id.about ->
            {
                val aboutIntent = Intent(context, AboutActivity::class.java)
                context.startActivity(aboutIntent)
                return true
            }
            R.id.viewLicense ->
            {
                displayLicensesAlert(context)
                return true
            }
            R.id.notes ->
            {
                val noteintent = Intent(context, NoteEditorActivity::class.java)
                noteintent.putExtra(context.getString(R.string.webcontent), currentContent)
                context.startActivity(noteintent)
                return true
            }
            R.id.share ->
            {
                val shareintent = Intent(Intent.ACTION_SEND)
                shareintent.type = context.getString(R.string.mimetype_text)

                if(currentContent != null && currentContent.url != null)
                {
                    shareintent.putExtra(Intent.EXTRA_SUBJECT, currentContent.bookTitle + " : " + currentContent.title)
                    shareintent.putExtra(Intent.EXTRA_TEXT, currentContent.url + "\n\n " + context.getString(R.string.shared_via))

                    val chooser = Intent.createChooser(shareintent, context.getString(R.string.tell_friend) + currentContent.bookTitle)
                    context.startActivity(chooser)
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.no_data_msg), Toast.LENGTH_LONG).show()
                }
                return true
            }
            R.id.add_book ->
            {
                val bookintent = Intent(context, SelectBookActivity::class.java)
                context.startActivity(bookintent)
                return true
            }
            else -> return false
        }
    }

    private fun displayLicensesAlert(context: Context)
    {
        val view = LayoutInflater.from(context).inflate(R.layout.license_dialog, null) as WebView
        view.loadUrl("file:///android_asset/licenses.html")
        val alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(context.getString(R.string.license_title))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }


}
