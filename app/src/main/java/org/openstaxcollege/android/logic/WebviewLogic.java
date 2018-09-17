/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.logic;

import org.openstaxcollege.android.beans.Books;
import org.openstaxcollege.android.beans.Response;
import org.openstaxcollege.android.utils.FetchBooks;

/**
 * Testable class for business logic for WebviewActivity
 *
 * @author Ed Woodward
 *
 */
public class WebviewLogic
{
    /**
     * Parses out the book title from the browser title
     * @param title String - title from webview
     * @return String Book title
     */
    public String getBookTitle(String title)
    {
        int index1 = title.indexOf(" - ");
        String newTitle;
        if(index1 > -1)
        {
            int index2 = title.indexOf(" - ", index1 + 3);
            //Log.d("WebViewActivity","1: " + index1 + " 2: " + index2);
            if(index2 == -1)
            {
                newTitle = title.substring(0, index1);
            }
            else
            {

                newTitle = title.substring(index1 + 3, index2);
            }
            if(newTitle.equals("Anatomy & Physiology"))
            {
                newTitle = "Anatomy and Physiology";
            }
            else if(newTitle.equals("Biology for AP® Courses"))
            {
                newTitle = "Biology For AP® Courses";
            }

            return translateTitle(newTitle);
        }
        else
        {
            return "";
        }
    }

    /**
     * Retrieves from openstax CMS the PDF URL for given book title.
     * @param bookTitle String - book title to get URL for
     * @return String URL to PDF
     */
    public String getPDFUrl(String bookTitle)
    {
        String jsonTitle = translateTitle(bookTitle);
        FetchBooks fetchBooks =  new FetchBooks();

        Response response = fetchBooks.getBooks();
        String pdfURL = null;
        if(response != null && response.getBooks() != null)
        {
            for (Books books : response.getBooks())
            {
                if (books.getTitle().equals(jsonTitle))
                {
                    pdfURL = books.getLowResolutionPdfUrl();

                    if(pdfURL == null)
                    {
                        pdfURL = books.getHighResolutionPdfUrl();
                    }
                }
            }
        }
        return pdfURL;
    }

    /**
     * Translate actual book title into title needed to fetch PDF URL
     * @param bookTitle - String
     * @return String translated book title if it matches otherwise the original book title
     */
    private String translateTitle(String bookTitle)
    {
        if(bookTitle.equals("College Physics For AP® Courses"))
        {
            return "The AP Physics Collection";
        }
        else
        {
            return bookTitle;
        }
    }

}
