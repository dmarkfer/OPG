package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import com.opp.fangla.terznica.net.LogIn;
import com.opp.fangla.terznica.util.SimpleTextWatcher;

public class LogInViewModel extends AndroidViewModel {

    private String username = new String(), password = new String();

    public LogInViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> logIn(){
        MutableLiveData<String> liveResult = new MutableLiveData<>();
        new LogIn(liveResult).execute(username, password);
        return liveResult;
    }

    public TextWatcher usernameWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = charSequence.toString();
            }
        };
    }

    public TextWatcher passwordWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString();
            }
        };
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
