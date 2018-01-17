package com.opp.fangla.terznica.data.entities;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

public class Address {

    private String state;
    private String town;
    private String street;
    private LatLng latLng;
    private String number;
    private String placeId;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }



    @Override
    public String toString () {
        return null;
        //TODO  address toString()
    }
}
