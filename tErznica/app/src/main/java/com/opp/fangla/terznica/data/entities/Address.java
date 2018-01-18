package com.opp.fangla.terznica.data.entities;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Address {

    private String state;
    private String town;
    private String street;
    private LatLng latLng;
    private String number;
    private String placeId;
    private String townNum;


    public String getTownNum() {
        return townNum;
    }

    public void setTownNum(String townNum) {
        this.townNum = townNum;
    }

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

    public double getLongitude () {
        return latLng.longitude;
    }

    public double getLatitude () {
        return latLng.latitude;
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

    public boolean hasState () {
        if(state != null) {
            return true;
        }
        return false;
    }

    public boolean hasTown () {
        if(town != null) {
            return true;
        }
        return false;
    }

    public boolean hasTownNum () {
        if(townNum != null) {
            return true;
        }
        return false;
    }

    public boolean hasNumber () {
        if(number != null) {
            return true;
        }
        return false;
    }

    public boolean hasStreet() {
        if(street != null) {
            return true;
        }
        return false;
    }

    public boolean hasLatLng() {
        if(latLng != null) {
            return true;
        }
        return false;
    }


    @Override
    public String toString () {
        List<String> elements = new ArrayList<>();
        String str = "";

        if(street != null) {
            str += street + ".";
        }
        if(number != null) {
            str += number + ",";
        }
        if(townNum != null) {
            str += townNum +",";
        }

        if(town != null) {
            str += town + ",";
        }

        if(state != null) {
            str += state;
        }

        return str;
    }
}
