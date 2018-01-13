package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DeleteProductCategory extends AsyncTask<Integer,Void,Boolean> {

    private MutableLiveData<Boolean> liveData;

    public DeleteProductCategory ( MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }


    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean success= null;
        Integer id = integers[0];
        //deleteProductCategory(idKategorije) -> (success)
        String[] commands = new String[]{"deleteProductCategory","idKategorije"};
        String[] values = new String[1];
        values[0]= id.toString();


        try {

            String sResponse = Random.sendMessageToServer(commands,values,"deleteProdCat");
            JSONObject response = new JSONObject(sResponse);
            success = response.getBoolean("success");

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
