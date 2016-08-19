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
        viewHolder.other.setText(content.getContentString());
        if (viewHolder.logo != null && content.getIcon() != null)
        {

            //set correct icon based on URL
            if(content.getIcon().equals(context.getString(R.string.physics_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.physics_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.sociology_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.sociology_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.biology_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.biology_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.concepts_icon)) )
            {
                viewHolder.logo.setImageResource(R.drawable.concepts_biology_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.anatomy_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.anatomy_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.statistics_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.statistics_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.econ_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.econ_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.macro_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.macro_econ_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.micro_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.micro_econ_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.precalculus_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.precalculus_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.psychology_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.psychology_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.history_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.history_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.chemistry_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.chemistry_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.algebra_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.algebra_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.trig_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.trig_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.ap_physics_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.ap_physics_lg);
            }
            else if(content.getIcon().equals(context.getString(R.string.ap_macro_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.ap_macro);
            }
            else if(content.getIcon().equals(context.getString(R.string.ap_micro_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.ap_micro);
            }
            else if(content.getIcon().equals(context.getString(R.string.american_gov_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.american_gov);
            }
            else if(content.getIcon().equals(context.getString(R.string.calculus1_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.calculus1);
            }
            else if(content.getIcon().equals(context.getString(R.string.calculus2_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.calculus2);
            }
            else if(content.getIcon().equals(context.getString(R.string.calculus3_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.calculus3);
            }
            else if(content.getIcon().equals(context.getString(R.string.chemistry_atoms_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.chemistry_atoms);
            }
            else if(content.getIcon().equals(context.getString(R.string.prealgebra_icon)))
            {
                viewHolder.logo.setImageResource(R.drawable.prealgebra);
            }
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
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebViewActivity.class);
            wv.putExtra(v.getContext().getString(R.string.webcontent), content);

            context.startActivity(wv);
        }


    }
}
