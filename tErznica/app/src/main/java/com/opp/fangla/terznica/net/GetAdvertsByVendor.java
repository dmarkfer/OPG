package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Advert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;
import static com.opp.fangla.terznica.util.Random.convertByteToBitMap;
import static com.opp.fangla.terznica.util.Random.setDateFromString;

public class GetAdvertsByVendor extends AsyncTask<Integer, Void, List<Advert>> {

    private MutableLiveData<List<Advert>> liveData;


    public GetAdvertsByVendor(MutableLiveData<List<Advert>> liveData){
        this.liveData = liveData;
    }

    @Override
    protected List<Advert> doInBackground(Integer... integers) {
        List<Advert> result = new ArrayList<>();
        Socket socket = new Socket();

        try{
            JSONObject json = new JSONObject();
            json.put("command","retrieveProductOffersByVendor");
            json.put("idOPG", integers[0]);

            Log.d("ProductSearch arguments",json.toString());
            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("ProductSearch server",c.getText());
            String sResponse = c.getText();
            Log.d("Product search results",sResponse);
            c.close();
            c.disconnect();

            JSONObject response = new JSONObject(sResponse);
            JSONArray array = response.getJSONArray("oglasi");


            //+retrieveProductOffers
            // (idKategorijeOglasa, naziv, brojTrazenihOglasa) -> (
            // oglasi[idOglasa, nazivOglasa, slikaOglasa, cijena, vrijeme])

            for (int i = 0; i < array.length(); i++) {
                Advert tmp = new Advert();
                tmp.setId(array.getJSONObject(i).getInt("idOglasa"));
                tmp.setName( array.getJSONObject(i).getString("nazivOglasa"));
                tmp.setPicture(convertByteToBitMap(array.getJSONObject(i).getString("slikaOglasa")));
                tmp.setValue(( array.getJSONObject(i).getInt("cijena")));
                tmp.setDate(setDateFromString( array.getJSONObject(i).getString("vrijeme")));
                result.add(tmp);
            }

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Advert> adverts) {
        liveData.postValue(adverts);
    }
}
