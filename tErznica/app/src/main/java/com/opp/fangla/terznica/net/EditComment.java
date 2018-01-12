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

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;


public class EditComment extends AsyncTask <Comment,Void,Boolean> {

    private MutableLiveData<Boolean> liveData;

    public  EditComment(MutableLiveData liveData) {
        this.liveData=liveData;
    }


    @Override
    protected Boolean doInBackground(Comment... comments) {
        Socket socket = new Socket();
        Comment comment = comments[0];
        Boolean bool = null;
        try {

            //editComment(idOcjene, ocjena, komenatar, vrijeme) -> (success)
            JSONObject json = new JSONObject();
            json.put("command", "editComment");
            json.put("idOcjene",comment.getIdReviewMark());
            json.put("ocjena",comment.getReviewMark().toString());
            json.put("komentar",comment.getComment());
            json.put("vrijeme",comment.getTime().toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());
            Log.d("Create comment args", json.toString());

            Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("Server response", sResponse);
            c.close();
            c.disconnect();

            JSONObject response = new JSONObject();
            bool = response.getBoolean(sResponse);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return bool;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
            liveData.postValue(aBoolean);
    }
}
