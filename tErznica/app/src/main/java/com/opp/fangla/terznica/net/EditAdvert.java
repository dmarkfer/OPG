package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class EditAdvert extends AsyncTask<Advert,Void,Boolean>{


    MutableLiveData<Boolean> liveData;
    public  EditAdvert (MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }
    //editProduct(idOglasa, idKategorijeOglasa, nazivOglasa,
    // slikaOglasa, opisOglasa, cijena, vrijeme)
    //-> (success)

    @Override
    protected Boolean doInBackground(Advert... adverts) {

        Boolean success = null;
        Advert advert = adverts[0];

        String[] commands = new String[] {"command","idOglasa","idKategorijeOglasa",
                "nazivOglasa","slikaOglasa","opisOglasa","cijena","vrijeme"};
        String[] values  = new String[7];
        values[0]=String.valueOf(advert.getAdvertId());
        values[1]=String.valueOf(advert.getCategoryId());
        values[2]=advert.getName();
        values[3]=advert.getPicture().toString();
        values[4]=advert.getDescription();
        values[5]=String.valueOf(advert.getValue());
        values[6]=advert.getDate().toString();

        String sResponse = Random.sendMessageToServer(commands,values,"editProduct args");

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
