/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;
import org.openstaxcollege.android.providers.ShelfBooks;
import org.openstaxcollege.android.utils.OSCUtil;

import java.util.ArrayList;

/**
 * Adapter for displaying list of books
 * @author Ed Woodward
 */
public class SelectBookRecyclerViewAdapter extends RecyclerView.Adapter<SelectBookRecyclerViewAdapter.ViewHolder>
{

    private int rowLayout;
    private Context context;
    private ArrayList<Content> contentList;

    public SelectBookRecyclerViewAdapter(ArrayList<Content> content, int rowLayout, Context context)
    {
        contentList = content;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Content book = contentList.get(position);
        holder.bookTitle.setText(book.getBookTitle());
        if (holder.logo != null && book.getIcon() != null)
        {
            holder.logo.setImageResource(OSCUtil.INSTANCE.getCoverId(book.getIcon(), context));

        }
    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView bookTitle;
        private ImageView logo;
        ArrayList<Content> contentList;

        public ViewHolder(View itemView, ArrayList<Content> contentList)
        {
            super(itemView);
            this.contentList = contentList;
            bookTitle = (TextView) itemView.findViewById(R.id.title);
            logo = (ImageView) itemView.findViewById(R.id.logoView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Content content = contentList.get(getAdapterPosition());
            ContentValues cv = new ContentValues();

            //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
            cv.put(ShelfBooks.TITLE, content.getTitle());
            //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
            if(content.getUrl() != null)
            {
                String url = content.getUrl();
                cv.put(ShelfBooks.URL, url.replaceAll("@\\d+(\\.\\d+)?", ""));
            }
            else
            {
                Toast.makeText(context, context.getString(R.string.bookmark_failure), Toast.LENGTH_SHORT).show();
            }
            cv.put(ShelfBooks.ICON, content.getIcon());
            cv.put(ShelfBooks.OTHER, content.getBookTitle());
            context.getContentResolver().insert(ShelfBooks.CONTENT_URI, cv);
            Toast.makeText(context,content.getTitle() + context.getString(R.string.added_to_bookshelf), Toast.LENGTH_SHORT).show();
        }
    }
}
