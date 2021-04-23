package com.example.simplearapp.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Util {
    private static final String TAG = "Util";
    private static final double MIN_OPENGL_VERSION = 3.0;

    /**
     * Sceneform을 실행할 수 없으면 false 반환
     *
     * @param activity
     * @return
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        // SDK 최소 버전을 만족하지 못하면 False Return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity.getApplicationContext(), "Sceneform requires Android N or later.", Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "Sceneform requires Android N or later.");
            activity.finish();
            return false;
        }

        // OpenGL 최소 버전을 만족하지 못하면 False Return
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();

        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity.getApplicationContext(), "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "Sceneform requires OpenGL ES 3.0 or later");
            activity.finish();
            return false;
        }
        return true;
    }
}
