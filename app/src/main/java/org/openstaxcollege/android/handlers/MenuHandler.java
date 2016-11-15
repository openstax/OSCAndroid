/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.handlers;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;
import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.NoteEditorActivity;
import org.openstaxcollege.android.activity.ViewBookmarksActivity;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.logic.WebviewLogic;
import org.openstaxcollege.android.providers.Bookmarks;
import org.openstaxcollege.android.utils.MenuUtil;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import java.io.File;

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

                //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
                cv.put(Bookmarks.TITLE, currentContent.getTitle());
                //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
                String url = currentContent.getUrl().toString();
                cv.put(Bookmarks.URL, url.replaceAll("@\\d+(\\.\\d+)?","")+ "?bookmark=1");
                cv.put(Bookmarks.ICON, currentContent.getIcon());
                cv.put(Bookmarks.OTHER, currentContent.getBookTitle());
                context.getContentResolver().insert(Bookmarks.CONTENT_URI, cv);
                Toast.makeText(context, "Bookmark added for " + currentContent.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.go_to_favs:
                Intent intent = new Intent(context, ViewBookmarksActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.viewLicense:
                displayLicensesAlert(context);
                return true;
            case R.id.notes:
                Intent noteintent = new Intent(context, NoteEditorActivity.class);
                noteintent.putExtra(context.getString(R.string.webcontent),currentContent);
                context.startActivity(noteintent);
                return true;
            case R.id.download:
                displayDownloadAlert(context, currentContent);
                return true;
            case R.id.share:
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType(context.getString(R.string.mimetype_text));

                if(currentContent != null)
                {
                    shareintent.putExtra(Intent.EXTRA_SUBJECT, currentContent.getBookTitle() + " : " + currentContent.getTitle());
                    shareintent.putExtra(Intent.EXTRA_TEXT, currentContent.getUrl().toString() + "\n\n " + context.getString(R.string.shared_via));

                    Intent chooser = Intent.createChooser(shareintent, context.getString(R.string.tell_friend) + " "+ currentContent.getBookTitle());
                    context.startActivity(chooser);
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.no_data_msg),  Toast.LENGTH_LONG).show();
                }
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

    /**
     * Displays alert telling user where the downloaded files are located, the size of the files to download and confirms download.
     * If download is confirmed, DownloadHandler is called.
     * @param context - Context - the current context
     * @param currentContent - Content - current content object
     */
    private void displayDownloadAlert(final Context context, final Content currentContent)
    {

        String message = "PDF files are saved in an OpenStax folder on the SDCard or on the device's internal memory.  Press OK to continue.";

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Download");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                File cnxDir = new File(Environment.getExternalStorageDirectory(), "OpenStax/");
                if(!cnxDir.exists())
                {
                    cnxDir.mkdir();
                }
                DownloadManager dm = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                WebviewLogic wl = new WebviewLogic();
                String pdfUrl = wl.getPDFUrl(currentContent.getBookTitle());

                Uri uri = Uri.parse(pdfUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDestinationInExternalPublicDir("/" + context.getString(R.string.folder_name), MenuUtil.getTitle(currentContent.getBookTitle()) + ".pdf");
                request.setTitle(currentContent.getBookTitle() + ".pdf");
                dm.enqueue(request);



            } });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing

            } });
        alertDialog.show();
    }

    private void displayNoMediaToast(Context context)
    {
        Toast.makeText(context, "The requested file cannot be downloaded because the app cannot write to memory.", Toast.LENGTH_LONG).show();

    }

}
