package com.example.simplearapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.simplearapp.ArObjectHelper;
import com.example.simplearapp.R;
import com.example.simplearapp.Util.Util;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

// 링크 https://aidalab.tistory.com/64?category=805476
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQ_OBJECT_ITEM = 101;
    private Context mContext;

    //ArFragment는 AR 시스템의 상태를 관리하고 세션의 수명주기를 처리하는 ARCore API의 주요 진입점의 역할을 한다
    //ArFragment 클래스를 사용하여 세션의 생성, 구성, 시작 및 중지가 가능하며, 카메라 이미지와 장치, 포즈에 접근할 수 있는 프레임을 수신한다.
    private ArFragment arFragment;
    private Session mSession;
    private boolean mUserRequestInstall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Util.checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_main);
        mContext = this;
        Button btnObjectList = findViewById(R.id.btn_objectlist);
        btnObjectList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, ObjectListActivity.class), REQ_OBJECT_ITEM);
            }
        });

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        ArObjectHelper arObjectHelper = new ArObjectHelper(mContext, arFragment, ArObjectHelper.OBJECT_TYPE.SPHERE);
        arObjectHelper.setRenderable();
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_OBJECT_ITEM) {
        }
    }
}
