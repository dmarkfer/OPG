package com.opp.fangla.terznica.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;

import com.google.android.gms.location.places.Place;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Vendor;
import com.opp.fangla.terznica.util.SimpleTextWatcher;

public class RegisterViewModel extends AndroidViewModel {

    private String name, surname, mail, phone, password, confirmPassword;
    private Vendor vendorObj;
    private boolean buyer, driver, validVendorImage, valid;
    private MutableLiveData<Boolean> strongPassword, passwordsMatch, vendor;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        name = new String();
        surname = new String();
        mail = new String();
        phone = new String();
        password = new String();
        confirmPassword = new String();
        vendorObj = new Vendor(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.camera_white));
        strongPassword = new MutableLiveData<>();
        strongPassword.postValue(false);
        passwordsMatch = new MutableLiveData<>();
        passwordsMatch.postValue(false);
        vendor = new MutableLiveData<>();
        vendor.postValue(false);
    }

    public LiveData<Bitmap> getVendorImage() {
        return vendorObj.getImage();
    }

    public void setVendorImage(Bitmap vendorImage) {
        //Log.d("RegisterViewModel", "AAAAAAAAAAAA image changed");
        vendorObj.setImage(vendorImage);
    }

    public String getVendorDescription() {
        return vendorObj.getDescription();
    }

    public String getVendorIban() {
        return vendorObj.getBank_account();
    }

    public LiveData<Boolean> getVendor(){
        return vendor;
    }

    public void setAddress(Place place){
        vendorObj.setAddress(place);
    }

    public LiveData<Place> getAddress() {
        return vendorObj.getAddress();
    }

    public String getVendorName() {
        return vendorObj.getName();
    }

    public String getVendorNumber() {
        return vendorObj.getId_num();
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
        if(vendor.getValue() == null) return false;
        else return vendor.getValue();
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

    public TextWatcher getPhoneWatcher(){
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

    public TextWatcher getVendorNameWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendorObj.setName(charSequence.toString());
            }
        };
    }

    public TextWatcher getVendorNumberWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendorObj.setId_num(charSequence.toString());
            }
        };
    }

    public TextWatcher getVendorDescriptionWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendorObj.setDescription(charSequence.toString());
            }
        };
    }

    public TextWatcher getVendorIbanWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendorObj.setBank_account(charSequence.toString());
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

    public CompoundButton.OnCheckedChangeListener getVendorCheckedListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vendor.postValue(b);
            }
        };
    }

    public CompoundButton.OnCheckedChangeListener getDriverCheckedListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                driver = b;
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

    public boolean vendorFieldsFilled(){
        return vendorObj.getName().length() > 0 &&
                vendorObj.getBank_account().length() > 0 &&
                vendorObj.getDescription().length() > 0 &&
                vendorObj.getId_num().length() > 0;
    }

    public boolean vendorAddressValid(){
        return vendorObj.getAddress().getValue() != null;
    }

    public boolean vendorImageValid(){
        return vendorObj.getImage().getValue() != null;
    }
}
