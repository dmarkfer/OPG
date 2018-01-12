package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.opp.fangla.terznica.data.entities.Report;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.opp.fangla.terznica.util.Random.HOSTNAME;
import static com.opp.fangla.terznica.util.Random.PORT;

//createReport(idKorisnika, tipPrijave, idPrijavljeneStavke, idVrstePrijave, komentar) -> (idPrijave)

public class CreateReport extends AsyncTask<Report,Void,Integer> {

    private  MutableLiveData<Integer> liveData;
    public CreateReport (MutableLiveData<Integer> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Integer doInBackground(Report... reports) {
        Socket socket = new Socket();
        Report report = reports[0];
        Integer reportId = null;

        try {

            //createReport(idKorisnika, tipPrijave, idPrijavljeneStavke, idVrstePrijave, komentar)
            // -> (idPrijave)
            JSONObject json = new JSONObject();
            json.put("command","createReport");
            json.put("idKorisnika",report.getUserId());
            json.put("tipPrijave",report.getReportType());
            json.put("idPrijavljeneStavke",report.getReportedItemId());
            json.put("idVrstePrijave",report.getReportTypeId());
            json.put("komentar",report.getComment());
            Log.d("CreateReport args",json.toString());

            socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
            CommunicationToServer c = new CommunicationToServer(socket);
            c.sendText(json.toString());

            Log.d("Server welcome", c.getText());
            String sResponse = c.getText();
            Log.d("Server response", sResponse);

            c.close();
            c.disconnect();

            JSONObject response = new JSONObject(sResponse);
            reportId = Integer.valueOf(response.getString("idPrijave"));


        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return reportId;
    }
}
