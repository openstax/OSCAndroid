/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.test

import org.junit.Test
import org.openstaxcollege.android.logic.WebviewLogic

class WebviewLogicTest
{
    @Test
    fun testGetPdfUrl()
    {
        val webLogic = WebviewLogic()
        val url:String = webLogic.getPDFUrl("Fizyka dla szkół wyższyc. Tom 1")
        //val url:String = webLogic.getPDFUrl("College Physics")
        //val url:String = webLogic.getPDFUrl("Precalculus")
        //val url:String = webLogic.getPDFUrl("Chemistry: Atoms First")
        //val url:String = webLogic.getPDFUrl("Biology for AP® Courses")
        //val url: String? = webLogic.getPDFUrl("Bad Title")
        println(url)
    }

    @Test
    fun testGetBookTitle()
    {
        val webLogic = WebviewLogic()
        //val title: String = webLogic.getBookTitle("Introduction to Science and the Realm of Physics, Physical Quantities, and Units - College Physics - OpenStax CNX")
        //val title: String = webLogic.getBookTitle("Introduction to Functions - Precalculus - OpenStax CNX")
        //val title: String = webLogic.getBookTitle("Introduction - Anatomy & Physiology - OpenStax CNX")
        val title: String = webLogic.getBookTitle("Introduction - Biology for AP® Courses - OpenStax CNX")
        println(title)
    }
}