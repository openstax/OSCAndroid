/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.adapters

import android.content.ContentValues
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.providers.ShelfBooks
import org.openstaxcollege.android.utils.OSCUtil

import java.util.ArrayList

/**
 * Adapter for displaying list of books
 * @author Ed Woodward
 */
class SelectBookRecyclerViewAdapter(private val contentList: ArrayList<Content>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<SelectBookRecyclerViewAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ViewHolder(v, contentList!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val book = contentList!![position]
        holder.bookTitle.text = book.bookTitle
        if(holder.logo != null && book.icon != null)
        {
            holder.logo.setImageResource(OSCUtil.getCoverId(book.icon!!, context))

        }
    }

    override fun getItemCount(): Int
    {
        return contentList?.size ?: 0
    }

    inner class ViewHolder(itemView: View, internal var contentList: ArrayList<Content>) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val bookTitle: TextView
        val logo: ImageView

        init
        {
            bookTitle = itemView.findViewById<View>(R.id.title) as TextView
            logo = itemView.findViewById<View>(R.id.logoView) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View)
        {
            //TODO: move database code to testable function
            val content = contentList[adapterPosition]
            val cv = ContentValues()

            //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
            cv.put(ShelfBooks.TITLE, content.title)
            //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
            if(content.url != null)
            {
                val url = content.url
                cv.put(ShelfBooks.URL, url!!.replace("@\\d+(\\.\\d+)?".toRegex(), ""))
            }
            else
            {
                Toast.makeText(context, context.getString(R.string.bookmark_failure), Toast.LENGTH_SHORT).show()
            }
            cv.put(ShelfBooks.ICON, content.icon)
            cv.put(ShelfBooks.OTHER, content.bookTitle)
            context.contentResolver.insert(ShelfBooks.CONTENT_URI, cv)
            Toast.makeText(context, content.title!! + context.getString(R.string.added_to_bookshelf), Toast.LENGTH_SHORT).show()
        }
    }
}
