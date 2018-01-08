package com.opp.fangla.terznica.interfaces;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Advert;

import java.util.List;

public class VendorViewModel extends AndroidViewModel {

    private DataRepository repository;

    public VendorViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp)application).getRepository();
    }

    public LiveData<List<Advert>> getAdverts(){
        return repository.getAdverts();
    }
}
