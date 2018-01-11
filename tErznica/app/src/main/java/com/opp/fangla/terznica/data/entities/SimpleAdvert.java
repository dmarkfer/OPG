package com.opp.fangla.terznica.data.entities;

import android.graphics.Bitmap;

import java.util.Date;

public class SimpleAdvert {

    private long id;
    private Bitmap image;
    private String title;
    private Integer value;
    private Date date;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {return this.date;}

    public void setDate (Date date) {
        this.date = date;
    }

}
