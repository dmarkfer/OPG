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
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by domagoj on 30.12.17..
 */

public class LogIn extends AsyncTask<String, Void, String> {

    private MutableLiveData<String> liveData;
    private final String HOSTNAME = "165.227.175.217";
    private final int PORT = 8080;

    public LogIn(MutableLiveData<String> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected String doInBackground(String... strings) {
        Socket socket = new Socket();
        try {
            JSONObject json = new JSONObject();
            json.put("command", "LoginUser");
            json.put("email", "");
            json.put("lozinka", "");


            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //c.getText();
            Log.d("Login", "AAAAAAAAAAAAAAAAAAA " + c);

            c.close();
            c.disconnect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        liveData.postValue(s);
    }
}
