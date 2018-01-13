package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


public class DeleteShipmentAdvert extends AsyncTask<Integer,Void,Boolean> {


    MutableLiveData<Boolean> liveData;

    public  DeleteShipmentAdvert (MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        //TODO check commands !!
        String[] commands = new String[] {"deleteShipmentOffer", "idOglasaPrijevoza"};
        String[] values = new String[1];
        values[0] = String.valueOf(integers[0]);
        Boolean success= null;

        String sResponse = Random.sendMessageToServer(commands,values,"deleteShipOffArgs");

        try {
            JSONObject jsonObject = new JSONObject(sResponse);
            success = jsonObject.getBoolean("idOglasaPrijevoza");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        liveData.postValue(aBoolean);
    }
}
