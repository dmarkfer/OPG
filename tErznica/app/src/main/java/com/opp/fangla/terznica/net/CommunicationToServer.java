package com.opp.fangla.terznica.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CommunicationToServer {
    private static final String FAIL="fail";
    private Socket accessPoint=null;
    private PrintWriter writeTo=null;
    private BufferedReader readFrom=null;
    private DataOutputStream writeToByte=null;
    private DataInputStream readFromByte=null;
    private StringBuilder buffer=new StringBuilder();

    //ssh development@165.227.175.217
    //schifra vladimirPutin
    //cd Server
    //java -jar Server.jar

    public CommunicationToServer(Socket socket) {
        accessPoint=socket;
        initializeStreams();
    }

    public void sendText(String text) {
        writeTo.println(text);
    }

    public String getText() {
        try {
            buffer.append(readFrom.readLine()+"\n");
            while(readFrom.ready()) {
                buffer.append(readFrom.readLine()+"\n");
            }

        } catch (IOException e) {
            System.out.println("Unable to get text from client");
            return FAIL;
        }
        String text=buffer.toString();
        buffer.setLength(0);
        return text;
    }

    public boolean sendImage(Bitmap image) {
        byte[] imageInBytes;
        try {
            imageInBytes=convertImageToByte(image);
            writeToByte.writeInt(imageInBytes.length);
            writeToByte.write(imageInBytes);
        } catch (IOException e) {
            System.out.println("Unable to read picture");
            return false;
        }
        return true;
    }

    //input stream to bitmap image
    public Bitmap getImage() {
        Bitmap fromByte=null;
        try {
            int len=readFromByte.readInt();
            if (len>0) {
                byte[] image=new byte[len];
                readFromByte.readFully(image,0,image.length);
                InputStream input = new ByteArrayInputStream(image);
                fromByte = BitmapFactory.decodeStream(input);
            }
        } catch (IOException e) {
            System.out.println("Reading image from stream error.");
            return null;
        }
        return fromByte;
    }

    public void initializeStreams() {
        try {
            writeTo=new PrintWriter(accessPoint.getOutputStream(),true);
            readFrom=new BufferedReader(new InputStreamReader(accessPoint.getInputStream(), StandardCharsets.UTF_8));
            writeToByte=new DataOutputStream(accessPoint.getOutputStream());
            readFromByte=new DataInputStream(accessPoint.getInputStream());

        }catch (IOException e) {
            System.out.println("Couldn't initialize IO streams. Terminating connection");
            System.exit(-1);

        }
    }

    public void close() {
        try {
            writeTo.close();
            readFrom.close();
            readFromByte.close();
            writeToByte.close();
            accessPoint.close();
        } catch (IOException Ignorable) {}
    }

    private byte[] convertImageToByte(Bitmap picture) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        baos.flush();

        byte[] array = baos.toByteArray();
        baos.close();
        return array;
    }

    public void disconnect(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", "terminate");
            sendText(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
