package com.opp.fangla.terznica;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.User;

public class ProfileViewModel extends AndroidViewModel {

    private DataRepository repository;
    private boolean editable;
    private MutableLiveData<User> user = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
    }

    public LiveData<User> getUser(int userId){
        if(user.getValue() == null){
            user = repository.getUser(Integer.toString(userId));
        }
        return user;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
}
