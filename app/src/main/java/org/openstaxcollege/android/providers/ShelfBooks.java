/**
 * Copyright (c) 2017 Rice University
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

public class ShelfBooks implements BaseColumns
{
    /** Private constructor. */
    private ShelfBooks()
    {

    }

    /** URI of code allowed to access table, I think*/
    public static final Uri CONTENT_URI = Uri.parse("content://org.openstaxcollege.android.providers.ShelfProvider");
    /** title column name*/
    public static final String TITLE = "shelf_title";
    /** url column name*/
    public static final String URL = "shelf_url";
    /** url column name*/
    public static final String ICON = "shelf_icon";
    /** id column name*/
    public static final String ID = "_id";
    /** other column name*/
    public static final String OTHER = "shelf_other";
}
