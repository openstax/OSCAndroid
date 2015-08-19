/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.handlers;


import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.ViewBookmarksActivity;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.Bookmarks;
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
            case R.id.viewLicense:
                displayLicensesAlert(context);
                return true;
            default:
                return false;
        }
    }

    private void displayLicensesAlert(Context context)
    {
        WebView view = (WebView) LayoutInflater.from(context).inflate(R.layout.license_dialog, null);
        view.loadUrl("file:///android_asset/licenses.html");
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(context.getString(R.string.license_title))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
