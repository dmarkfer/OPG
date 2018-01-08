package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Advert;

import java.util.ArrayList;
import java.util.List;

public class AdvertsDownload extends AsyncTask<String, Void, List<Advert>>{

    private MutableLiveData<List<Advert>> result;

    public AdvertsDownload(MutableLiveData<List<Advert>> result) {
        this.result = result;
    }

    @Override
    protected List<Advert> doInBackground(String... strings) {
        List<Advert> list = new ArrayList<>();



        return list;
    }

    @Override
    protected void onPostExecute(List<Advert> adverts) {
        result.postValue(adverts);
    }
}
