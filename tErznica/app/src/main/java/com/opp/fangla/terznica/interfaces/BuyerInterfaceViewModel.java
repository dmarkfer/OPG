package com.opp.fangla.terznica.interfaces;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.SimpleAdvert;

import java.util.List;

public class BuyerInterfaceViewModel extends AndroidViewModel {

    private DataRepository repository;

    public BuyerInterfaceViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
    }

    public LiveData<List<SimpleAdvert>> getProductSearchResults(String searchTerm) {
        return repository.getProductSearchResults(searchTerm);
    }

    public LiveData<MatrixCursor> getProductSearchSuggestions(){
        return repository.getProductSearchSuggestions();
    }
}
