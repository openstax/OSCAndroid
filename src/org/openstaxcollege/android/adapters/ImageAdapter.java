/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.adapters;

import org.openstaxcollege.android.R;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Adapter for book cover grid view
 * @author Ed Woodward
 *
 */
public class ImageAdapter extends BaseAdapter 
{
	private Context context;
	
	
	public Integer[] imageIds = {
            R.drawable.physics_lg, R.drawable.sociology_lg,
            R.drawable.biology_lg, R.drawable.concepts_biology_lg,
            R.drawable.anatomy_lg, R.drawable.statistics_lg,
            R.drawable.precalculus_lg, R.drawable.psychology_lg,
            R.drawable.econ_lg, R.drawable.chemistry_lg,
            R.drawable.history_lg, R.drawable.macro_econ_lg,
            R.drawable.micro_econ_lg};
	
	public ImageAdapter(Context c)
	{
		context = c;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() 
	{
		return imageIds.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) 
	{
		return imageIds[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//final int scaleImage=200;

		// Get the screen's density scale
		final float scale = context.getResources().getDisplayMetrics().density;
		
		//String scaleImage = context.getString(R.dimen.gridview);
		//Log.d("ImageAdator", "gridview = " + scaleImage);
		int size = 0;
		//Log.d("ImageAdator", "Size = " + size);
		//Log.d("ImageAdator", "Scale = " + scale);
		// Convert the dps to pixels, based on density scale
		//final int imgSize  = (int) (size * scale + 0.5f);
		//final int imgSize  = (int) (scaleImage * scale + 0.5f);
		//LayoutInflater infla=context.getgetLayoutInflater();

	    //View v=infla.inflate(R.layout.main1,null);

	    //ImageView iv1=(ImageView)v.findViewById(R.id.imageView1);
		ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIds[position]);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //DisplayMetrics metrics = new DisplayMetrics();
        //context.getDefaultDisplay().getMetrics(metrics);
//        switch(context.getResources().getDisplayMetrics().densityDpi){
//             case DisplayMetrics.DENSITY_LOW:
//                        imageView.setLayoutParams(new GridView.LayoutParams(lowVal, lowVal));
//                        break;
//             case DisplayMetrics.DENSITY_MEDIUM:
//                        imageView.setLayoutParams(new GridView.LayoutParams(medVal, medVal));
//                        break;
//             case DisplayMetrics.DENSITY_HIGH:
//                         imageView.setLayoutParams(new GridView.LayoutParams(highVal, highVal));
//                         break;
//        }
        if(Build.VERSION.SDK_INT <= 10)//add code to check for screen size of 480 px
        {
        	size = 280;
        }
        else
        {
        	size = (int) context.getResources().getDimension(R.dimen.width);
        	Log.d("ImageAdator", "Size = " + size);
        }
        imageView.setLayoutParams(new GridView.LayoutParams(size, size));
        return imageView;
	}

}
