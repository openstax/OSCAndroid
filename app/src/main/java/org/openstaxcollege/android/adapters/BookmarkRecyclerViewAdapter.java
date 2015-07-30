/**
 * Copyright (c) 2015 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.WebViewActivity;
import org.openstaxcollege.android.beans.Content;

import java.util.List;

/** Adapter to properly display bookmarks in RecyclerView
 * @author Ed Woodward
 * */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>
{
    /** List of Content objects to display*/
    private List<Content> contentList;
    Content content;

    private int rowLayout;

    public BookmarkRecyclerViewAdapter(List<Content> content, int rowLayout)
    {
        contentList = content;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        content = contentList.get(i);
        //viewHolder.logo.setImageDrawable(content.getIconImage());
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
        }
        //set onClickListener here?
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Intent wv = new Intent(v.getContext(), WebViewActivity.class);
                wv.putExtra(v.getContext().getString(R.string.webcontent), content);

                v.getContext().startActivity(wv);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView logo;
        public TextView title;
        public View view;

        public ViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
//            view.setOnClickListener(new View.OnClickListener()
//            {
//                @Override public void onClick(View v)
//                {
//                    //
//                }
//            });
            logo = (ImageView) itemView.findViewById(R.id.logoView);
            title = (TextView)itemView.findViewById(R.id.bookName);
        }

    }
}
