/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Utility class for MenuHandler
 * @author Ed Woodward
 *
 */
public class MenuUtil
{
    /**
     * Replaces spaces in title with underscores and truncates to 20 chars if needed
     * @param title - The title to modify
     * @return String - the given title with spaces replaced by underscores and truncated to 20 chars if longer that 20 chars.
     */
    public static String getTitle(String title)
    {
    	if(title == null)
    	{
    		return "";
    	}
        String realNewTitle = title.replaceAll("&","_");
        realNewTitle = realNewTitle.replaceAll(" ", "_");
        return realNewTitle.replaceAll("\\p{Punct}", "");
        //return realNewTitle.replaceAll("&","_");
    }
    
}
