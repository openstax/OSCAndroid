/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans

import java.io.Serializable

/** Class for holding Content details.  Used for Lenses, collections and modules.  */
class Content : Serializable, Comparable<Content>
{

    /** URL  to retrieve content  */
    var url: String? = null
    /** Title of content  */
    var title: String? = null
    /** icon of content.  Only used for Lenses  */
    var icon: String? = null
    var bookTitle: String? = null
    /**
     * String to hold lens description and keywords
     */
    var contentString: String? = null
    /**
     * database id
     */
    var id: Int = 0

    var bookUrl: String? = null

    var pdfUrl = ""

    override fun compareTo(another: Content): Int
    {
        val titleCompare = title!!.toUpperCase().trim { it <= ' ' }.compareTo(another.title!!.toUpperCase().trim { it <= ' ' })
        return if(titleCompare != 0)
        {
            titleCompare
        }
        else
        {
            url!!.compareTo(another.url!!)
        }

    }


}
