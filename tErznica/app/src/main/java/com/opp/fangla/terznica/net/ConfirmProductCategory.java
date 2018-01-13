package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

//confirmProductCategory(idKategorije) -> (success)
public class ConfirmProductCategory extends AsyncTask<Integer,Void,Boolean> {

     MutableLiveData<Boolean> liveData;

     public  ConfirmProductCategory (MutableLiveData<Boolean> liveData) {
         this.liveData = liveData;
     }

     @Override
     protected Boolean doInBackground(Integer... integers) {

         Integer idCat = integers[0];
         Boolean success = null;
         String[] commands = new String[] {"confirmProductCategory","idKategorije"};
         String[] values = new String[1];
         values[0] = idCat.toString();

         String sResponse = Random.sendMessageToServer(commands,values,"confirmProdCatArg");
         try {
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
