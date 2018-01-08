package com.opp.fangla.terznica.data.entities;

import android.graphics.Bitmap;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

public class Advert {

    private String name, description;
    private Bitmap picture;
    private double cijena;
    private Date date;
    private int id, categoryId, vendorId;
    private List<SimplePerson> persons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public List<SimplePerson> getPersons() {
        return persons;
    }

    public void setPersons(List<SimplePerson> persons) {
        this.persons = persons;
    }
}
