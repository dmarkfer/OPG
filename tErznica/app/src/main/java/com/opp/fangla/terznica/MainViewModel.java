package com.opp.fangla.terznica;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;

import com.google.android.gms.location.places.Place;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private DataRepository repository;
    private MutableLiveData<Place> departure, destination;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
        departure = new MutableLiveData<>();
        destination = new MutableLiveData<>();
    }

    public LiveData<Place> getDeparture() {
        return departure;
    }

    public void setDeparture(Place departure) {
        this.departure.postValue(departure);
    }

    public LiveData<Place> getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination.postValue(destination);
    }

    public LiveData<AdvertShipment> getShipmentAdverts() {
        return null;// TODO repository.getShipmentAdverts(Random.placeCity(departure.getValue(), destination.getValue());
    }

    public LiveData<List<Advert>> getProductAdverts(){
        return repository.getAdverts();
    }

    public LiveData<List<Advert>> getProductSearchResults(String searchTerm) {
        return repository.getProductSearchResults(searchTerm);
    }

    public LiveData<MatrixCursor> getProductSearchSuggestions(){
        return repository.getProductSearchSuggestions();
    }
}
