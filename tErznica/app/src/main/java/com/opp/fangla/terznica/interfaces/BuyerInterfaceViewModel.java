package com.opp.fangla.terznica.interfaces;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Advert;

import java.util.List;

public class BuyerInterfaceViewModel extends AndroidViewModel {

    private DataRepository repository;

    public BuyerInterfaceViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
    }

    public LiveData<List<Advert>> getProductSearchResults(String searchTerm) {
        return repository.getProductSearchResults(searchTerm);
    }

    public LiveData<MatrixCursor> getProductSearchSuggestions(){
        return repository.getProductSearchSuggestions();
    }
}
