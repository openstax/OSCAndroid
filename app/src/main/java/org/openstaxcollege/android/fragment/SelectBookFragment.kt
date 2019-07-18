package org.openstaxcollege.android.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.openstaxcollege.android.R
import org.openstaxcollege.android.adapters.SelectBookRecyclerViewAdapter
import org.openstaxcollege.android.beans.BookList
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.handlers.JsonHelper

import java.util.Collections


/**
 * Fragment for Display of list of books
 * @author Ed Woodward
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SelectBookFragment : Fragment()
{
    internal var recyclerView: RecyclerView? = null

    internal var activity: Activity? = null

    private val content: ArrayList<Content>?
        get()
        {
            val helper = JsonHelper()
            val bookList = helper.getBookData(activity!!, BookList::class.java, "bookList.json")
            if(bookList != null)
            {
                Collections.sort(bookList.bookList!!)
                return bookList.bookList
            }
            else
            {
                return ArrayList()
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        activity = getActivity()

        return inflater!!.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        recyclerView = getView()!!.findViewById(R.id.book_list)

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false
        val adapter = SelectBookRecyclerViewAdapter(content, R.layout.fragment_book, activity!!)
        recyclerView!!.adapter = adapter
    }

}
