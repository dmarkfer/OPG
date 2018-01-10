package com.opp.fangla.terznica.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.database.MatrixCursor;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.SimpleAdvert;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.net.AdvertsDownload;
import com.opp.fangla.terznica.net.ProductSearchResults;
import com.opp.fangla.terznica.net.ProductSearchSuggestions;
import com.opp.fangla.terznica.net.RegisterUser;
import com.opp.fangla.terznica.util.LogInCallback;
import com.opp.fangla.terznica.util.RegisterUserCallback;

import org.json.JSONObject;

import java.util.List;

public class DataRepository {

    private static DataRepository dInstance;

    private MutableLiveData<List<SimpleAdvert>> productSearchResults;
    private MutableLiveData<MatrixCursor> productSearchSuggestions;

    private DataRepository() {
        productSearchResults = new MutableLiveData<>();
        productSearchSuggestions = new MutableLiveData<>();
    }

    public static DataRepository getInstance(){
        if(dInstance == null){
            synchronized (DataRepository.class){
                dInstance = new DataRepository();
            }
        }
        return dInstance;
    }

    public void searchProducts(String searchTerm){
        new ProductSearchResults(productSearchResults).execute(searchTerm);
    }

    public void searchProductSuggestions(){
        new ProductSearchSuggestions(productSearchSuggestions).execute();
    }

    public MutableLiveData<List<SimpleAdvert>> getProductSearchResults() {
        return productSearchResults;
    }

    public MutableLiveData<MatrixCursor> getProductSearchSuggestions(){
        return productSearchSuggestions;
    }

    public LiveData<List<Advert>> getAdverts() {
        MutableLiveData<List<Advert>> adverts = new MutableLiveData<>();
        new AdvertsDownload(adverts).execute();
        return adverts;
    }

    public LiveData<RegisterUserCallback> registerUser(JSONObject user){
        MutableLiveData<RegisterUserCallback> result = new MutableLiveData<>();
        new RegisterUser(result).execute(user);
        return result;
    }

}
