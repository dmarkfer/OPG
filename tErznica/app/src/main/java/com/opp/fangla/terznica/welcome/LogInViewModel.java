package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.opp.fangla.terznica.net.LogIn;

public class LogInViewModel extends AndroidViewModel {

    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public LogInViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> logIn(){
        MutableLiveData<String> liveResult = new MutableLiveData<>();
        new LogIn(liveResult).execute(username.get(), password.get());
        return liveResult;
    }

    public TextWatcher usernameWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username.set(charSequence.toString());
            }
        };
    }

    public TextWatcher passwordWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.set(charSequence.toString());
            }
        };
    }


}
