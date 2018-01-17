package com.opp.fangla.terznica.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.opp.fangla.terznica.data.entities.Address;
import com.opp.fangla.terznica.net.CommunicationToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.util.Log.d;

public class Random {

    public static final String HOSTNAME = "165.227.175.217";
    public static final int PORT = 8080;

    public static Bitmap convertByteToBitMap (String bytes) {
        Bitmap bitmap;

        byte[] bitmapdata = bytes.getBytes();
        bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        return bitmap;
    }


    public static Date setDateFromString (String d) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date;

        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return date;
    }

    public static Address getAddressFromPlace (Place place) {

        Address address = new Address();
        address.setLatLng(place.getLatLng());
        address.setPlaceId(place.getId());
        String[] coreAddr = new String[4];
        String[] tmp = place.getAddress().toString().split(",|.");
        // TODO finish getAddressFromPlace


        return null;
    }

    /**
     * NAPOMENA: Stringovi u logs ne smiju imati vise od 23 char-a.
     * NAPOMENA: Naredbe u commands moraju biti poslozene u istom redosljedu kao i vrijednosti u
     * values. S izuzetko da commands ima u sebi ime naredbe.
     * PRIMJER: commands: retrieveProductOffers, idKategorijeOglasa, naziv, brojTrazenihOglasa
     *          values:  idCategory, ProductName, numOfAdverts
     * @param commands
     * @param values
     * @param log
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static String sendMessageToServer (String[] commands, String[] values,String log ) {
        Socket socket = new Socket();
        String sResponse = null;

       try {
           JSONObject json = new JSONObject();
           json.put("command", commands[0]);

           for (int i = 0; i < values.length; i++) {
               json.put(commands[i + 1], values[i]);
           }

           d(log, json.toString());

           socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME), PORT));
           CommunicationToServer c = new CommunicationToServer(socket);
           c.sendText(json.toString());

           d("Server welcome", c.getText());
           sResponse = c.getText();
           d("Server response", sResponse);
           c.close();
           c.disconnect();
       }catch (IOException | JSONException e) {
           e.printStackTrace();
       }
        return sResponse;
    }

    public static String dateToString(Date date){
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.ENGLISH);
        return format.format(date);
    }
}
