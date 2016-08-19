/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.activity.WebViewActivity;
import org.openstaxcollege.android.beans.Content;

import java.util.ArrayList;

/**
 * Adapter for displaying list of books
 * @author Ed Woodward
 */
public class LandingListRecyclerViewAdapter extends RecyclerView.Adapter<LandingListRecyclerViewAdapter.ViewHolder>
{

    private int rowLayout;
    Context context;
    private ArrayList<Content> contentList;

    public LandingListRecyclerViewAdapter(ArrayList<Content> content, int rowLayout, Context context)
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
        //Log.d("Adapter", "book desc: " + book.getDesc());
        holder.bookTitle.setText(book.getBookTitle());
        holder.desc.setText(Html.fromHtml(book.getContentString()));
        if (holder.logo != null && book.getIcon() != null)
        {

            //set correct icon based on URL
            if(book.getIcon().equals(context.getString(R.string.physics_icon)))
            {
                holder.logo.setImageResource(R.drawable.physics_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.sociology_icon)))
            {
                holder.logo.setImageResource(R.drawable.sociology_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.biology_icon)))
            {
                holder.logo.setImageResource(R.drawable.biology_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.concepts_icon)) )
            {
                holder.logo.setImageResource(R.drawable.concepts_biology_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.anatomy_icon)))
            {
                holder.logo.setImageResource(R.drawable.anatomy_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.statistics_icon)))
            {
                holder.logo.setImageResource(R.drawable.statistics_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.econ_icon)))
            {
                holder.logo.setImageResource(R.drawable.econ_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.macro_icon)))
            {
                holder.logo.setImageResource(R.drawable.macro_econ_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.micro_icon)))
            {
                holder.logo.setImageResource(R.drawable.micro_econ_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.precalculus_icon)))
            {
                holder.logo.setImageResource(R.drawable.precalculus_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.psychology_icon)))
            {
                holder.logo.setImageResource(R.drawable.psychology_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.history_icon)))
            {
                holder.logo.setImageResource(R.drawable.history_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.chemistry_icon)))
            {
                holder.logo.setImageResource(R.drawable.chemistry_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.algebra_icon)))
            {
                holder.logo.setImageResource(R.drawable.algebra_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.trig_icon)))
            {
                holder.logo.setImageResource(R.drawable.trig_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.ap_physics_icon)))
            {
                holder.logo.setImageResource(R.drawable.ap_physics_lg);
            }
            else if(book.getIcon().equals(context.getString(R.string.ap_macro_icon)))
            {
                holder.logo.setImageResource(R.drawable.ap_macro);
            }
            else if(book.getIcon().equals(context.getString(R.string.ap_micro_icon)))
            {
                holder.logo.setImageResource(R.drawable.ap_micro);
            }
            else if(book.getIcon().equals(context.getString(R.string.american_gov_icon)))
            {
                holder.logo.setImageResource(R.drawable.american_gov);
            }
            else if(book.getIcon().equals(context.getString(R.string.calculus1_icon)))
            {
                holder.logo.setImageResource(R.drawable.calculus1);
            }
            else if(book.getIcon().equals(context.getString(R.string.calculus2_icon)))
            {
                holder.logo.setImageResource(R.drawable.calculus2);
            }
            else if(book.getIcon().equals(context.getString(R.string.calculus3_icon)))
            {
                holder.logo.setImageResource(R.drawable.calculus3);
            }
            else if(book.getIcon().equals(context.getString(R.string.chemistry_atoms_icon)))
            {
                holder.logo.setImageResource(R.drawable.chemistry_atoms);
            }
            else if(book.getIcon().equals(context.getString(R.string.prealgebra_icon)))
            {
                holder.logo.setImageResource(R.drawable.prealgebra);
            }
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
        private TextView desc;
        private ImageView logo;
        ArrayList<Content> contentList;

        public ViewHolder(View itemView, ArrayList<Content> contentList)
        {
            super(itemView);
            this.contentList = contentList;
            bookTitle = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.details);
            logo = (ImageView) itemView.findViewById(R.id.logoView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Content book = contentList.get(position);
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebViewActivity.class);
            wv.putExtra(context.getString(R.string.webcontent), book);

            context.startActivity(wv);
        }
    }
}
