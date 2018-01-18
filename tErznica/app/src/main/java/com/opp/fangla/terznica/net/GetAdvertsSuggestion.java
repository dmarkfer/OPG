package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.interfaces.BuyerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.*;


public class GetAdvertsSuggestion extends AsyncTask<Void, Void, MatrixCursor> {

    private MutableLiveData<MatrixCursor> liveData;

    public GetAdvertsSuggestion(MutableLiveData<MatrixCursor> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected MatrixCursor doInBackground(Void... voids) {
        MatrixCursor result = new MatrixCursor(BuyerInterface.matrixColumns);
        Socket socket = new Socket();
        try {
            JSONObject json = new JSONObject();
            json.put("command", "RetrieveProductCategories");

            Log.d("Prod categ arguments", json.toString());
            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            //Log.d("Prod categ welcome", c.getText());
            String response = c.getText();
            Log.d("Prod categ result", response);
            c.close();
            c.disconnect();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("kategorije");
            for(int i = 0; i < array.length(); i++){
                Object[] row = new Object[2];
                row[0] = array.getJSONObject(i).get("idKategorije");
                row[1] = array.getJSONObject(i).get("naziv");
                result.addRow(row);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(MatrixCursor cursor) {
        liveData.postValue(cursor);
    }
}
