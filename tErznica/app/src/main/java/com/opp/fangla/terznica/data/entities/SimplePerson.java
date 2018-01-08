package com.opp.fangla.terznica.data.entities;

import android.graphics.Bitmap;

public class SimplePerson {

    private Bitmap picture;
    private String name;
    private int id;

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
