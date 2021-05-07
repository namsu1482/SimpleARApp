package com;

import com.example.simplearapp.ArObjectHelper;
import com.example.simplearapp.Model.ObjectItem;

import java.util.ArrayList;

public class AppData {
    private static final AppData ourInstance = new AppData();
    public ArObjectHelper.OBJECT_TYPE ObjectType = null;
    public ArrayList<ObjectItem> ObjectList = null;

    public static AppData getInstance() {
        return ourInstance;
    }

    private AppData() {
    }
}
