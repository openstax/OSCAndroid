/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Ed Woodward
 * 
 * Custom ImgageView for initial display of book covers
 *
 */
public class OSCImageView extends ImageView 
{
	public OSCImageView(Context context) 
	{
        super(context);
    }

    public OSCImageView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
    }

    public OSCImageView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); 
    }

}
