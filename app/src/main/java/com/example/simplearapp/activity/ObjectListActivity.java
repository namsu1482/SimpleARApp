package com.example.simplearapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.simplearapp.Model.ObjectItem;
import com.example.simplearapp.ObjectListAdapter;
import com.example.simplearapp.R;

import java.util.ArrayList;

public class ObjectListActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;
    ArrayList<ObjectItem> mObjectList;
    ObjectListAdapter mObjectListAdapter;
    GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);
        mContext = this;
        initView();
    }

    private void initView() {
        TypedArray list = getResources().obtainTypedArray(R.array.object_items);
        mObjectList = new ArrayList<>();
        for (int i = 0; i < list.length(); i++) {
            Drawable image = mContext.getResources().getDrawable(list.getResourceId(i, -1), getTheme());
            ObjectItem objectItem = new ObjectItem(image);
            mObjectList.add(objectItem);
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mObjectListAdapter = new ObjectListAdapter(mObjectList);
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mObjectListAdapter);
    }
}
