package com.opp.fangla.terznica.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;


import com.opp.fangla.terznica.data.entities.*;
import com.opp.fangla.terznica.net.*;
import com.opp.fangla.terznica.util.*;

import org.json.JSONObject;

import java.util.List;

public class DataRepository {

    private static DataRepository dInstance;
    private MutableLiveData<MatrixCursor> suggestions = new MutableLiveData<>();

    public static DataRepository getInstance(){
        if(dInstance == null){
            synchronized (DataRepository.class){
                dInstance = new DataRepository();
            }
        }
        return dInstance;
    }

    public MutableLiveData<List<Advert>> getProductSearchResults(String searchTerm) {
        MutableLiveData<List<Advert>> adverts = new MutableLiveData<>();
        new GetAdverts(adverts).execute(searchTerm);
        return adverts;
    }

    public MutableLiveData<MatrixCursor> getProductSearchSuggestions(){
        if(suggestions.getValue() == null) {
            new GetAdvertsSuggestion(suggestions).execute();
        }
        return suggestions;
    }

    public LiveData<List<Advert>> getAdverts() {
        MutableLiveData<List<Advert>> adverts = new MutableLiveData<>();
        new AdvertsDownload(adverts).execute();
        return adverts;
    }

    /*public LiveData<RegisterUserCallback> registerUser(JSONObject user){
        MutableLiveData<RegisterUserCallback> result = new MutableLiveData<>();
        new RegisterUser(result).execute(user);
        return result;
    }*/

    public LiveData<LogInCallback> registerUser(JSONObject user){
        MutableLiveData<LogInCallback> result = new MutableLiveData<>();
        new RegisterUser(result).execute(user);
        return result;
    }

    public LiveData<LogInCallback> logIn(String username, String password){
        MutableLiveData<LogInCallback> liveResult = new MutableLiveData<>();
        new LogIn(liveResult).execute(username, password);
        return liveResult;
    }

    public LiveData<Boolean> deleteUser (String userId) {
        MutableLiveData <Boolean> isSuccesful= new MutableLiveData();
        new DeleteUser(isSuccesful).execute(userId);
        return isSuccesful;
    }

    public MutableLiveData<User> getUser (String userId) {
        MutableLiveData<User> liveResult = new MutableLiveData<>();
        new GetUser(liveResult).execute(userId);
        return liveResult;
    }

    public MutableLiveData<String> createComment ( Comment comment) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        new CreateComment(liveData).execute(comment);
        return liveData;
    }

    /*
        Create new object Comment and put in new data and old
        idReviewMark.
     */
    public MutableLiveData<Boolean> editComment (Comment comment) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new EditComment(liveData).execute(comment);
        return liveData;
    }


    public MutableLiveData<Boolean> deleteComment (String idReviewMark) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new DeleteComment(liveData).execute(idReviewMark);
        return liveData;
    }

    public MutableLiveData<String> createConversation (Conversation conversation) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        new CreateConversation(liveData).execute(conversation);
        return liveData;
    }

    public MutableLiveData<Boolean> sendMessage (Message message) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new SendMessage(liveData).execute(message);
        return liveData;
    }

    public MutableLiveData<List<Message>> getMessages (int idChat) {
        MutableLiveData<List<Message>> liveData = new MutableLiveData<>();
        new GetMessages(liveData).execute(idChat);
        return  liveData;
    }

    public MutableLiveData<Integer> createReport (Report report) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new CreateReport(liveData).execute(report);
        return liveData;
    }

    public MutableLiveData<Integer> createProductCategory (ProductCategory productCategory) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new CreateProductCategory(liveData).execute(productCategory);
        return liveData;
    }

    public MutableLiveData<Boolean> deleteProductCategory (Integer categoryId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new DeleteProductCategory(liveData).execute(categoryId);
        return liveData;
    }

    public MutableLiveData<Integer> createAdvert (Advert advert) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new CreateAdvert(liveData).execute(advert);
        return liveData;
    }

    public MutableLiveData<Boolean> editAdvert (Advert advert) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new EditAdvert(liveData).execute(advert);
        return liveData;
    }

    public  MutableLiveData<Boolean> deleteAdvert (Integer advertId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new DeleteAdvert(liveData).execute(String.valueOf(advertId));
        return liveData;
    }

    public  MutableLiveData<Integer> createAdvertShipment (AdvertShipment advertShipment) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new CreateAdvertShipment(liveData).execute(advertShipment);
        return liveData;
    }

    public MutableLiveData<List<AdvertShipment>> getShipmentAdverts (String placeName) {
        MutableLiveData<List<AdvertShipment>> liveData = new MutableLiveData<>();
        new GetShipmentAdverts(liveData).execute(placeName);
        return liveData;
    }

    public MutableLiveData<Integer> createVehicle (Vehicle vehicle) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        new CreateVehicle(liveData).execute(vehicle);
        return liveData;
    }

    public MutableLiveData<List<Vehicle>> getVehicles (Integer userId) {
        MutableLiveData<List<Vehicle>> liveData = new MutableLiveData<>();
        new GetVehicles(liveData).execute(userId);
        return liveData;
    }

    public  MutableLiveData<Boolean> deleteVehicles (Integer vehicleId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        new DeleteVehicle(liveData).execute(vehicleId);
        return liveData;
    }
}
