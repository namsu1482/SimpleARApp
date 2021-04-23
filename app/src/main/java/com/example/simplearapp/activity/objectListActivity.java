package com.example.simplearapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.simplearapp.R;

public class objectListActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);
        mContext = this;
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
