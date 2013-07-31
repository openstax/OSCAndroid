/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.providers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
*  Constants class for database table 
* 
* @author Ed Woodward
*
*/
public class Bookmarks implements BaseColumns
{
    /** Private constructor.  Cannot instanciate this class */
    private Bookmarks()
    {
        
    }
    
    /** URI of code allowed to access table, I think*/
    public static final Uri CONTENT_URI = Uri.parse("content://org.openstaxcollege.android.providers.OSCProvider");
    /** title column name*/
    public static final String TITLE = "fav_title";
    /** url column name*/
    public static final String URL = "fav_url";
    /** url column name*/
    public static final String ICON = "fav_icon";
    /** id column name*/
    public static final String ID = "_id";
    /** other column name*/
    public static final String OTHER = "fav_other";


}
