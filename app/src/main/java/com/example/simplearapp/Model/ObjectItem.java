package com.example.simplearapp.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.simplearapp.ArObjectHelper;

public class ObjectItem {
    private String name;
    private Drawable image;
    private ArObjectHelper.OBJECT_TYPE object_type;
    private boolean isAnimateObject = false;

    public ArObjectHelper.OBJECT_TYPE getObject_type() {
        return object_type;
    }

    int imgId;

    public int getImgId() {
        return imgId;
    }

    public ObjectItem(Context context, String name, String object_type, String imageName) {
        this.name = name;
        switch (object_type.toUpperCase()) {
            case "CUBE":
                this.object_type = ArObjectHelper.OBJECT_TYPE.CUBE;
                break;
            case "CYLINDER":
                this.object_type = ArObjectHelper.OBJECT_TYPE.CYLINDER;
                break;
            case "SPHERE":
                this.object_type = ArObjectHelper.OBJECT_TYPE.SPHERE;
                break;
            case "RESOURCE":
                this.object_type = ArObjectHelper.OBJECT_TYPE.RESOURCE;
                isAnimateObject = true;
                break;
        }
        imgId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public ObjectItem(Drawable image) {
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAnimateObject() {
        return isAnimateObject;
    }
}
