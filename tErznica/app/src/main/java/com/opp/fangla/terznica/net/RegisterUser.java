package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.util.LogInCallback;
import com.opp.fangla.terznica.util.RegisterUserCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.*;


//Kad se slozi AdministratorInterface, treba vratiti objekt u verziju s RegisterUserCallbackom

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
            json.put("command", "RegisterUser args");


            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("RegisterUser welcome", c.getText());
            String sResponse = c.getText();
            Log.d("RegisterUser response", sResponse);

            JSONObject response = new JSONObject(sResponse);
            result.setSuccess(response.getBoolean("success"));
            if(result.isSuccess()){
                result.setId(response.getInt("idKorisnika"));
                result.setBuyer(jsonObjects[0].getInt("kupac") == 1);
                result.setVendor(jsonObjects[0].getInt("poljoprivrednik") == 1);
                result.setDriver(jsonObjects[0].getInt("prijevoznik") == 1);
            }
            c.close();
            c.disconnect();
        } catch ( IOException | JSONException e) {
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

/*public class RegisterUser extends AsyncTask<JSONObject, Void, RegisterUserCallback> {

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
                result.setIdVehicle(response.getInt("idKorisnika"));
            }
            Log.d("RegisterUser", response.toString());
            c.close();
            c.disconnect();
        } catch ( IOException | JSONException e) {
            e.printStackTrace();
            result.setServerError(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(RegisterUserCallback callback) {
        liveData.postValue(callback);
    }
}*/
