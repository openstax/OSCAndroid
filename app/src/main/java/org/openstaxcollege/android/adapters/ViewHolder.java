/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.adapters;

import org.openstaxcollege.android.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class to hold view to improve performance of list rendering
 * @author Ed Woodward
 *
 */
public class ViewHolder
{
    /**
     * image view for listview
     */
    protected ImageView imageView;
    /**
     * TextView for title in ListView
     */
    protected TextView textView;
    /**
     * TextView for other information in ListView
     */
    protected TextView otherView;

    ViewHolder(View base)
    {
        imageView = (ImageView) base.findViewById(R.id.logoView);
        textView = (TextView) base.findViewById(R.id.lensName);
        otherView = (TextView) base.findViewById(R.id.lensOther);

    }

}
