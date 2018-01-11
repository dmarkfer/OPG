package com.opp.fangla.terznica.util;

public class RegisterUserCallback extends InternetCallback{


    private boolean success;
    private int id;

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
