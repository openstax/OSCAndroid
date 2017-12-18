package org.openstaxcollege.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.adapters.SelectBookRecyclerViewAdapter;
import org.openstaxcollege.android.beans.BookList;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.utils.OSCUtil;

import java.util.ArrayList;


/**
 * Fragment for Display of list of books
 * @author Ed Woodward
 */
public class SelectBookFragment extends Fragment
{
    RecyclerView recyclerView;

    Activity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectBookFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();

        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.book_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        SelectBookRecyclerViewAdapter adapter = new SelectBookRecyclerViewAdapter(getContent(), R.layout.fragment_book, activity);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Content> getContent()
    {
        BookList bookList = OSCUtil.readJson(getActivity());
        return bookList.getBookList();
    }

}
