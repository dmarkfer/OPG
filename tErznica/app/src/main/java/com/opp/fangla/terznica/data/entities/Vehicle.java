package com.opp.fangla.terznica.data.entities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.support.annotation.IntegerRes;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class Vehicle {

    private Integer id, category;
    private String registration, model;
    private MutableLiveData<Bitmap> image;
    private boolean validImage;

    public Vehicle(Bitmap image) {
        registration = new String();
        model = new String();
        this.image = new MutableLiveData<>();
        this.image.postValue(image);
    }

    public int getId() {
        return id;
    }

    public void setId(String idStr) {
        this.id = Integer.valueOf(idStr);
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

    public int getCategory() {
        return category;
    }

    public void setCategory(String cat) {
        Integer.valueOf(cat);
        this.category = Integer.valueOf(cat);
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
        try {
            if(vehicle.registration != null){
                result.put("registarskaOznaka", vehicle.registration);
            }
            if(vehicle.category != null){
                result.put("idKategorijaVozila", vehicle.category);
            }
            if(vehicle.model != null){
                result.put("opisVozila", vehicle.model);
            }
            if(vehicle.image.getValue() != null){
                result.put("slikaVozila", User.bitmapToString(vehicle.image.getValue()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
