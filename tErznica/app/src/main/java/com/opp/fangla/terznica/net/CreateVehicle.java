package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.Vehicle;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


//createVehicle(idKorisnika, registarskaOznaka, idKategorijeVozila, opisVozila, slikaVozila) -> (idVozila)
public class CreateVehicle extends AsyncTask<Vehicle,Void,Integer> {

    private MutableLiveData<Integer> liveData;

    public CreateVehicle(MutableLiveData<Integer> liveData){
        this.liveData = liveData;
    }


    @Override
    protected Integer doInBackground(Vehicle... vehicles) {

        Integer idVehicle = null;
        Vehicle vehicle = vehicles[0];
        String[] commands = new String[]{"createVehicle","idKorisnika","registarskaOznaka","idKategorijeVozila",
                "opisVozila","slikaVozila"};
        String[] values = new String[5];
        values[0]=String.valueOf(vehicle.getIdVehicle());
        values[1]=String.valueOf(vehicle.getRegistration());
        values[2]=String.valueOf(vehicle.getCategory());
        values[3]=vehicle.getModel();
        values[4]=vehicle.getImage().toString();

        String sResponse = Random.sendMessageToServer(commands,values,"createVehicleArgs");

        try {

            JSONObject jsonObject = new JSONObject(sResponse);
            idVehicle = jsonObject.getInt("idVozila");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return idVehicle;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        liveData.postValue(integer);
    }
}
