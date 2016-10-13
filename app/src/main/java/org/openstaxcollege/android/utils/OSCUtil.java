/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.openstaxcollege.android.R;

/**
 * General Utility Class
 * @author Ed Woodward
 *
 */
public class OSCUtil
{
    /**
     * Checks to see if there is a mobile or wifi connection
     * @param context - the current Context
     * @return true if there is a connection, otherwise false.
     */
    public static boolean isConnected(Context context)
    {
        boolean isConnected = false;
        
        String conService = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(conService);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null)
        {
            isConnected = ni.isConnectedOrConnecting();
        }
        
        return isConnected;
    }

   public static int getCoverId(String icon, Context context)
   {
       int coverId = 0;

       if(icon.equals(context.getString(R.string.physics_icon)))
       {
           coverId = R.drawable.physics_lg;
       }
       else if(icon.equals(context.getString(R.string.sociology_icon)))
       {
           coverId = R.drawable.sociology_lg;
       }
       else if(icon.equals(context.getString(R.string.biology_icon)))
       {
           coverId = R.drawable.biology_lg;
       }
       else if(icon.equals(context.getString(R.string.concepts_icon)) )
       {
           coverId = R.drawable.concepts_biology_lg;
       }
       else if(icon.equals(context.getString(R.string.anatomy_icon)))
       {
           coverId = R.drawable.anatomy_lg;
       }
       else if(icon.equals(context.getString(R.string.statistics_icon)))
       {
           coverId = R.drawable.statistics_lg;
       }
       else if(icon.equals(context.getString(R.string.econ_icon)))
       {
           coverId = R.drawable.econ_lg;
       }
       else if(icon.equals(context.getString(R.string.macro_icon)))
       {
           coverId = R.drawable.macro_econ_lg;
       }
       else if(icon.equals(context.getString(R.string.micro_icon)))
       {
           coverId = R.drawable.micro_econ_lg;
       }
       else if(icon.equals(context.getString(R.string.precalculus_icon)))
       {
           coverId = R.drawable.precalculus_lg;
       }
       else if(icon.equals(context.getString(R.string.psychology_icon)))
       {
           coverId = R.drawable.psychology_lg;
       }
       else if(icon.equals(context.getString(R.string.history_icon)))
       {
           coverId = R.drawable.history_lg;
       }
       else if(icon.equals(context.getString(R.string.chemistry_icon)))
       {
           coverId = R.drawable.chemistry_lg;
       }
       else if(icon.equals(context.getString(R.string.algebra_icon)))
       {
           coverId = R.drawable.algebra_lg;
       }
       else if(icon.equals(context.getString(R.string.trig_icon)))
       {
           coverId = R.drawable.trig_lg;
       }
       else if(icon.equals(context.getString(R.string.ap_physics_icon)))
       {
           coverId = R.drawable.ap_physics_lg;
       }
       else if(icon.equals(context.getString(R.string.ap_macro_icon)))
       {
           coverId = R.drawable.ap_macro;
       }
       else if(icon.equals(context.getString(R.string.ap_micro_icon)))
       {
           coverId = R.drawable.ap_micro;
       }
       else if(icon.equals(context.getString(R.string.american_gov_icon)))
       {
           coverId = R.drawable.american_gov;
       }
       else if(icon.equals(context.getString(R.string.calculus1_icon)))
       {
           coverId = R.drawable.calculus1;
       }
       else if(icon.equals(context.getString(R.string.calculus2_icon)))
       {
           coverId = R.drawable.calculus2;
       }
       else if(icon.equals(context.getString(R.string.calculus3_icon)))
       {
           coverId = R.drawable.calculus3;
       }
       else if(icon.equals(context.getString(R.string.chemistry_atoms_icon)))
       {
           coverId = R.drawable.chemistry_atoms;
       }
       else if(icon.equals(context.getString(R.string.prealgebra_icon)))
       {
           coverId = R.drawable.prealgebra;
       }
       else if(icon.equals(context.getString(R.string.univ_physics1_icon)))
       {
           coverId = R.drawable.university_physics_vol1;
       }
       else if(icon.equals(context.getString(R.string.univ_physics2_icon)))
       {
           coverId = R.drawable.university_physics_vol2;
       }
       else if(icon.equals(context.getString(R.string.univ_physics3_icon)))
       {
           coverId = R.drawable.university_physics_vol3;
       }

       return coverId;
   }
    
    public static void makeNoDataToast(Context context)
    {
        Toast.makeText(context, "No data connection",  Toast.LENGTH_LONG).show();
    }
    


}
