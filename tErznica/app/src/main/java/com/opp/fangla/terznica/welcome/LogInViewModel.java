package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.opp.fangla.terznica.net.LogIn;

/**
 * Created by domagoj on 29.12.17..
 */

public class LogInViewModel extends AndroidViewModel {

    private MutableLiveData<String> username, password;
    //private String username, password;

    public LogInViewModel(@NonNull Application application) {
        super(application);
        username = new MutableLiveData<>();
        password = new MutableLiveData<>();
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<String> logIn(){
        MutableLiveData<String> liveResult = new MutableLiveData<>();
        new LogIn(liveResult).execute(username.getValue(), password.getValue());
        return liveResult;
    }
}
