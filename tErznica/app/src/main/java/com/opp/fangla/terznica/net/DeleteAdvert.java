package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


public class DeleteAdvert extends AsyncTask<String,Void,Boolean>{

    MutableLiveData<Boolean> liveData;

    public  DeleteAdvert(MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }


    @Override
    protected Boolean doInBackground(String... adverts) {

        Boolean success = null;
        String[] commands = new String[] {"deleteProduct","idOglasa"};
        String[] values = new String[1];
        values[0] = adverts[0];

        String sResponse = Random.sendMessageToServer(commands,values,"deleteProduct args");

        try {
            JSONObject json = new JSONObject(sResponse);
            success = json.getBoolean("success");

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
