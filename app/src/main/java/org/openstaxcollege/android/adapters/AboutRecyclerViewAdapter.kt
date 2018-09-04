package org.openstaxcollege.android.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.About

import java.util.ArrayList

class AboutRecyclerViewAdapter(private val contentList: ArrayList<About>, private val rowLayout: Int) : RecyclerView.Adapter<AboutRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(rowLayout, viewGroup, false)
        return ViewHolder(v, contentList)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val (title, blurb) = contentList!![i]
        //Log.d("rv","title: " + about.getTitle() + " details: "+ about.getBlurb());
        viewHolder.title.text = title
        viewHolder.blurb.text = Html.fromHtml(blurb)


    }

    override fun getItemCount(): Int {
        return contentList?.size ?: 0
    }


    class ViewHolder(itemView: View, internal var contentList: ArrayList<About>) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val blurb: TextView

        init {

            title = itemView.findViewById(R.id.title)
            blurb = itemView.findViewById(R.id.blurb)

        }

    }
}
