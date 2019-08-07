/**
 * Copyright (c) 2015 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.openstaxcollege.android.R
import org.openstaxcollege.android.adapters.BookmarkRecyclerViewAdapter
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.providers.Bookmarks
import org.openstaxcollege.android.providers.utils.DBUtils

import java.util.ArrayList
import java.util.Collections

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback

/**
 * Fragment for displaying cards of bookmarks
 * @author Ed Woodward
 */
class BookmarkFragment : Fragment(), OnStartDragListener
{
    /** Adaptor for card display  */
    internal var adapter: BookmarkRecyclerViewAdapter? = null
    internal var recyclerView: RecyclerView? = null
    /** list of bookmarks as Content objects  */
    internal var content: ArrayList<Content>? = null

    /**handler  */
    private val handler = Handler()

    /** Inner class for completing load work  */
    private val finishedLoadingListTask = Runnable { finishedLoadingList() }

    private var itemTouchHelper: ItemTouchHelper? = null

    internal var activity: Activity? = null

    /**
     * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private val dbCount: Int
        get()
        {
            val c = activity!!.contentResolver.query(Bookmarks.CONTENT_URI, null, null, null, null)
            val count = c!!.count
            c.close()
            return count

        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        activity = getActivity()


        return inflater!!.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        recyclerView = view!!.findViewById(R.id.book_list)

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false


        //get already retrieved feed and reuse if it is there
        content = activity!!.lastNonConfigurationInstance as ArrayList<Content>?
        if(content == null)
        {
            //no previous data, so database must be read
            readDB()
        }
        else
        {
            //reuse existing feed data
            adapter = BookmarkRecyclerViewAdapter(content, R.layout.bookmark, activity!!)
            recyclerView!!.adapter = adapter
            val callback = SimpleItemTouchHelperCallback(adapter)
            itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper!!.attachToRecyclerView(recyclerView)

        }
    }

    /**
     * For OnStartDragListener
     * @param viewHolder The holder of the view to drag.
     */
    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    {
        itemTouchHelper!!.startDrag(viewHolder)
    }

    override fun onResume()
    {
        super.onResume()
        //if database state has changed, reload the display
        if(content != null)
        {
            val dbCount = dbCount

            if(dbCount > content!!.size)
            {
                readDB()
            }
        }
    }

    protected fun finishedLoadingList()
    {
        recyclerView!!.adapter = adapter
        val callback = SimpleItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(recyclerView)
    }

    private fun readDB()
    {
        val loadFavsThread = object : Thread()
        {
            override fun run()
            {

                val order = "fav_icon DESC, fav_title ASC"

                content = DBUtils.readCursorIntoList(activity!!.contentResolver.query(Bookmarks.CONTENT_URI, null, null, null, order)!!)

                Collections.sort(content!!)

                fillData(content)
                handler.post(finishedLoadingListTask)
            }
        }
        loadFavsThread.start()

    }

    /**
     * Puts data in adapter
     * @param contentList ArrayList<Content>
    </Content> */
    private fun fillData(contentList: ArrayList<Content>?)
    {
        //Log.d("LensViewer", "fillData() called");
        adapter = BookmarkRecyclerViewAdapter(contentList, R.layout.bookmark, activity!!)
    }
}
