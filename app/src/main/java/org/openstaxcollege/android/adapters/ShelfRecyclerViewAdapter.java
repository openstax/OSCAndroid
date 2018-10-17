/**
 * Copyright (c) 2017 Rice University
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
import org.openstaxcollege.android.providers.ShelfBooks;
import org.openstaxcollege.android.utils.OSCUtil;

import java.util.ArrayList;

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;

/** Adapter to properly display bookshelf in RecyclerView
 * @author Ed Woodward
 * */

public class ShelfRecyclerViewAdapter extends RecyclerView.Adapter<ShelfRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    private ArrayList<Content> contentList;
    private Context context;

    private int rowLayout;

    public ShelfRecyclerViewAdapter(ArrayList<Content> content, int rowLayout, Context context)
    {
        contentList = content;
        //Log.d("SRVA","Content size = " + contentList.size());
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ShelfRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ShelfRecyclerViewAdapter.ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(ShelfRecyclerViewAdapter.ViewHolder viewHolder, int i)
    {
        //Log.d("SRVA","BVH: Content size = " + contentList.size());
        Content content = contentList.get(i);
        viewHolder.bookTitle.setText(content.getTitle());
        //viewHolder.other.setText(content.getContentString());
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
        //Log.d("SRVA","Dismiss: Content size = " + contentList.size());
        //Log.d("SRVA","Dismiss: position = " + position);
        if(contentList.size() >= position + 1)
        {
            Content currentContent = contentList.get(position);
            context.getContentResolver().delete(ShelfBooks.CONTENT_URI, "_id="+ currentContent.getId(), null);

            contentList.remove(position);

            notifyItemRemoved(position);
            Toast.makeText(context, currentContent.getTitle() + context.getString(R.string.removed_from_bookshelf), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition)
    {
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView logo;
        private TextView bookTitle;
        public View view;
        ArrayList<Content> contentList;

        private ViewHolder(View itemView, ArrayList<Content> contentList)
        {
            super(itemView);
            view = itemView;
            this.contentList = contentList;

            logo = (ImageView) itemView.findViewById(R.id.logoView);
            bookTitle = (TextView)itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Content content = contentList.get(getAdapterPosition());
            //Log.d("BRVA", "title: " + content.getBookTitle());
            Content bookTitle = OSCUtil.getTitle(content.getBookTitle(), v.getContext());
            if(bookTitle != null)
            {
                content.setBookUrl(bookTitle.getBookUrl());
            }
            if(OSCUtil.isConnected(context))
            {
                Context context = v.getContext();
                Intent wv = new Intent(v.getContext(), WebViewActivity.class);
                wv.putExtra(v.getContext().getString(R.string.webcontent), content);

                context.startActivity(wv);
            }
            else
            {
                Toast.makeText(context, context.getString(R.string.no_connextion), Toast.LENGTH_SHORT).show();
            }
        }


    }
}
