package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextWatcher;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.net.LogIn;
import com.opp.fangla.terznica.util.LogInCallback;
import com.opp.fangla.terznica.util.SimpleTextWatcher;

public class LogInViewModel extends AndroidViewModel {

    private String username, password;
    private DataRepository repository;

    public LogInViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
        username = new String();
        password = new String();
    }

    public LiveData<LogInCallback> logIn(){
        return repository.logIn(username, password);
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

    public String getPassword() {
        return password;
    }

}
