/**
 * Copyright (c) 2017 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */

package org.openstaxcollege.android.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import org.openstaxcollege.android.R
import org.openstaxcollege.android.activity.WebViewActivity
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.providers.ShelfBooks
import org.openstaxcollege.android.utils.OSCUtil

import java.util.ArrayList

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter

/** Adapter to properly display bookshelf in RecyclerView
 * @author Ed Woodward
 */

class ShelfRecyclerViewAdapter(private val contentList: ArrayList<Content>?, private val rowLayout: Int, private val context: Context) //Log.d("SRVA","Content size = " + contentList.size());
    : RecyclerView.Adapter<ShelfRecyclerViewAdapter.ViewHolder>(), ItemTouchHelperAdapter
{

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder
    {
        val v = LayoutInflater.from(viewGroup.context).inflate(rowLayout, viewGroup, false)
        return ViewHolder(v, contentList!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int)
    {
        //Log.d("SRVA","BVH: Content size = " + contentList.size());
        val content = contentList!![i]
        viewHolder.bookTitle.text = content.title
        //viewHolder.other.setText(content.getContentString());
        if(viewHolder.logo != null && content.icon != null)
        {
            viewHolder.logo.setImageResource(OSCUtil.getCoverId(content.icon!!, context))

        }

    }

    override fun getItemCount(): Int
    {
        return contentList?.size ?: 0
    }

    override fun onItemDismiss(position: Int)
    {
        //Log.d("SRVA","Dismiss: Content size = " + contentList.size());
        //Log.d("SRVA","Dismiss: position = " + position);
        if(contentList!!.size >= position + 1)
        {
            val currentContent = contentList[position]
            context.contentResolver.delete(ShelfBooks.CONTENT_URI, "_id=" + currentContent.id, null)

            contentList.removeAt(position)

            notifyItemRemoved(position)
            Toast.makeText(context, currentContent.title!! + context.getString(R.string.removed_from_bookshelf), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    {
        return true
    }

    inner class ViewHolder constructor(var view: View, internal var contentList: ArrayList<Content>) : RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val logo: ImageView
        val bookTitle: TextView

        init
        {

            logo = view.findViewById<View>(R.id.logoView) as ImageView
            bookTitle = view.findViewById<View>(R.id.title) as TextView
            view.setOnClickListener(this)
        }


        override fun onClick(v: View)
        {
            val content = contentList[adapterPosition]
            //Log.d("BRVA", "title: " + content.getBookTitle());
            val bookTitle = OSCUtil.getTitle(content.bookTitle, v.context)
            if(bookTitle != null)
            {
                content.bookUrl = bookTitle.bookUrl
            }
            if(OSCUtil.isConnected(context))
            {
                val context = v.context
                val wv = Intent(v.context, WebViewActivity::class.java)
                wv.putExtra(v.context.getString(R.string.webcontent), content)

                context.startActivity(wv)
            }
            else
            {
                Toast.makeText(context, context.getString(R.string.no_connextion), Toast.LENGTH_SHORT).show()
            }
        }


    }
}
