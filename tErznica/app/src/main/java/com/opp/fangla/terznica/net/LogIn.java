package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.util.LogInCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import static com.opp.fangla.terznica.util.Random.*;


public class LogIn extends AsyncTask<String, Void, LogInCallback> {

    private MutableLiveData<LogInCallback> liveData;


    public LogIn(MutableLiveData<LogInCallback> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected LogInCallback doInBackground(String... strings) {
        Socket socket = new Socket();
        LogInCallback result = new LogInCallback();
        try {
            JSONObject json = new JSONObject();
            json.put("command", "LoginUser");
            json.put("email", strings[0]);
            json.put("lozinka", strings[1]);
            Log.d("Login arguments", json.toString());


            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("Login server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("Login result", sResponse);
            c.close();
            c.disconnect();

            JSONObject response = new JSONObject(sResponse);
            result.setSuccess(response.getBoolean("success"));
            if(result.isSuccess()) {
                result.setId(response.getInt("idKorisnika"));
                result.setAdmin(response.getInt("uloga") == 1);
                result.setBuyer(response.getInt("kupac") == 1);
                result.setDriver(response.getInt("prijevoznik") == 1);
                result.setVendor(response.getInt("poljoprivrednik") == 1);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            result.setServerError(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(LogInCallback callback) {
        liveData.postValue(callback);
    }
}
