package com.opp.fangla.terznica.data.entities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class Vehicle {

    private Integer id, category;
    private String registration, model, description;
    private MutableLiveData<Bitmap> image;
    private boolean validImage;

    public Vehicle(Bitmap image) {
        registration = new String();
        model = new String();
        description = new String();
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
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
        try {
            if(vehicle.registration != null){
                result.put("registarskaOznaka", vehicle.registration);
            }
            if(vehicle.category != null){
                result.put("idKategorijaVozila", vehicle.category);
            }
            if(vehicle.description != null){
                result.put("opisVozila", vehicle.model);
            }
            if(vehicle.description != null){
                result.put("opisPrijevoza", vehicle.description);
            }
            if(vehicle.image.getValue() != null){
                result.put("slikaVozila", Base64.encodeToString(vehicle.image.getValue().getNinePatchChunk(), Base64.DEFAULT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
