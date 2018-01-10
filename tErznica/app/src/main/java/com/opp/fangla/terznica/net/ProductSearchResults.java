package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.SimpleAdvert;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchResults extends AsyncTask<String, Void, List<SimpleAdvert>> {

    private MutableLiveData<List<SimpleAdvert>> liveData;

    public ProductSearchResults(MutableLiveData<List<SimpleAdvert>> liveData){
        this.liveData = liveData;
    }

    @Override
    protected List<SimpleAdvert> doInBackground(String... strings) {
        List<SimpleAdvert> result = new ArrayList<>();

        //to do network communication

        return result;
    }

    @Override
    protected void onPostExecute(List<SimpleAdvert> adverts) {
        liveData.postValue(adverts);
    }
}
