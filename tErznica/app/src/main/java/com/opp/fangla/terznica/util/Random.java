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

    public static Address parseStringToAddress (String str) {

        String[] tmp = str.split(",|\\.");
        Address address ;

        //TODO logitude i latitude popravi ovdje ako treba
        switch (tmp.length){
            case 1:
                address = setAddress(tmp[0],null,null,null,null);
                break;
            case 2:
                address = setAddress(tmp[0],tmp[1],null,null,null);
                break;
            case 3:
                address = setAddress(tmp[0],tmp[1],tmp[2],null,null);
                break;
            case 4:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],null);
                break;
            case 5:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]);
                break;
            default:
                address = new Address();

        }

        return address;
    }

    private static Address setAddress (String street, String num,String townNum ,String town, String state) {
        Address address = new Address();
        address.setState(state);
        address.setTown(town);
        address.setStreet(street);
        address.setNumber(num);
        address.setTownNum(townNum);

        return address;
    }

    public static Address getAddressFromPlace (Place place) {

        Address address ;
        String[] tmp = place.getAddress().toString().split(",|\\.");


        switch (tmp.length){
            case 1:
                address = setAddress(tmp[0],null,null,null,null);
                break;
            case 2:
                address = setAddress(tmp[0],tmp[1],null,null,null);
                break;
            case 3:
                address = setAddress(tmp[0],tmp[1],tmp[2],null,null);
                break;
            case 4:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],null);
                break;
            case 5:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]);
                break;
            default:
                address = new Address();

        }

        address.setLatLng(place.getLatLng());
        address.setPlaceId(place.getId());

        return address;
    }

    public static JSONObject addressToJSON (Address address) {

        JSONObject object = new JSONObject();
        //TODO check Janjic api when it is finished
        try {

            object.put("idMjesta",address.getPlaceId().toString());
            if(address.hasTown()){
                object.put("grad",address.getTown());
            }
            if(address.hasLatLng()) {
                object.put("latitude",address.getLatitude());
                object.put("longitude",address.getLongitude());
            }
            if(address.hasState()) {
                object.put("drzava",address.getState());
            }
            if(address.hasTownNum()) {
                object.put("postanskiBroj",address.getTownNum());
            }
            if(address.hasStreet()) {
                object.put("ulica",address.getStreet());
            }
            if(address.hasNumber()) {
                object.put("brojUlaza", address.getNumber());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object;
    }

    public static Address JSonToAddress (JSONObject object) {

        //TODO JsonToAddress
        Address address = new Address();

        try {
            address.setTown(object.getString("grad"));
        } catch (JSONException e) {
            address.setTown(null);
        }
        try {
            address.setTownNum(object.getString("postanskiBroj"));
        } catch (JSONException e) {
            address.setTownNum(null);
        }
        try {
            address.setNumber(object.getString("brojUlice"));
        } catch (JSONException e) {
            address.setNumber(null);
        }
        try {
            address.setStreet(object.getString("ulica"));
        } catch (JSONException e) {
            address.setStreet(null);
        }

        try {
            address.setState(object.getString("drzava"));
        } catch (JSONException e) {
            address.setState(null);
        }



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

    public static String dateToNormalString(Date date){
        DateFormat format = new SimpleDateFormat("dd. MM. YYYY. HH:mm", Locale.ENGLISH);
        return format.format(date);
    }
}
