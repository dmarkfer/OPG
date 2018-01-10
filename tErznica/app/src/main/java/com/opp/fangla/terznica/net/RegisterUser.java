package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.util.RegisterUserCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.net.LogIn.HOSTNAME;
import static com.opp.fangla.terznica.net.LogIn.PORT;

public class RegisterUser extends AsyncTask<JSONObject, Void, RegisterUserCallback> {

    private MutableLiveData<RegisterUserCallback> liveData;

    public RegisterUser(MutableLiveData<RegisterUserCallback> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected RegisterUserCallback doInBackground(JSONObject... jsonObjects) {
        RegisterUserCallback result = new RegisterUserCallback();
        Socket socket = new Socket();
        try {
            JSONObject json = jsonObjects[0];
            json.put("command", "RegisterUser");


            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            String sResponse = c.getText();

            Log.d("RegisterUser", sResponse);

            JSONObject response = new JSONObject(sResponse);
            result.setSuccess(response.getBoolean("success"));
            if(result.isSuccess()){
                result.setId(response.getInt("idKorisnika"));
            }
            Log.d("RegisterUser", response.toString());
            c.close();
            c.disconnect();
        } catch ( IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(RegisterUserCallback callback) {
        liveData.postValue(callback);
    }
}
