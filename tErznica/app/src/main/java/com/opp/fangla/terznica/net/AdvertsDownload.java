package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;
import static com.opp.fangla.terznica.util.Random.convertByteToBitMap;
import static com.opp.fangla.terznica.util.Random.setDateFromString;

public class AdvertsDownload extends AsyncTask<String, Void, List<Advert>>{

    private MutableLiveData<List<Advert>> result;

    public AdvertsDownload(MutableLiveData<List<Advert>> result) {
        this.result = result;
    }


    @Override
    protected List<Advert> doInBackground(String... strings) {
        List<Advert> list = new ArrayList<>();


        String[] commands = new String[] {"retrieveProductOffers","idKategorijeOglasa","naziv","brojTrazenihOglasa"};
        String[] values = strings;
        //retrieveProductOffers(idKategorijeOglasa, naziv, brojTrazenihOglasa) ->
        // (oglasi[idOglasa, nazivOglasa, slikaOglasa, cijena, vrijeme])

        try{

            String sResponse = Random.sendMessageToServer(commands,values,"ProductOffersArgs");
            JSONObject response = new JSONObject(sResponse);
            JSONArray array = response.getJSONArray("oglasi");


            for (int i = 0; i < array.length(); i++) {
                Advert tmp = new Advert();
                tmp.setId(array.getJSONObject(i).getInt("idOglasa"));
                tmp.setName( array.getJSONObject(i).getString("nazivOglasa"));
                tmp.setPicture(convertByteToBitMap(array.getJSONObject(i).getString("slikaOglasa")));
                tmp.setValue(( array.getJSONObject(i).getInt("cijena")));
                tmp.setDate(setDateFromString( array.getJSONObject(i).getString("vrijeme")));
                list.add(tmp);
            }


        }catch ( JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<Advert> adverts) {
        result.postValue(adverts);
    }
}
