/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.handlers;

import java.net.MalformedURLException;
import java.net.URL;

import android.view.MenuItem;
import android.widget.Toast;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.LandingActivity;
import org.openstaxcollege.android.activity.NoteEditorActivity;
import org.openstaxcollege.android.activity.ViewBookmarksActivity;
import org.openstaxcollege.android.activity.WebViewActivity;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.Bookmarks;
import org.openstaxcollege.android.utils.Constants;
import org.openstaxcollege.android.utils.ContentCache;
import org.openstaxcollege.android.utils.MenuUtil;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Handler for context and other menus
 * 
 * @author Ed Woodward
 *
 */
public class MenuHandler
{

    public boolean handleContextMenu(MenuItem item, Context context, Content currentContent)
    {
        return handleContextMenu(item.getItemId(), context, currentContent);
    }
    /**
     * Handles selected menu item actions
     * @param item MenuItem - the selected menu item
     * @param context - Context the current context
     * @param currentContent Content current content object
     * @return true if menu item handled otherwise false
     */
    public boolean handleContextMenu(int item, Context context, Content currentContent)
    {
        switch (item) 
        {
            case R.id.add_to_favs:
                ContentValues cv = new ContentValues();
                if(currentContent.getUrl().toString().indexOf("http://mobile.cnx.org/content/search") > -1 || currentContent.getUrl().toString().indexOf("http://m.cnx.org/content/search") > -1)
                {
                    String title = MenuUtil.getSearchTitle(currentContent.getUrl().toString());
                    cv.put(Bookmarks.TITLE, title);
                }
                else
                {
                    cv.put(Bookmarks.TITLE, currentContent.getTitle());
                }
                //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
                cv.put(Bookmarks.URL, currentContent.getUrl().toString());
                cv.put(Bookmarks.ICON, currentContent.getIcon());
                context.getContentResolver().insert(Bookmarks.CONTENT_URI, cv);
                Toast.makeText(context, "Bookmark added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.go_to_favs:
                Intent intent = new Intent(context, ViewBookmarksActivity.class);
                context.startActivity(intent);
                return true;
//            case R.id.help:
//                try
//                {
//                    Content content = new Content();
//                    content.setUrl(new URL(Constants.HELP_FILE_URL));
//                    ContentCache.setObject(context.getString(R.string.webcontent), content);
//                    context.startActivity(new Intent(context, WebViewActivity.class));
//
//                }
//                catch (MalformedURLException e)
//                {
//                    e.printStackTrace();
//                }
//                return true;
            case R.id.delete_from__favs:
                context.getContentResolver().delete(Bookmarks.CONTENT_URI, "_id="+ currentContent.getId(), null);
                return true;
//            case R.id.home:
//                Intent homeIntent = new Intent(context, LandingActivity.class);
//                context.startActivity(homeIntent);
//                return true;
//            case R.id.note:
//                ContentCache.setObject("content", currentContent);
//                Intent noteIntent = new Intent(context, NoteEditorActivity.class);
//                context.startActivity(noteIntent);
//                return true;
            default:
                return false;
        }
    }
}
