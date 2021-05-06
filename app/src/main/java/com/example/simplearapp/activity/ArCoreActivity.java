package com.example.simplearapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.simplearapp.R;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class ArCoreActivity extends AppCompatActivity {
    private static final String TAG = ArCoreActivity.class.getSimpleName();

    private Context mContext;
    private Session mSession;

    private boolean mUserRequestInstall = true;

    ArFragment mArFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_core);

        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCameraPermission();
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(this, mUserRequestInstall)) {
                    case INSTALLED:
                        mSession = new Session(mContext);
                        Log.i(TAG, "Session is Created");
                        break;
                    case INSTALL_REQUESTED:
                        mUserRequestInstall = false;
                        Log.i(TAG, "Session creation is failed");
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.CAMERA}
                    , 0);
        }
    }
}
