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
        //val url:String = webLogic.getPDFUrl("Fizyka dla szkół wyższych Tom 1")
        //val url:String = webLogic.getPDFUrl("Fizyka dla szkół wyższych Tom 2")
        //val url:String = webLogic.getPDFUrl("College Physics")
        //val url:String = webLogic.getPDFUrl("Precalculus")
        //val url:String = webLogic.getPDFUrl("Chemistry: Atoms First")
        val url:String = webLogic.getPDFUrl("Biology For AP® Courses")
        //val url: String? = webLogic.getPDFUrl("Bad Title")
        println(url)
    }
}