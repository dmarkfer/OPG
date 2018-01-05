package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextWatcher;

public class RegisterViewModel extends AndroidViewModel {

    final public ObservableField<String> name = new ObservableField<>();
    final public ObservableField<String> surname = new ObservableField<>();
    final public ObservableField<String> mail = new ObservableField<>();
    final public ObservableField<String> phone = new ObservableField<>();
    final public ObservableField<String> password = new ObservableField<>();
    final public ObservableField<String> confirmPassword = new ObservableField<>();
    private MutableLiveData<Boolean> strongPassword, passwordsMatch, buyer, vendor, driver;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        strongPassword = new MutableLiveData<>();
        strongPassword.postValue(false);
        passwordsMatch = new MutableLiveData<>();
        passwordsMatch.postValue(false);
        buyer = new MutableLiveData<>();
        buyer.postValue(false);
        vendor = new MutableLiveData<>();
        vendor.postValue(false);
        driver = new MutableLiveData<>();
        driver.postValue(false);
    }

    public LiveData<Boolean> getBuyer() {
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer.postValue(buyer);
    }

    public LiveData<Boolean> getVendor() {
        return vendor;
    }

    public void setVendor(boolean vendor) {
        this.vendor.postValue(vendor);
    }

    public LiveData<Boolean> getDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver.postValue(driver);
    }

    public LiveData<Boolean> getStrongPassword() {
        return strongPassword;
    }

    public LiveData<Boolean> getPasswordsMatch() {
        return passwordsMatch;
    }

    public TextWatcher passwordWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.set(charSequence.toString());
                comparePasswords();
                boolean flag = charSequence.length() > 3;
                if(strongPassword.getValue().booleanValue() != flag)
                    strongPassword.postValue(flag);
            }
        };
    }

    public TextWatcher confirmPasswordWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPassword.set(charSequence.toString());
            }
        };
    }

    private void comparePasswords(){
        boolean flag = password.get().equals(confirmPassword.get());
        if(passwordsMatch.getValue().booleanValue() != flag)
        passwordsMatch.postValue(flag);
    }
}
