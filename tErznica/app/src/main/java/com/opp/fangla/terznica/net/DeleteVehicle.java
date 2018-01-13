package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteVehicle extends AsyncTask<Integer,Void,Boolean> {


    MutableLiveData<Boolean> liveData = new MutableLiveData<>();

    public DeleteVehicle (MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Integer vehicleId = integers[0];
        Boolean success = null;
        String[] command = new String[] {"deleteVehicle","idVozila"};
        String[] values = new String[1];
        values[0] = String.valueOf(vehicleId);

        String sResponse = Random.sendMessageToServer(command,values,"deleteVehicleArgs");

        try {
            JSONObject jsonObject = new JSONObject(sResponse);
            success = jsonObject.getBoolean("success");
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
