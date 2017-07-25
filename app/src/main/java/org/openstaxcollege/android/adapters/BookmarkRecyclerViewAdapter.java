/**
 * Copyright (c) 2015 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.WebViewActivity;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.Bookmarks;
import org.openstaxcollege.android.utils.OSCUtil;

import java.util.ArrayList;

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;

/** Adapter to properly display bookmarks in RecyclerView
 * @author Ed Woodward
 * */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    /** List of Content objects to display*/
    private ArrayList<Content> contentList;
    Content content;
    static Context context;

    private int rowLayout;

    public BookmarkRecyclerViewAdapter(ArrayList<Content> content, int rowLayout, Context context)
    {
        contentList = content;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        content = contentList.get(i);
        viewHolder.title.setText(content.getTitle());
        viewHolder.other.setText(content.getContentString());
        if (viewHolder.logo != null && content.getIcon() != null)
        {
            viewHolder.logo.setImageResource(OSCUtil.getCoverId(content.getIcon(), context));

        }

    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }

    @Override
    public void onItemDismiss(int position)
    {
        Content currentContent = contentList.get(position);
        context.getContentResolver().delete(Bookmarks.CONTENT_URI, "_id="+ currentContent.getId(), null);
        contentList.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(context, "Bookmark deleted for " + currentContent.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition)
    {
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView logo;
        public TextView title;
        public TextView other;
        public View view;
        ArrayList<Content> contentList;

        public ViewHolder(View itemView, ArrayList<Content> contentList)
        {
            super(itemView);
            view = itemView;
            this.contentList = contentList;

            logo = (ImageView) itemView.findViewById(R.id.logoView);
            title = (TextView)itemView.findViewById(R.id.bookName);
            other = (TextView)itemView.findViewById(R.id.other);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Content content = contentList.get(getAdapterPosition());
            Log.d("BRVA", "title: " + content.getBookTitle());
            Content bookTitle = OSCUtil.getTitle(content.getBookTitle(), context);
            if(bookTitle != null)
            {
                content.setBookUrl(bookTitle.getBookUrl());
            }
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebViewActivity.class);
            wv.putExtra(v.getContext().getString(R.string.webcontent), content);

            context.startActivity(wv);
        }


    }
}
