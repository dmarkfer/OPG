package com.opp.fangla.terznica.data.entities;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class User {

    private Integer id;
    private String name, surname, phone, mail, password;
    private boolean buyer, vendor, driver;
    private Vendor vendorData;
    private List<Vehicle> vehicles;
    private Bitmap picture;
    private String transportDescription;



    public void setTransportDescription(String transportDescription) {
        this.transportDescription = transportDescription;
    }

    public String getTransportDescription() {

        return transportDescription;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBuyer() {
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public boolean isVendor() {
        return vendor;
    }

    public void setVendor(boolean vendor) {
        this.vendor = vendor;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public Vendor getVendorData() {
        return vendorData;
    }

    public void setVendorData(Vendor vendorData) {
        this.vendorData = vendorData;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public static JSONObject toJSON(User user){
        JSONObject result = new JSONObject();

        try {
            if(user.id != null){
                result.put("idKorisnika", user.id);
            }
            if(user.name != null){
                result.put("ime", user.name);
            }
            if(user.surname != null){
                result.put("prezime", user.surname);
            }
            if(user.password != null){
                result.put("lozinka", user.password);
            }
            if(user.mail != null){
                result.put("email", user.mail);
            }
            if(user.phone != null){
                result.put("telefon", user.phone);
            }
            if(user.picture != null){
                result.put("slika", bitmapToString(user.picture));
            }
            result.put("kupac", user.buyer ? 1 : 0);
            result.put("poljoprivrednik", user.vendor ? 1 : 0);
            if(user.vendor) {
                result.put("poljoprivrednikJSON", Vendor.toJSON(user.vendorData));
            }
            result.put("prijevoznik", user.driver ? 1 : 0);
            if(user.driver){
                JSONArray vehicles = new JSONArray();
                for(Vehicle vehicle : user.vehicles){
                    vehicles.put(Vehicle.toJSON(vehicle));
                }
                result.put("vozila", vehicles);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }
}
