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

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;

public class DeleteComment extends AsyncTask<String,Void,Boolean> {

    private MutableLiveData<Boolean> liveData;

    public DeleteComment (MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        Socket socket = new Socket();
        Boolean bool = null;

        try{
            JSONObject json = new JSONObject();
            json.put("command","deleteComment");
            json.put("idOcjene",strings[0]);
            Log.d("deleteComment args",json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());


            Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("Delete comment result", sResponse);
            c.close();
            c.disconnect();

            JSONObject response = new JSONObject(sResponse);
            bool = response.getBoolean("success");


        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return bool;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
         liveData.postValue(aBoolean);
    }
}
