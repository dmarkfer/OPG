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


import static com.opp.fangla.terznica.util.Random.*;

public class GetAdverts extends AsyncTask<String, Void, List<Advert>> {

    private MutableLiveData<List<Advert>> liveData;


    public GetAdverts(MutableLiveData<List<Advert>> liveData){
        this.liveData = liveData;
    }

    @Override
    protected List<Advert> doInBackground(String... strings) {
        List<Advert> result = new ArrayList<>();
        Socket socket = new Socket();

        try{
            JSONObject json = new JSONObject();
            json.put("command","retrieveProductOffers");
            json.put("idKategorijeOglasa", strings[0]);
            json.put("naziv", strings[1]);
            json.put("brojTrazenihOglasa", strings[2]);

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
            JSONArray  array = response.getJSONArray("oglasi");


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
