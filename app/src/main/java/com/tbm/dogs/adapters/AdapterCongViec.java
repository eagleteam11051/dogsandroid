package com.tbm.dogs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tbm.dogs.R;

import java.util.HashMap;
import java.util.List;

public class AdapterCongViec extends BaseExpandableListAdapter {
    private static final String TAG = "CustomAdapter";
    Context context;
    List<String> headerGroup;
    HashMap<String,List<String>> child;

    public AdapterCongViec(Context context, List<String> headerGroup, HashMap<String,List<String>> child){
        this.context = context;
        this.headerGroup = headerGroup;
        this.child =  child;
    }

    @Override
    public int getGroupCount() {
        return headerGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return child.get(headerGroup.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headerGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return child.get(headerGroup.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater li = LayoutInflater.from(context);
            view = li.inflate(R.layout.group_layout, viewGroup, false);
        }
        TextView tHeader = (TextView) view.findViewById(R.id.tHeader);
        tHeader.setText(headerGroup.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater li = LayoutInflater.from(context);
            view = li.inflate(R.layout.item_row_layout, viewGroup, false);
        }

        TextView tItem = (TextView) view.findViewById(R.id.tItem);
        TextView tTime = (TextView) view.findViewById(R.id.tTime);
        tItem.setText(((String)getChild(i, i1)));
        tTime.setText("04:34, 08-07-2018");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
