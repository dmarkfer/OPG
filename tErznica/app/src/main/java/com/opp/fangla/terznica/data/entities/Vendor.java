package com.opp.fangla.terznica.data.entities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.google.android.gms.location.places.Place;

public class Vendor {

    private int id;
    private String name, description, id_num, bank_account;
    private MutableLiveData<Bitmap> image;
    private MutableLiveData<Place> address;
    private boolean validImage;

    public Vendor(Bitmap image) {
        name = new String();
        description = new String();
        id_num = new String();
        bank_account = new String();
        this.image = new MutableLiveData<>();
        this.image.postValue(image);
        address = new MutableLiveData<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
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
}
