package testClient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public class TestClient {
	private static final String HOSTNAME="localhost";
	private static final int PORT=8080;
	private static ClientServerCommunication clientServerCommunication=null;
	private static final String path="/home/luka/pic.jpg";
	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket =new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
		clientServerCommunication=new ClientServerCommunication(socket);
		
		System.out.println(clientServerCommunication.getText());
		
		JSONObject request = new JSONObject();
		request.put("command", "RETRIEVEUSERPROFILE");
		request.put("idKorisnika", 5);
		clientServerCommunication.sendText(request.toString());
		
		
		String line =clientServerCommunication.getText();
		System.out.println(line);
			

	}
}