/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans;

import android.util.Log;

import java.util.ArrayList;

/**
 * Holds list of content (Book) objects
 * @author Ed Woodward
 */
public class BookList
{
    ArrayList<Content> bookList;

    public ArrayList<Content> getBookList()
    {
        return bookList;
    }

    public void setBookList(ArrayList<Content> bookList)
    {
        this.bookList = bookList;
    }

    public Content findTitle(String title)
    {
        for(int i = 0; i < bookList.size();i++)
        {
            Content c = bookList.get(i);
            if(c.getBookTitle().equals(title))
            {
                return c;
            }
        }
        return null;
    }
}
