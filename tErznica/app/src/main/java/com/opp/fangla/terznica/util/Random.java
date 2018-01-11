package com.opp.fangla.terznica.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Random {


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
}
