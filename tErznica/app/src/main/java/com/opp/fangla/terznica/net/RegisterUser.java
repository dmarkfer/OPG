package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.util.LogInCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.net.LogIn.HOSTNAME;
import static com.opp.fangla.terznica.net.LogIn.PORT;

public class RegisterUser extends AsyncTask<JSONObject, Void, LogInCallback> {

    private MutableLiveData<LogInCallback> liveData;

    public RegisterUser(MutableLiveData<LogInCallback> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected LogInCallback doInBackground(JSONObject... jsonObjects) {
        LogInCallback result = new LogInCallback();
        Socket socket = new Socket();
        try {
            JSONObject json = jsonObjects[0];
            json.put("command", "RegisterUser");


            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            JSONObject response = new JSONObject(c.getText());
            result.setSuccess(response.getBoolean("success"));
            if(result.isSuccess()){
                result.setAdmin(response.getBoolean("uloga"));
                result.setBuyer(response.getBoolean("kupac"));
                result.setVendor(response.getBoolean("poljprivrednik"));
                result.setDriver(response.getBoolean("prijevoznik"));
                result.setId(response.getInt("idKorisnika"));
            }

            c.close();
            c.disconnect();
        } catch ( IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(LogInCallback logInCallback) {
        liveData.postValue(logInCallback);
    }
}
