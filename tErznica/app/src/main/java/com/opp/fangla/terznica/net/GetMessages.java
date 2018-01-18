package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Message;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.opp.fangla.terznica.util.Random.*;



public class GetMessages extends AsyncTask<Integer,Void,List<Message>> {
    MutableLiveData<List<Message>> liveData;

    public GetMessages (MutableLiveData<List<Message>> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected List<Message> doInBackground(Integer... integers) {
        Socket socket = new Socket();
        String idChat = integers[0].toString();
        Message message;
        List<Message> messages = new ArrayList<>();

        try{
            JSONObject json = new JSONObject();
            json.put("command","retrieveMessages");
            json.put("idRazgovora",idChat);
            Log.d("Get Messages args", json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("RetreiveMessages result", sResponse);

            JSONObject response = new JSONObject(sResponse);
            JSONArray  array = response.getJSONArray("poruke");
            //retrieveMessages(idRazgovora) ->
            //(poruke[idPoruka, idRazgovora, vrijeme, poruka, idPosiljatelja])
            for (int i=0; i< array.length();i++) {
                message = new Message();
                message.setDate(setDateFromString(array.getJSONObject(i).getString("vrijeme")));
                message.setIdChat(Integer.valueOf(array.getJSONObject(i).getString("idRazgovora")));
                message.setIdSender(Integer.valueOf(array.getJSONObject(i).getString("idPosiljatelja")));
                message.setMessage(array.getJSONObject(i).getString("poruka"));
                message.setMessageId(array.getJSONObject(i).getInt("idPoruka"));
                messages.add(message);
            }

            c.close();
            c.disconnect();

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
            this.liveData.postValue(messages);
    }
}
