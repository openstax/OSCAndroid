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

import java.util.ArrayList;
import java.util.List;

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;

/** Adapter to properly display bookmarks in RecyclerView
 * @author Ed Woodward
 * */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    /** List of Content objects to display*/
    private ArrayList<Content> contentList;
    Content content;
    Context context;

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
        if (viewHolder.logo != null && content.icon != null)
        {

            //set correct icon based on URL
            if(content.getIcon().equals("physics"))
            {
                viewHolder.logo.setImageResource(R.drawable.physics_lg);
            }
            else if(content.getIcon().equals("sociology"))
            {
                viewHolder.logo.setImageResource(R.drawable.sociology_lg);
            }
            else if(content.getIcon().equals("biology"))
            {
                viewHolder.logo.setImageResource(R.drawable.biology_lg);
            }
            else if(content.getIcon().equals("concepts") )
            {
                viewHolder.logo.setImageResource(R.drawable.concepts_biology_lg);
            }
            else if(content.getIcon().equals("anatomy"))
            {
                viewHolder.logo.setImageResource(R.drawable.anatomy_lg);
            }
            else if(content.getIcon().equals("statistics"))
            {
                viewHolder.logo.setImageResource(R.drawable.statistics_lg);
            }
            else if(content.getIcon().equals("econ"))
            {
                viewHolder.logo.setImageResource(R.drawable.econ_lg);
            }
            else if(content.getIcon().equals("macro"))
            {
                viewHolder.logo.setImageResource(R.drawable.macro_econ_lg);
            }
            else if(content.getIcon().equals("micro"))
            {
                viewHolder.logo.setImageResource(R.drawable.micro_econ_lg);
            }
            else if(content.getIcon().equals("precalculus"))
            {
                viewHolder.logo.setImageResource(R.drawable.precalculus_lg);
            }
            else if(content.getIcon().equals("psychology"))
            {
                viewHolder.logo.setImageResource(R.drawable.psychology_lg);
            }
            else if(content.getIcon().equals("history"))
            {
                viewHolder.logo.setImageResource(R.drawable.history_lg);
            }
            else if(content.getIcon().equals("chemistry"))
            {
                viewHolder.logo.setImageResource(R.drawable.chemistry_lg);
            }
            else if(content.getIcon().equals("algebra"))
            {
                viewHolder.logo.setImageResource(R.drawable.algebra_lg);
            }
            else if(content.getIcon().equals("trig"))
            {
                viewHolder.logo.setImageResource(R.drawable.trig_lg);
            }
            else if(content.getIcon().equals("ap-physics"))
            {
                viewHolder.logo.setImageResource(R.drawable.ap_physics_lg);
            }
        }

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override public void onClick(View v)
//            {
//                contentList.get(i);
//                Intent wv = new Intent(context, WebViewActivity.class);
//                wv.putExtra(v.getContext().getString(R.string.webcontent), content);
//
//                context.startActivity(wv);
//            }
//        });
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
        public View view;
        ArrayList<Content> contentList;

        public ViewHolder(View itemView, ArrayList<Content> contentList)
        {
            super(itemView);
            view = itemView;
            this.contentList = contentList;

            logo = (ImageView) itemView.findViewById(R.id.logoView);
            title = (TextView)itemView.findViewById(R.id.bookName);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Content content = contentList.get(getAdapterPosition());
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebViewActivity.class);
            wv.putExtra(v.getContext().getString(R.string.webcontent), content);

            context.startActivity(wv);
        }


    }
}
