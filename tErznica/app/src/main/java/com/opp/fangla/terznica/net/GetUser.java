package com.opp.fangla.terznica.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class GetUser extends AsyncTask<String, Void, User> {



    private LiveData<User> liveData;
    protected static final String HOSTNAME = "165.227.175.217";
    protected static final int PORT = 8080;

    public GetUser (MutableLiveData<User> liveData) {
        this.liveData = liveData;
    }


    // ime, prezime, lozinka, email, telefon, poljoprivrednik, kupac, prijevoznik, nazivOPG, OIBOPG, adresaOPG,
    // slikaOPG, opisOPG, IBAN, vozila[registarskaOznaka, idKategorijeVozila, opisVozila, slikaVozila], opisPrijevoza)

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

            Log.d("server welcome" , c.getText());
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
            user.setDriver(response.getBoolean("prijevoznik"));

            //TODO Finish GetUser



        }catch (IOException | JSONException e) {
            e.getStackTrace();
        }


        return null;
    }
}
