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
        String realNewTitle = title.replaceAll("\\p{Punct}", "");
        return realNewTitle;
    }
    
    /**
     * Fixes encoded spaces in connexions title saved in favs database
     * @param title - the part of the URL that is being used for the title
     * @return String - Connexions search with the search terms added
     */
    public static String getSearchTitle(String title)
    {
        StringBuilder sb = new StringBuilder();
        String newTitle = "";
        int wordsIndex = title.indexOf("words=");
        sb.append("Connexions search: ");
        int ampIndex = title.indexOf("&", wordsIndex);
        if(wordsIndex != -1 && ampIndex != -1)
        {
            newTitle = title.substring(wordsIndex+6, ampIndex);
        }
        else
        {
            newTitle = "Unknown Title";
        }
        try
        {
            newTitle = URLDecoder.decode(newTitle, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        sb.append(newTitle);
        return sb.toString();
    }
   

}
