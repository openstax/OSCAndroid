/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.providers;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder; 
import android.net.Uri;
//import android.util.Log;

/**
*  Database provider for user favorites 
* 
* @author Ed Woodward
*
*/
public class OSCProvider extends ContentProvider
{
    /** Favorites table name */
    public static final String FAVS_TABLE = "favs";

    /** Map of Fav table columns */
    private static HashMap<String, String> FavsProjectionMap;
    
    /** static section to initialize fav table map */
    static
    {
        FavsProjectionMap = new HashMap<>();
        FavsProjectionMap.put(Bookmarks.ID, Bookmarks.ID);
        FavsProjectionMap.put(Bookmarks.TITLE, Bookmarks.TITLE);
        FavsProjectionMap.put(Bookmarks.URL, Bookmarks.URL);
        FavsProjectionMap.put(Bookmarks.ICON, Bookmarks.ICON);
        FavsProjectionMap.put(Bookmarks.OTHER, Bookmarks.OTHER);
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

    /** used to delete content from favs table */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(FAVS_TABLE, selection, selectionArgs);
        db.close();
        return count;
    }

    /**  needed for interface.  Not sure why.*/
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    /** Used to insert a favorite in the favs table */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        //check if the URL is already in favorites
        boolean duplicate = checkForDuplicate(values.getAsString(Bookmarks.URL));
        //If not in favorites, save it
        if(! duplicate)
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(FAVS_TABLE, null, new ContentValues(values));
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

    /**  Used to retrieve all items in the Favs table*/
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(FAVS_TABLE);
        qb.setProjectionMap(FavsProjectionMap);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    /** Could be used to update items already in the favs table
     * Not used now, but needed for interface. */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
    
    /**
     * Check if URL isd already in database
     * @param url String - URL to check
     * @return true if the URL is already in the database, otherwise false
     */
    private boolean checkForDuplicate(String url)
    {
        boolean dup = false;
        Cursor c = query(Bookmarks.CONTENT_URI,null,"fav_url='"+url+"'",null, null);
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
