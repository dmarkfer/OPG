package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.data.entities.Vehicle;
import com.opp.fangla.terznica.data.entities.Vendor;
import com.opp.fangla.terznica.util.Random;

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


public class GetUser extends AsyncTask<String, Void, User> {



    private MutableLiveData<User> liveData;


    public GetUser (MutableLiveData<User> liveData) {
        this.liveData = liveData;
    }


    // ime, prezime, lozinka, email, telefon, poljoprivrednik, kupac, prijevoznik, nazivOPG, OIBOPG, adresaOPG,
    // slikaOPG, opisOPG, IBAN, vozila[registarskaOznaka, idKategorijeVozila, opisVozila, slikaVozila],
    // opisPrijevoza)

    @Override
    protected User doInBackground(String... strings) {
        Socket socket = new Socket();
        User user = new User();


        try {

            JSONObject json = new JSONObject();
            json.put("command","retrieveUserProfile");
            json.put("idKorisnika",strings[0]);
            Log.d("GetUser arguments ", json.toString());
            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //Log.d("server welcome" , c.getText());
            String sResponse = c.getText();
            Log.d("Server response",sResponse);
            c.close();
            c.disconnect();

            JSONObject response = new JSONObject(sResponse);
            user.setName(response.getString("ime"));
            user.setSurname(response.getString("prezime"));
            user.setPassword(response.getString("lozinka"));
            user.setBuyer(response.getBoolean("kupac"));
            user.setPhone(response.getString("telefon"));
            user.setVendor(response.getBoolean("poljoprivrednik"));
            if(user.isVendor()){
                Bitmap image = convertByteToBitMap(response.getString("slikaOPG"));
                Vendor vendor = new Vendor(image);
                vendor.setAddress(Random.parseStringToAddress(response.getString("adresaOPG")));
                vendor.setBankAccount(response.getString("IBAN"));
                vendor.setDescription(response.getString("opisOPG"));
                vendor.setName(response.getString("nazivOPG"));
                vendor.setIdNum(response.getString("OIBOPG"));
                user.setVendorData(vendor);

            }
            user.setDriver(response.getBoolean("prijevoznik"));
            if(user.isDriver()) {
                JSONArray array;
                array = response.getJSONArray("vozila");
                JSONObject object;
                Vehicle vehicle;
                List<Vehicle> vehicleList = new ArrayList<>();

                for (int i = 0; i< array.length(); i++) {
                    object = array.getJSONObject(i);
                    vehicle = new Vehicle(convertByteToBitMap(object.getString("slikaVozila")));
                    vehicle.setCategory(object.getString("idKategorijeVozila"));
                    vehicle.setIdVehicle(object.getString("registarskaOznaka"));
                    vehicle.setModel(object.getString("opisVozila"));
                    vehicleList.add(vehicle);
                }

                user.setVehicles(vehicleList);
                user.setTransportDescription(response.getString("opisPrijevoza"));
            }



        }catch (IOException | JSONException e) {
            e.getStackTrace();
        }


        return user;
    }

    @Override
    protected void onPostExecute(User user) {
            liveData.postValue(user);
    }
}
