package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.widget.CompoundButton;

import com.opp.fangla.terznica.util.SimpleTextWatcher;

public class RegisterViewModel extends AndroidViewModel {

    private String name, surname, mail, phone, password, confirmPassword;
    private boolean buyer, vendor, driver;
    private MutableLiveData<Boolean> strongPassword, passwordsMatch;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        name = new String();
        surname = new String();
        mail = new String();
        phone = new String();
        password = new String();
        confirmPassword = new String();
        strongPassword = new MutableLiveData<>();
        strongPassword.postValue(false);
        passwordsMatch = new MutableLiveData<>();
        passwordsMatch.postValue(false);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public boolean isBuyer() {
        return buyer;
    }

    public boolean isVendor() {
        return vendor;
    }

    public boolean isDriver() {
        return driver;
    }

    public LiveData<Boolean> getStrongPasswordObs() {
        return strongPassword;
    }

    public LiveData<Boolean> getPasswordsMatchObs() {
        return passwordsMatch;
    }

    public TextWatcher getNameWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString();
            }
        };
    }

    public TextWatcher getSurnameWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                surname = charSequence.toString();
            }
        };
    }

    public TextWatcher getMailWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mail = charSequence.toString();
            }
        };
    }

    public TextWatcher getPhoneeWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = charSequence.toString();
            }
        };
    }

    public TextWatcher getPasswordWatcher(){
        return new SimpleTextWatcher(){
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString();
                comparePasswords();
                boolean flag = charSequence.length() > 3;
                if(strongPassword.getValue().booleanValue() != flag) {
                    strongPassword.postValue(flag);
                }
            }
        };
    }

    public TextWatcher getConfirmPasswordWatcher(){
        return new SimpleTextWatcher(){
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPassword = charSequence.toString();
                comparePasswords();
            }
        };
    }

    public CompoundButton.OnCheckedChangeListener getBuyerCheckedListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                buyer = b;
            }
        };
    }

    private void comparePasswords(){
        boolean flag = password.equals(confirmPassword);
        if(passwordsMatch.getValue().booleanValue() != flag) {
            passwordsMatch.postValue(flag);
        }
    }

    public boolean generalFieldsFilled(){
        return name.length() > 0 &&
                surname.length() > 0 &&
                mail.length() > 0 &&
                phone.length() > 0;
    }

    public boolean getStrongPassword(){
        return strongPassword.getValue() != null && strongPassword.getValue();
    }

    public boolean getPasswordsMatch(){
        return passwordsMatch.getValue() != null && passwordsMatch.getValue();
    }
}
