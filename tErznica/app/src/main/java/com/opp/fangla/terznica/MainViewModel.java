package com.opp.fangla.terznica;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.location.places.Place;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.data.entities.Category;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.util.Random;
import com.opp.fangla.terznica.util.SimpleTextWatcher;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private DataRepository repository;
    private MutableLiveData<Place> departure, destination;
    private Advert newAdvert;
    private MutableLiveData<List<Advert>> vendorAdverts; //TODO

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
        departure = new MutableLiveData<>();
        destination = new MutableLiveData<>();
        vendorAdverts = new MutableLiveData<>();
    }

    public LiveData<List<Advert>> getVendorAdverts(int id){
        if(vendorAdverts.getValue() == null){
            vendorAdverts = repository.getAdvertsByVendor(id);
        }
        return vendorAdverts;
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

    public LiveData<List<AdvertShipment>> getShipmentAdverts() {
        String dep, dest;
        boolean hasEnteredSomething = false;
        if(departure.getValue() == null){
            dep = "";
        } else {
            dep = Random.getAddressFromPlace(departure.getValue()).getTown();
            hasEnteredSomething = true;
        }
        if(destination.getValue() == null){
            dest = "";
        } else {
            dest = Random.getAddressFromPlace(destination.getValue()).getTown();
            hasEnteredSomething = true;
        }
        if(hasEnteredSomething){
            return repository.getShipmentAdverts(dep, dest);
        } else {
            return null;
        }
    }

    public LiveData<List<Advert>> getProductAdverts(){
        return repository.getAdverts();
    }

    public LiveData<List<Advert>> getProductSearchResults(String searchTerm) {
        return repository.getProductSearchResults(searchTerm);
    }

    public LiveData<List<Category>> getCategories(){
        return repository.getCategories();
    }

    public Advert getNewAdvert(){
        if(newAdvert == null){
            newAdvert = new Advert();
            newAdvert.setValue(0);
            newAdvert.setDescription("");
            newAdvert.setName("");
        }
        return newAdvert;
    }

    public SimpleTextWatcher getAdvertNameWatcher(final Advert advert){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                advert.setName(charSequence.toString());
            }
        };
    }

    public SimpleTextWatcher getAdvertDescriptionWatcher(final Advert advert){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                advert.setDescription(charSequence.toString());
            }
        };
    }

    public SimpleTextWatcher getAdvertPriceWatcher(final Advert advert){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    advert.setValue(Integer.valueOf(charSequence.toString()));
                }
            }
        };
    }

    public void createProduct(final Advert advert){
        final LiveData<Integer> liveData = repository.createAdvert(advert);
        liveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer != null) {
                    advert.setId(integer);
                    List<Advert> list = vendorAdverts.getValue();
                    list.add(advert);
                    vendorAdverts.postValue(list);
                    liveData.removeObserver(this);
                }
            }
        });
    }

    public void editProduct(Advert advert){
        repository.editAdvert(advert);
    }

    public LiveData<String> createConversation(Conversation conversation){
        return repository.createConversation(conversation);
    }

    public LiveData<User> getUser(int id){
        return repository.getUser(Integer.toString(id));
    }
}
