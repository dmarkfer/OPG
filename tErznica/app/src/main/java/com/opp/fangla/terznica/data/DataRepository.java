package com.opp.fangla.terznica.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.database.MatrixCursor;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.SimpleAdvert;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.net.AdvertsDownload;
import com.opp.fangla.terznica.net.DeleteUser;
import com.opp.fangla.terznica.net.GetUser;
import com.opp.fangla.terznica.net.LogIn;
import com.opp.fangla.terznica.net.ProductSearchResults;
import com.opp.fangla.terznica.net.ProductSearchSuggestions;
import com.opp.fangla.terznica.net.RegisterUser;
import com.opp.fangla.terznica.util.LogInCallback;
import com.opp.fangla.terznica.util.RegisterUserCallback;

import org.json.JSONObject;

import java.util.List;

public class DataRepository {

    private static DataRepository dInstance;

    public static DataRepository getInstance(){
        if(dInstance == null){
            synchronized (DataRepository.class){
                dInstance = new DataRepository();
            }
        }
        return dInstance;
    }

    public MutableLiveData<List<SimpleAdvert>> getProductSearchResults(String searchTerm) {
        MutableLiveData<List<SimpleAdvert>> adverts = new MutableLiveData<>();
        new ProductSearchResults(adverts).execute(searchTerm);
        return adverts;
    }

    public MutableLiveData<MatrixCursor> getProductSearchSuggestions(){
        MutableLiveData<MatrixCursor> suggestions = new MutableLiveData<>();
        new ProductSearchSuggestions(suggestions).execute();
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

    public LiveData<User> getUser (String userId) {
        MutableLiveData<User> liveResult = new MutableLiveData<>();
        new GetUser(liveResult).execute(userId);
        return liveResult;
    }
}
