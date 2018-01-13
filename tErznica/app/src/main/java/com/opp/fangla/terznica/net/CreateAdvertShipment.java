package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


//createShipmentOffer(idOglasa, idKupca, polaziste, odrediste, vrijeme) -> (idOglasaPrijevoza)
public class CreateAdvertShipment extends AsyncTask<AdvertShipment,Void,Integer> {

    MutableLiveData<Integer> liveData;

    public CreateAdvertShipment (MutableLiveData<Integer> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Integer doInBackground(AdvertShipment... advertShipments) {

        AdvertShipment advertShipment = advertShipments[0];
        Integer shipmentAdvertId = null;
        String[] commands = new String [] {"command", "idOglasa", "idKupca", "polaziste",
                "odrediste", "vrijeme"};
        String[] values = new String[5];
        values[0] = String.valueOf(advertShipment.getAdvertId());
        values[1] = String.valueOf(advertShipment.getCreatorId());
        values[2] = advertShipment.getStartLocation();
        values[3] = advertShipment.getEndLocation();
        values[4] = advertShipment.getDate().toString();


        String sResponse = Random.sendMessageToServer(commands,values,"createShipmOfferArgs");

        try {
            JSONObject jsonObject = new JSONObject(sResponse);
            shipmentAdvertId = jsonObject.getInt("idOglasaPrijevoza");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shipmentAdvertId;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        liveData.postValue(integer);
    }
}
