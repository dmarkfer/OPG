package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class DeleteUser extends AsyncTask<String, Void, Boolean> {

    protected static final String HOSTNAME = "165.227.175.217";
    protected static final int PORT = 8080;
    private MutableLiveData<Boolean> liveData;

    public DeleteUser(MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }
    @Override
    protected Boolean doInBackground(String... strings) {
        Socket socket = new Socket();
        Boolean b;
        try{
            JSONObject json = new JSONObject();
            json.put("command","deleteUser");
            json.put("idKorisnika", strings[0]);

            Log.d("Delete User args ", json.toString());
            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("Server welcome",c.getText());
            String sResponse = c.getText();
            Log.d("Server response",sResponse);
            c.close();
            c.disconnect();
            JSONObject response = new JSONObject(sResponse);
            b = response.getBoolean("success");


        }catch (IOException | JSONException e) {

            e.getStackTrace();
            return null;
        }


        return b;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
            liveData.postValue(aBoolean);
    }
}
