/**
 * Copyright (c) 2013 Rice University
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details. 
 */
package org.openstaxcollege.android.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.openstaxcollege.android.R;
import org.openstaxcollege.android.beans.Content;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

/** Adapter to properly display the Lens list 
 * @author Ed Woodward
 * */
public class BookmarkListAdapter extends ArrayAdapter<Content> implements SectionIndexer
{
    /** Current context */
    private Context context;
    /** List of Content objects to display*/
    private ArrayList<Content> contentList;
    /**
     * used to create array of alpha characters for section indexer
     */
    private HashMap<String, Integer> alphaIndexer;
    /**
     * list of alpha characters for section indexer
     */
    private String[] sections;
    
    /**
     * Viewholder for better performance
     */
    ViewHolder holder;

    
    /**
     * Constructor
     * @param context - Current Context
     * @param contentList - ArrayList of Content objects
     */
    public BookmarkListAdapter(Context context, ArrayList <Content> contentList)
    {
        super(context, android.R.layout.simple_list_item_1, contentList);
        this.context = context;
        this.contentList = contentList;
        
        alphaIndexer = new HashMap<>();
        int size = contentList.size();

        for (int x = 0; x < size; x++) 
        {
            Content s = contentList.get(x);
            if(s == null || s.title == null)
            {
                break;
            }


            String ch =  s.title.substring(0, 1);
            if(!ch.equals(" "))
            {
                 // convert to uppercase otherwise lowercase a -z will be sorted after upper A-Z
                ch = ch.toUpperCase();

                // HashMap will prevent duplicates
                alphaIndexer.put(ch, x);
            }
        }

        Set<String> sectionLetters = alphaIndexer.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters); 

        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);

        //Log.d("LensListAdapter constructor", "content list size: " + contentList.size());
    }
    
    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     * Creates layout
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        View v = convertView;
        
        if (v == null) 
        {
            //Log.d("LensListAdapter.getView()", "view is null ");
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cnx_bookmark_list, null);
            holder = new ViewHolder(v);
           
            v.setTag(holder);
        }
        else
        {
            //Log.d("LensListAdapter.getView()", "view is NOT null ");
            holder= (ViewHolder)v.getTag();
            if(holder == null)
            {
                holder = new ViewHolder(v);
                v.setTag(holder);
            }
        }
        
        Content c = contentList.get(position);
        if(c != null)
        {
            //Log.d("LensListAdapter.getView()", "content is not null ");
            TextView text = holder.textView;
            TextView other = holder.otherView;
            holder.imageView.setTag(position);
            if (holder.imageView != null && c.icon != null)
            {
                //if(c.icon != null)
               //{
                    //set correct icon based on URL
                    if(c.getIcon().equals("physics"))
                    {
                        holder.imageView.setImageResource(R.drawable.physics_lg);
                    }
                    else if(c.getIcon().equals("sociology"))
                    {
                        holder.imageView.setImageResource(R.drawable.sociology_lg);
                    }
                    else if(c.getIcon().equals("biology"))
                    {
                        holder.imageView.setImageResource(R.drawable.biology_lg);
                    }
                    else if(c.getIcon().equals("concepts") )
                    {
                        holder.imageView.setImageResource(R.drawable.concepts_biology_lg);
                    }
                    else if(c.getIcon().equals("anatomy"))
                    {
                        holder.imageView.setImageResource(R.drawable.anatomy_lg);
                    }
                    else if(c.getIcon().equals("statistics"))
                    {
                        holder.imageView.setImageResource(R.drawable.statistics_lg);
                    }
                    else if(c.getIcon().equals("econ"))
                    {
                        holder.imageView.setImageResource(R.drawable.econ_lg);
                    }
                    else if(c.getIcon().equals("macro"))
                    {
                        holder.imageView.setImageResource(R.drawable.macro_econ_lg);
                    }
                    else if(c.getIcon().equals("micro"))
                    {
                        holder.imageView.setImageResource(R.drawable.micro_econ_lg);
                    }
                    else if(c.getIcon().equals("precalculus"))
                    {
                        holder.imageView.setImageResource(R.drawable.precalculus_lg);
                    }
                    else if(c.getIcon().equals("psychology"))
                    {
                        holder.imageView.setImageResource(R.drawable.psychology_lg);
                    }
                    else if(c.getIcon().equals("history"))
                    {
                        holder.imageView.setImageResource(R.drawable.history_lg);
                    }
                    else if(c.getIcon().equals("chemistry"))
                    {
                        holder.imageView.setImageResource(R.drawable.chemistry_lg);
                    }
                    else if(c.getIcon().equals("algebra"))
                    {
                        holder.imageView.setImageResource(R.drawable.algebra_lg);
                    }
                    else if(c.getIcon().equals("trig"))
                    {
                        holder.imageView.setImageResource(R.drawable.trig_lg);
                    }
                //}
            }
            if(text != null){
                holder.textView.setText(c.title);
            }
            if(other != null)
            {
                holder.otherView.setText(c.getContentString());
            }
        }
        return v;
    }
    
    /**  allows access to list of Content objects.
     *  Used so data can be stored by Activity when orientation is changed.
     *  Prevents data reload.
     *  
     *  @return ArrayList of Content objects
     * */
    public ArrayList<Content> getItems()
    {
        return contentList;
    }
    
    
    
    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getPositionForSection(int)
     */
    public int getPositionForSection(int section) 
    {
    	String letter;
        
    	if(section < sections.length)
    	{
    		letter = sections[section];
    	}
    	else
    	{
    		return 0;
    	}
        //Log.d("LensListAdapter ", "letter: " + letter);
        return alphaIndexer.get(letter);
    }

    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getSectionForPosition(int)
     */
    public int getSectionForPosition(int position) 
    {
        return 1;
    }

    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getSections()
     */
    public Object[] getSections() 
    {
         return sections;
    }
    
    




}
