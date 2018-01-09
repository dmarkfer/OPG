package com.opp.fangla.terznica.data.entities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.google.android.gms.location.places.Place;

import org.json.JSONException;
import org.json.JSONObject;

public class Vendor {

    //private int id;
    private String name, description, idNum, bankAccount;
    private MutableLiveData<Bitmap> image;
    private MutableLiveData<Place> address;
    private boolean validImage;

    public Vendor(Bitmap image) {
        name = new String();
        description = new String();
        idNum = new String();
        bankAccount = new String();
        this.image = new MutableLiveData<>();
        this.image.postValue(image);
        address = new MutableLiveData<>();
    }

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

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

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image.postValue(image);
        validImage = true;
    }

    public LiveData<Place> getAddress() {
        return address;
    }

    public void setAddress(Place address) {
        this.address.postValue(address);
    }

    public boolean isValidImage() {
        return validImage;
    }

    public static JSONObject toJSON(Vendor vendor){
        JSONObject result = new JSONObject();

        try {
            if(vendor.name != null) {
                result.put("nazivOPG", vendor.name);
            }
            if(vendor.idNum != null){
                result.put("OIBOPG", vendor.idNum);
            }
            if(vendor.address.getValue() != null){
                result.put("adresaOPG", placeJSON(vendor.address.getValue()));
            }
            if(vendor.description != null){
                result.put("opisOPG", vendor.description);
            }
            if(vendor.bankAccount != null){
                result.put("IBAN", vendor.bankAccount);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject placeJSON(Place place){
        JSONObject result = new JSONObject();
        //TODO
        return result;
    }
}
