/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.test

import org.openstaxcollege.android.beans.Response
import org.openstaxcollege.android.utils.FetchBooks
import org.junit.Test as test

class FetchJsonTest
{
    @test
    fun testFetchJson()
    {
        var fetchBooks = FetchBooks()

        val response: Response? = fetchBooks.getBooks()
        val books = response?.books
        for(item in books.orEmpty())
        {
            println(item?.title)
            var pdf = item?.lowResolutionPdfUrl
            if(pdf == null)
            {
                pdf = item?.highResolutionPdfUrl
            }
            println(pdf)
        }

    }
}

