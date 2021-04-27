package com.example.simplearapp.Model;

import android.graphics.drawable.Drawable;

public class ObjectItem {
    String name;
    String path;
    Drawable image;

    public ObjectItem(String name, String path) {
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
