/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans

import android.util.Log

import java.util.ArrayList

/**
 * Holds list of content (Book) objects
 * @author Ed Woodward
 */
class BookList
{
    var bookList: ArrayList<Content>? = null

    fun findTitle(title: String): Content?
    {
        for(i in bookList!!.indices)
        {
            val c = bookList!![i]
            if(c.bookTitle == title)
            {
                return c
            }
        }
        return null
    }
}
