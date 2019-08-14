/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.BookList
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.handlers.JsonHelper

import java.util.Collections

/**
 * General Utility Class
 * @author Ed Woodward
 */
object OSCUtil
{
    var bookList: BookList? = null
    /**
     * Checks to see if there is a mobile or wifi connection
     * @param context - the current Context
     * @return true if there is a connection, otherwise false.
     */
    fun isConnected(context: Context): Boolean
    {
        var isConnected = false

        val conService = Context.CONNECTIVITY_SERVICE
        val cm = context.getSystemService(conService) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        if(ni != null)
        {
            isConnected = ni.isConnectedOrConnecting
        }

        return isConnected
    }

    fun getCoverId(icon: String, context: Context): Int
    {
        var coverId = 0

        if(icon == context.getString(R.string.physics_icon))
        {
            coverId = R.drawable.physics_lg
        }
        else if(icon == context.getString(R.string.sociology_icon))
        {
            coverId = R.drawable.sociology_lg
        }
        else if(icon == context.getString(R.string.biology_icon))
        {
            coverId = R.drawable.biology_lg
        }
        else if(icon == context.getString(R.string.concepts_icon))
        {
            coverId = R.drawable.concepts_biology_lg
        }
        else if(icon == context.getString(R.string.anatomy_icon))
        {
            coverId = R.drawable.anatomy_lg
        }
        else if(icon == context.getString(R.string.statistics_icon))
        {
            coverId = R.drawable.statistics_lg
        }
        else if(icon == context.getString(R.string.precalculus_icon))
        {
            coverId = R.drawable.precalculus_lg
        }
        else if(icon == context.getString(R.string.psychology_icon))
        {
            coverId = R.drawable.psychology_lg
        }
        else if(icon == context.getString(R.string.history_icon))
        {
            coverId = R.drawable.history_lg
        }
        else if(icon == context.getString(R.string.chemistry_icon))
        {
            coverId = R.drawable.chemistry_lg
        }
        else if(icon == context.getString(R.string.algebra_icon))
        {
            coverId = R.drawable.algebra_lg
        }
        else if(icon == context.getString(R.string.trig_icon))
        {
            coverId = R.drawable.trig_lg
        }
        else if(icon == context.getString(R.string.ap_physics_icon))
        {
            coverId = R.drawable.ap_physics_lg
        }
        else if(icon == context.getString(R.string.american_gov_icon))
        {
            coverId = R.drawable.american_gov
        }
        else if(icon == context.getString(R.string.calculus1_icon))
        {
            coverId = R.drawable.calculus1
        }
        else if(icon == context.getString(R.string.calculus2_icon))
        {
            coverId = R.drawable.calculus2
        }
        else if(icon == context.getString(R.string.calculus3_icon))
        {
            coverId = R.drawable.calculus3
        }
        else if(icon == context.getString(R.string.chemistry_atoms_icon))
        {
            coverId = R.drawable.chemistry_atoms
        }
        else if(icon == context.getString(R.string.prealgebra_icon))
        {
            coverId = R.drawable.prealgebra
        }
        else if(icon == context.getString(R.string.univ_physics1_icon))
        {
            coverId = R.drawable.university_physics_vol1
        }
        else if(icon == context.getString(R.string.univ_physics2_icon))
        {
            coverId = R.drawable.university_physics_vol2
        }
        else if(icon == context.getString(R.string.univ_physics3_icon))
        {
            coverId = R.drawable.university_physics_vol3
        }
        else if(icon == context.getString(R.string.astronomy_icon))
        {
            coverId = R.drawable.astronomy
        }
        else if(icon == context.getString(R.string.elem_algebra_icon))
        {
            coverId = R.drawable.elementary_algebra
        }
        else if(icon == context.getString(R.string.intermediate_algebra_icon))
        {
            coverId = R.drawable.intermediate_algebra
        }
        else if(icon == context.getString(R.string.microecon_2e_icon))
        {
            coverId = R.drawable.microeconomics_2e
        }
        else if(icon == context.getString(R.string.intro_business_stats_icon))
        {
            coverId = R.drawable.intro_business_statistics
        }
        else if(icon == context.getString(R.string.microbiology_icon))
        {
            coverId = R.drawable.microbiology
        }
        else if(icon == context.getString(R.string.econ_2e_icon))
        {
            coverId = R.drawable.econ_2e
        }
        else if(icon == context.getString(R.string.macro_2e_icon))
        {
            coverId = R.drawable.macro_2e
        }
        else if(icon == context.getString(R.string.ap_macro_2e_icon))
        {
            coverId = R.drawable.ap_macro_2e
        }
        else if(icon == context.getString(R.string.ap_micro_2e_icon))
        {
            coverId = R.drawable.ap_micro_2e
        }
        else if(icon == context.getString(R.string.biology_2e_icon))
        {
            coverId = R.drawable.biology_2e
        }
        else if(icon == context.getString(R.string.biology_ap_icon))
        {
            coverId = R.drawable.biology_ap
        }
        else if(icon == context.getString(R.string.fizyka_tom1))
        {
            coverId = R.drawable.fizyka_tom1
        }
        else if(icon == context.getString(R.string.fizyka_tom2))
        {
            coverId = R.drawable.fizyka_tom2
        }
        else if(icon == context.getString(R.string.fizyka_tom3))
        {
            coverId = R.drawable.fizyka_tom3
        }
        else if(icon == context.getString(R.string.macro_icon))
        {
            coverId = R.drawable.macro_econ_lg
        }
        else if(icon == context.getString(R.string.micro_icon))
        {
            coverId = R.drawable.micro_econ_lg
        }
        else if(icon == context.getString(R.string.ap_macro_icon))
        {
            coverId = R.drawable.ap_macro
        }
        else if(icon == context.getString(R.string.ap_micro_icon))
        {
            coverId = R.drawable.ap_micro
        }
        else if(icon == context.getString(R.string.intro_business_icon))
        {
            coverId = R.drawable.intro_to_business
        }
        else if(icon == context.getString(R.string.business_ethics_icon))
        {
            coverId = R.drawable.business_ethics
        }
        else if(icon == context.getString(R.string.accounting_vol_1_icon))
        {
            coverId = R.drawable.principles_of_acounting_volume_1
        }
        else if(icon == context.getString(R.string.accounting_vol_2_icon))
        {
            coverId = R.drawable.principles_of_acounting_volume_2
        }
        else if(icon == context.getString(R.string.management_icon))
        {
            coverId = R.drawable.principles_of_management
        }
        else if(icon == context.getString(R.string.chem_atoms_2e_icon))
        {
            coverId = R.drawable.chemistry_atoms_first_2e
        }
        else if(icon == context.getString(R.string.chemistry_2e_icon))
        {
            coverId = R.drawable.chemistry_2e
        }
        else if(icon == context.getString(R.string.amer_gov_2e_icon))
        {
            coverId = R.drawable.american_government_2e
        }
        else if(icon == context.getString(R.string.organizational_behavior_icon))
        {
            coverId = R.drawable.organizational_behavior
        }

        return coverId
    }

    fun makeNoDataToast(context: Context)
    {
        Toast.makeText(context, "No data connection", Toast.LENGTH_LONG).show()
    }

    fun getTitle(title: String, context: Context): Content?
    {
        var title = title
        //Log.d("OSCUtil", "title: " + title);
        if(title == "Fizyka dla szkół wyższych Tom 3")
        {
            title = "Fizyka dla szkół wyższych. Tom 3"
        }
        else if(title == "Fizyka dla szkół wyższych Tom 2")
        {
            title = "Fizyka dla szkół wyższych. Tom 2"
        }
        else if(title == "Fizyka dla szkół wyższych Tom 1")
        {
            title = "Fizyka dla szkół wyższych. Tom 1"
        }
        if(bookList == null)
        {
            val helper = JsonHelper()
            bookList = helper.getBookData(context, BookList::class.java, "bookList.json")
            Collections.sort(bookList!!.bookList!!)

        }
        return bookList!!.findTitle(title)

    }

}
