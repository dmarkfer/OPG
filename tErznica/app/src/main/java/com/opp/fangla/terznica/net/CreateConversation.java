package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Conversation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;


public class CreateConversation extends AsyncTask<Conversation,Void,String> {


    private  MutableLiveData<String> liveData;


    public CreateConversation (MutableLiveData<String> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected String doInBackground(Conversation... conversations) {

        Socket socket = new Socket();
        Conversation conversation = conversations[0];
        String idConversation = null;

        try {

            JSONObject json = new JSONObject();
            json.put("command","createConversation");
            json.put("idPrijevoznika", conversation.getIdDriver());
            json.put("idKupca", conversation.getIdBuyer());
            json.put("idOglasa",conversation.getIdAdvert());
            json.put("idOglasaPrijevoza",conversation.getIdAdvertTransport());
            Log.d("CreateConversation args",json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("Server response", sResponse);
            c.close();
            c.disconnect();

            idConversation = json.getString(sResponse);

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return idConversation;
    }

    @Override
    protected void onPostExecute(String s) {
        liveData.postValue(s);
    }
}
