package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;
import static com.opp.fangla.terznica.util.Random.setDateFromString;

public class GetConversations extends AsyncTask<Integer,Void,List<Conversation>> {
    MutableLiveData<List<Conversation>> liveData;

    public GetConversations (MutableLiveData<List<Conversation>> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected List<Conversation> doInBackground(Integer... integers) {
        Socket socket = new Socket();
        String idChat = integers[0].toString();
        Message message;
        Conversation conversation;
        List<Conversation> conversations = new ArrayList<>();

        try{
            JSONObject json = new JSONObject();
            json.put("command","getConversations");
            json.put("idKorisnika",idChat);
            Log.d("Get Convos args", json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("GetConvos result", sResponse);

            JSONObject response = new JSONObject(sResponse);
            JSONArray array = response.getJSONArray("razgovori");
            for (int i=0; i< array.length();i++) {
                JSONObject jConversation = array.getJSONObject(i);
                conversation = new Conversation();
                conversation.setIdConversation(jConversation.getLong("idRazgovora"));
                conversation.setIdAdvert(jConversation.getLong("idOglasa"));
                conversation.setIdAdvertTransport(jConversation.getLong("idOglasaPrijevoza"));
                conversation.setIdBuyer(jConversation.getInt("sudionik"));
                conversation.setFinished(jConversation.getBoolean("gotovRazgovor"));

                message = new Message();
                message.setDate(setDateFromString(jConversation.getString("vrijeme")));
                message.setIdChat(Integer.valueOf(jConversation.getString("idRazgovora")));
                message.setIdSender(Integer.valueOf(jConversation.getString("idPosiljatelja")));
                message.setMessage(jConversation.getString("poruka"));
                message.setMessageId(jConversation.getInt("idPoruka"));
                conversation.setFirstMessage(message);
            }

            c.close();
            c.disconnect();

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    @Override
    protected void onPostExecute(List<Conversation> conversations) {
        this.liveData.postValue(conversations);
    }

}
