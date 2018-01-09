package com.opp.fangla.terznica.data.entities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import org.json.JSONObject;

public class Vehicle {

    private int id;
    private String registration, model, description, category;
    private MutableLiveData<Bitmap> image;
    private boolean validImage;

    public Vehicle(Bitmap image) {
        registration = new String();
        model = new String();
        description = new String();
        category = new String();
        this.image = new MutableLiveData<>();
        this.image.postValue(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image.postValue(image);
        validImage = true;
    }

    public boolean isValidImage() {
        return validImage;
    }

    public static JSONObject toJSON(Vehicle vehicle){
        JSONObject result = new JSONObject();
        //TODO
        return result;
    }
}
