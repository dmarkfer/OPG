package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.*;


public class CreateComment extends AsyncTask<Comment,Void, String> {

    private MutableLiveData<String> liveData;

    public CreateComment (MutableLiveData liveData) {
        this.liveData = liveData;
    }


    @Override
    protected String doInBackground(Comment... comments) {
        Socket socket = new Socket();
        Comment comment = comments[0];
        String idReviewMark = null;
        try{

            JSONObject json = new JSONObject();
            json.put("command","createComment");
            json.put("idOcjenjenog",comment.getIdCommentReceiver());
            json.put("idOcjenjivaca",comment.getIdCommentGiver());
            json.put("ocjena",comment.getReviewMark());
            json.put("komentar",comment.getComment());
            json.put("vrijeme",comment.getTime().toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());
            Log.d("Create comment args", json.toString());

            Log.d("Server welcome",c.getText());
            idReviewMark = c.getText();
            Log.d("Server response",idReviewMark);
            c.close();
            c.disconnect();

            idReviewMark = json.getString(idReviewMark);

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return idReviewMark;

    }

    @Override
    protected void onPostExecute(String s) {
            liveData.postValue(s);
    }
}
