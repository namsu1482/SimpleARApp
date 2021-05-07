package com.example.simplearapp.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AppData;
import com.example.simplearapp.Model.ObjectItem;
import com.example.simplearapp.ObjectListAdapter;
import com.example.simplearapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

        parseObjectListJson();
        mRecyclerView = findViewById(R.id.recycler_view);
        mObjectListAdapter = new ObjectListAdapter(mObjectList);
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mObjectListAdapter);
    }

    private void parseObjectListJson() {
        if (AppData.getInstance().ObjectList == null) {
            AssetManager assetManager = getResources().getAssets();
            InputStream inputStream = null;
            String jsonString = "";
            try {
                inputStream = assetManager.open("object_list.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                jsonString = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject rootObject = new JSONObject(jsonString);
                JSONArray jsonArray = rootObject.optJSONArray("object_list");
                mObjectList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    ObjectItem objectItem = new ObjectItem(mContext
                            , jsonObject.optString("name")
                            , jsonObject.optString("object_type")
                            , jsonObject.optString("image"));
                    mObjectList.add(objectItem);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppData.getInstance().ObjectList = mObjectList;
        }
        mObjectList = AppData.getInstance().ObjectList;
    }
}