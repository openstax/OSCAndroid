/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.utils


/**
 * Utility class for MenuHandler
 * @author Ed Woodward
 */
object MenuUtil
{
    /**
     * Replaces spaces in title with underscores and truncates to 20 chars if needed
     * @param title - The title to modify
     * @return String - the given title with spaces replaced by underscores and truncated to 20 chars if longer that 20 chars.
     */
    fun getTitle(title: String?): String
    {
        if(title == null)
        {
            return ""
        }
        var realNewTitle = title.replace("&".toRegex(), "_")
        realNewTitle = realNewTitle.replace(" ".toRegex(), "_")
        return realNewTitle.replace("\\p{Punct}".toRegex(), "")
        //return realNewTitle.replaceAll("&","_");
    }

}
