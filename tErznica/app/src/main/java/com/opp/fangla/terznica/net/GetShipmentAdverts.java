package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.opp.fangla.terznica.util.Random.convertByteToBitMap;
import static com.opp.fangla.terznica.util.Random.sendMessageToServer;
import static com.opp.fangla.terznica.util.Random.setDateFromString;

public class GetShipmentAdverts extends AsyncTask<String,Void,List<AdvertShipment>>{

    private MutableLiveData<List<AdvertShipment>> liveData;

    public GetShipmentAdverts (MutableLiveData<List<AdvertShipment>> liveData) {
        this.liveData = liveData;
    }


    @Override
    protected List<AdvertShipment> doInBackground(String... strings) {
       // retrieveShipmentOffers(nazivMjesta) ->
       // (oglasi[idOglasaPrijevoza, idOglasa, idKupca, polaziste, odrediste, vrijeme])
        String[] commands = new String[] {"retrieveShipmentOffers","nazivPolazista","nazivOdredista"};
        String[] values = new String[2];
        List<AdvertShipment> list = new ArrayList<>();

        values[0] = strings[0];
        values[1] = strings[1];

        String sResponse = sendMessageToServer(commands,values,"retrieveShipOffArgs");

        try {

            //(oglasi[idOglasaPrijevoza, idOglasa, idKupca, polaziste, odrediste, vrijeme])
            JSONObject json = new JSONObject(sResponse);
            JSONArray array = json.getJSONArray("oglasi");

            for (int i = 0; i< array.length(); i++) {
                AdvertShipment advertShipment = new AdvertShipment();
                Advert tmp = new Advert();
                JSONObject object = array.getJSONObject(i);
                advertShipment.setAdvertId(object.getInt("idOglasa"));
                advertShipment.setCreatorId(object.getInt("idKupca"));
                advertShipment.setShipmentAdvertId(object.getInt("idOglasaPrijevoza"));
                advertShipment.setDate(Date.valueOf(object.getString("vrijeme")));
                advertShipment.setStartLocation(object.getString("polaziste"));
                advertShipment.setEndLocation(object.getString("odrediste"));
                JSONArray array1 = object.getJSONArray("oglasProizvoda");
                JSONObject object1 = array1.getJSONObject(0);
                tmp.setAdvertId(object1.getInt("[idOglasa"));
                tmp.setCategoryId(object1.getInt("idKategorije"));
                tmp.setCreatorId(object1.getInt("idPoljoprivrednika"));
                tmp.setDescription(object1.getString("opisOglasa"));
                tmp.setPicture(convertByteToBitMap(object1.getString("slikaOglasa")));
                tmp.setValue(object1.getInt("cijena"));
                tmp.setDate(setDateFromString(object1.getString("vrijeme")));
                tmp.setName(object1.getString("nazivOglasa"));
                advertShipment.setAdvert(tmp);
                list.add(advertShipment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<AdvertShipment> advertShipments) {
        liveData.postValue(advertShipments);
    }
}
