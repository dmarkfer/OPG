package testClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

public class TestClient {
	private static final String HOSTNAME="localhost";
	private static final int PORT=8080;
	private static ClientServerCommunication clientServerCommunication=null;
	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket =new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(HOSTNAME),PORT));
		clientServerCommunication=new ClientServerCommunication(socket);
		
		System.out.println(clientServerCommunication.getText());
		
		JSONObject request = new JSONObject();
		
		/* LOGINUSER */
		request.put("command", "LOGINUSER");
		request.put("email", "vv");
		request.put("lozinka", "bla");
		
		/* REGISTERUSER	
		request.put("command", "REGISTERUSER");
		request.put("ime", "Meho");
		request.put("prezime", "Puzić");
		request.put("lozinka", "Puzić");
		request.put("email", "Puzsisć@bla.hr");
		request.put("telefon", "654746");
		request.put("poljoprivrednik", 1);
		request.put("kupac", 1);
		request.put("prijevoznik", 1);
		request.put("nazivOPG", "opgg");
		request.put("OIBOPG", "45452");
		request.put("adresaOPG", "oddpgg");
		request.put("slikaOPG", "slki");
		request.put("opisOPG", "opsfad");
		request.put("IBAN", "456565435");
		request.put("registarskaOznaka", "zg454534DFD");
		request.put("idKategorijeVozila", 1);
		request.put("opisVozila", "opisddfas");
		request.put("slikaVozila", "slikica");
		request.put("opisPrijevoza", "opisprijevza"); */
		
		
		/* RETRIEVEUSERPROFILE	
		request.put("command", "RETRIEVEUSERPROFILE");
		request.put("idKorisnika", 5); */
		
		
		clientServerCommunication.sendText(request.toString());
		
		String line =clientServerCommunication.getText();
		System.out.println(line);
			

	}
}