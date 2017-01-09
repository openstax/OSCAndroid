/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.providers.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.Bookmarks;

import android.database.Cursor;

/**
 * Utility class for Content Provider
 * @author Ed Woodward
 *
 */
public class DBUtils
{
    /**
     * Private constructor since all methods are static
     */
    private DBUtils()
    {
        
    }
    
    /**
     * Read Cursor into ArrayList of Content objects.  Closes the cursor when read is finished.
     * @param c - the Cursor to read
     * @return ArrayList<Content>
     */
    public static ArrayList<Content> readCursorIntoList(Cursor c)
    {
        ArrayList<Content> contentList = new ArrayList<Content>();
        
        int titleColumn = c.getColumnIndex(Bookmarks.TITLE); 
        int urlColumn = c.getColumnIndex(Bookmarks.URL);
        int idColumn = c.getColumnIndex(Bookmarks.ID);
        int iconColumn = c.getColumnIndex(Bookmarks.ICON);
        int otherColumn = c.getColumnIndex(Bookmarks.OTHER);
        if(c.getCount() > 0)
        {
            c.moveToNext();
            do
            {
                try
                {
                    Content con = new Content();
                    con.setTitle(c.getString(titleColumn));
                    con.setBookTitle(c.getString(otherColumn));
                    con.setUrl(c.getString(urlColumn));
                    con.setId(c.getInt(idColumn));
                    con.setIcon(c.getString(iconColumn));
                    con.setContentString(c.getString(otherColumn));
                    contentList.add(con);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }while(c.moveToNext());
        }
        c.close();
        
        return contentList;
        
    }

}
