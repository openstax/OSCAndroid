/**
 * Copyright (c) 2017 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */

package org.openstaxcollege.android.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

/**
 * Database provider for book shelf books
 *
 * @author Ed Woodward
 */

public class ShelfProvider extends ContentProvider
{
    /** Shelf table name */
    public static final String SHELF_TABLE = "shelf";

    /** Map of shelf table columns */
    private static HashMap<String, String> ShelfProjectionMap;

    /** static section to initialize shelf table map */
    static
    {
        ShelfProjectionMap = new HashMap<>();
        ShelfProjectionMap.put(ShelfBooks.ID, ShelfBooks.ID);
        ShelfProjectionMap.put(ShelfBooks.TITLE, ShelfBooks.TITLE);
        ShelfProjectionMap.put(ShelfBooks.URL, ShelfBooks.URL);
        ShelfProjectionMap.put(ShelfBooks.ICON, ShelfBooks.ICON);
        ShelfProjectionMap.put(ShelfBooks.OTHER, ShelfBooks.OTHER);
    }

    /** Variable for database helper */
    private DatabaseHelper dbHelper;

    /**  Called when class created. initializes database helper*/
    @Override
    public boolean onCreate()
    {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    /** used to delete content from shelf table */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(SHELF_TABLE, selection, selectionArgs);
        db.close();
        return count;
    }

    /**  needed for interface.  Not sure why.*/
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    /** Used to insert a book in the shelf table */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        //check if the URL is already in favorites
        boolean duplicate = checkForDuplicate(values.getAsString(Bookmarks.URL));
        //If not in favorites, save it
        if(! duplicate)
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(SHELF_TABLE, null, new ContentValues(values));
            db.close();
            if (rowId > 0)
            {
                Uri favUri = ContentUris.withAppendedId(Bookmarks.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(favUri, null);
                return favUri;
            }

            throw new SQLException("Failed to insert row into " + uri);
        }
        return null;

    }

    /**  Used to retrieve all items in the Shelf table*/
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(SHELF_TABLE);
        qb.setProjectionMap(ShelfProjectionMap);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    /** Could be used to update items already in the Shelf table
     * Not used now, but needed for interface. */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }

    /**
     * Check if URL is already in database
     * @param url String - URL to check
     * @return true if the URL is already in the database, otherwise false
     */
    private boolean checkForDuplicate(String url)
    {
        boolean dup = false;
        Cursor c = query(Bookmarks.CONTENT_URI,null,"shelf_url='"+url+"'",null, null);
        int urlColumn = c.getColumnIndex(Bookmarks.URL);
        if(c.getCount()>0)
        {
            c.moveToNext();
            if(c.getString(urlColumn).equals(url))
            {
                dup = true;
            }
        }
        c.close();
        return dup;
    }
}
