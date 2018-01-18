package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateAdvert extends AsyncTask<Advert,Void,Integer> {
    MutableLiveData<Integer> liveData;

    public CreateAdvert (MutableLiveData<Integer> liveData) {
        this.liveData = liveData;
    }


    @Override
    protected Integer doInBackground(Advert... adverts) {

        Advert advert = adverts[0];
        Integer advertId = null;
        //createProduct(idKorisnika, idKategorijeOglasa, nazivOglasa,
//        slikaOglasa, opisOglasa, cijena, vrijeme) -> (idOglasa)

        String[] commands = new String[]{"createProduct","idKorisnika","idKategorijeOglasa","nazivOglasa",
                "slikaOglasa","opisOglasa","cijena","vrijeme"};
        String[] values = new String[7];
        values[0] = String.valueOf(advert.getCreatorId());
        values[1] =String.valueOf(advert.getCategoryId());
        values[2] =advert.getName();
        values[3] =Random.bitmapToString(advert.getPicture());
        values[4] =advert.getDescription();
        values[5] =String.valueOf(advert.getValue());
        values[6] =Random.dateToString(advert.getDate());

        String sResponse = Random.sendMessageToServer(commands,values,"createProduct args");

        try {
            JSONObject json = new JSONObject(sResponse);
            advertId = json.getInt("idOglasa");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return advertId;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        liveData.postValue(integer);
    }
}
