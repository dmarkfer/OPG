package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;


public class SendMessage extends AsyncTask<Message,Void,Boolean> {

    private MutableLiveData<Boolean> liveData;

    public  SendMessage (MutableLiveData<Boolean> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Boolean doInBackground(Message... messages) {
        Socket socket = new Socket();
        Message message = messages[0];
        Boolean bool = null;

        //>sendMessage(idRazgovora, idPosiljatelja, vrijeme, poruka) -> (success)
        try{
            JSONObject json = new JSONObject();
            json.put("command","sendMessage");
            json.put("idRazgovora",message.getIdChat());
            json.put("idPosiljatelja",message.getIdSender());
            json.put("vrijeme",message.getDate().toString());
            json.put("poruka",message.getMessage());

            Log.d("SendMessage args",json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //Log.d("Server welcome ", c.getText());
            String sResponse = c.getText();
            Log.d("Server response", sResponse);

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
        this.liveData.postValue(aBoolean);
    }
}

