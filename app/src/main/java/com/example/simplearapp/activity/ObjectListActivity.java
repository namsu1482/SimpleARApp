package com.example.simplearapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
        mObjectList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Drawable image = mContext.getResources().getDrawable(R.drawable.ic_launcher_foreground, getTheme());
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
