package com.opp.fangla.terznica.data.entities;

import android.graphics.Bitmap;

/**
 * Created by domagoj on 29.12.17..
 */

public class SimpleAdvert {

    private long id;
    private Bitmap image;
    private String title, description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
