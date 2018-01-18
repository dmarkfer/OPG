package com.opp.fangla.terznica.util;

public class LogInCallback extends InternetCallback{
    private boolean admin, buyer, vendor, driver, success;
    private int id;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
