package com;

import com.example.simplearapp.ArObjectHelper;

public class AppData {
    private static final AppData ourInstance = new AppData();
    public ArObjectHelper.OBJECT_TYPE ObjectType = null;

    public static AppData getInstance() {
        return ourInstance;
    }

    private AppData() {
    }
}
