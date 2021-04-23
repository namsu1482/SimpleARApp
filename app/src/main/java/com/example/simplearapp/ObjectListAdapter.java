package com.example.simplearapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ObjectListAdapter extends BaseAdapter {
    ArrayList<ObjectItem> arrayList = new ArrayList<>();

    public ObjectListAdapter(ArrayList<ObjectItem> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ObjectItem getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = null;
        View view;
        if (context == null) {
            context = parent.getContext();
        }
        ObjectItem objectItem = arrayList.get(position);
        objectItem.getName();
        objectItem.getPath();

        return convertView;
    }
}
