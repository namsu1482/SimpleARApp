package com.example.simplearapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.simplearapp.R;
import com.google.ar.sceneform.ux.ArFragment;

public class ArCoreActivity extends AppCompatActivity {
    private static final String TAG = ArCoreActivity.class.getSimpleName();

    private Context mContext;
    ArFragment mArFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_core);

        mContext = this;
    }

}
