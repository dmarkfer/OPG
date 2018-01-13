package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.opp.fangla.terznica.util.Random.convertByteToBitMap;
import static com.opp.fangla.terznica.util.Random.sendMessageToServer;


public class GetVehicles extends AsyncTask<Integer,Void,List<Vehicle>> {

    //retrieveVehicles(idKorisnika) ->
     //(vozila[idVozila, registarskaOznaka, idKategorijeVozila, opisVozila, slikaVozila])

    private MutableLiveData<List<Vehicle>> liveData;

    public GetVehicles (MutableLiveData<List<Vehicle>> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected List<Vehicle> doInBackground(Integer... integers) {

        String userId = integers[0].toString();
        String[] commands = new String[] {"retrieveVehicles","idKorisnika"};
        String[] values = new String[1];
        values[0] = userId;
        List<Vehicle> list = new ArrayList<>();

        String sResponse = sendMessageToServer(commands,values,"retrieveVehiclesArgs");

        //(vozila[idVozila, registarskaOznaka, idKategorijeVozila, opisVozila, slikaVozila])
        try {
            JSONObject jsonObjects = new JSONObject(sResponse);
            JSONArray array = jsonObjects.getJSONArray("vozila");

            for(int i=0;i< array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                Vehicle vehicle = new Vehicle(
                        convertByteToBitMap(jsonObject.getString("slikaVozila")));
                vehicle.setIdVehicle(jsonObject.getString("idVozila"));
                vehicle.setModel(jsonObject.getString("opisVozila"));
                vehicle.setCategory(jsonObject.getString("idKategorijeVozila"));
                vehicle.setRegistration(jsonObject.getString("registarskaOznaka"));
                list.add(vehicle);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Vehicle> vehicles) {
        liveData.postValue(vehicles);
    }
}
