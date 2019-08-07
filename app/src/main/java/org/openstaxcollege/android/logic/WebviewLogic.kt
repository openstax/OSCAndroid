/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.logic

import android.util.Log

import org.openstaxcollege.android.utils.FetchBooks

/**
 * Testable class for business logic for WebviewActivity
 *
 * @author Ed Woodward
 */
class WebviewLogic
{
    /**
     * Parses out the book title from the browser title
     * @param title String - title from webview
     * @return String Book title
     */
    fun getBookTitle(title: String): String
    {
        val index1 = title.indexOf(" - ")
        var newTitle: String
        if(index1 > -1)
        {
            val index2 = title.indexOf(" - ", index1 + 3)
            //Log.d("WebViewActivity","1: " + index1 + " 2: " + index2);
            if(index2 == -1)
            {
                newTitle = title.substring(0, index1)
            }
            else
            {

                newTitle = title.substring(index1 + 3, index2)
            }
            if(newTitle == "Anatomy & Physiology")
            {
                newTitle = "Anatomy and Physiology"
            }

            return newTitle
        }
        else
        {
            return ""
        }
    }

    /**
     * Retrieves from openstax CMS the PDF URL for given book title.
     * @param bookTitle String - book title to get URL for
     * @return String URL to PDF
     */
    fun getPDFUrl(bookTitle: String): String?
    {
        //Log.d("**WVLogic", "called");
        val jsonTitle = translateTitle(bookTitle)
        val fetchBooks = FetchBooks()

        val response = fetchBooks.getBooks()
        var pdfURL: String? = ""
        if(response != null && response.books != null)
        {
            for(books in response.books)
            {
                if(books?.title == jsonTitle)
                {
                    pdfURL = books.lowResolutionPdfUrl

                    if(pdfURL == null)
                    {
                        pdfURL = books.highResolutionPdfUrl
                    }
                }
            }
            if(pdfURL == null)
            {
                pdfURL = ""
            }
        }
        return pdfURL
    }

    /**
     * Translate actual book title into title needed to fetch PDF URL
     * @param bookTitle - String
     * @return String translated book title if it matches otherwise the original book title
     */
    private fun translateTitle(bookTitle: String): String
    {
        return if(bookTitle == "College Physics For AP® Courses")
        {
            "The AP Physics Collection"
        }
        else
        {
            bookTitle
        }
    }

}
