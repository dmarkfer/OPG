package com.opp.fangla.terznica.data;

import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;

import com.opp.fangla.terznica.data.entities.SimpleAdvert;
import com.opp.fangla.terznica.net.ProductSearchResults;
import com.opp.fangla.terznica.net.ProductSearchSuggestions;

import java.util.List;

/**
 * Created by domagoj on 21.12.17..
 */

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
}