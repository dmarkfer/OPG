package testClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

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
		
		/* CREATECOMMENT 
		request.put("command", "CREATECOMMENT");
		request.put("idOcjenjenog", 18);
		request.put("idOcjenjivaca", 17);
		request.put("ocjena", 3);
		request.put("komentar", "blA");
		request.put("vrijeme", new Date()); */
		
		/* CREATECONVERSATION 
		request.put("command", "CREATECONVERSATION");
		request.put("idPrijevoznika", 5);
		request.put("idKupca", 10);
		request.put("idOglasa", 4);
		request.put("idOglasaPrijevoza", 1); */
		
		/* CREATEPRODUCT */
		request.put("command", "CREATEPRODUCT");
		request.put("idKorisnika", 10);
		request.put("idKategorijeOglasa", 1);
		request.put("nazivOglasa", "jabkeee");
		request.put("slikaOglasa", "slik");
		request.put("opisOglasa", "opis");
		request.put("cijena", 12);
		request.put("vrijeme", new Date());
 
		/* DELETECOMMENT
		request.put("command", "DELETECOMMENT");
		request.put("idOcjene", 4); */
		
		/* DELETEUSER 
		request.put("command", "DELETEUSER");
		request.put("idKorisnika", 19); */
		
		/* EDITCOMMENT 
		request.put("command", "EDITCOMMENT");
		request.put("idOcjene", 4);
		request.put("ocjena", 5);
		request.put("komentar", "novo");
		request.put("vrijeme", new Date()); */
		
		/* LOGINUSER 
		request.put("command", "LOGINUSER");
		request.put("email", "vv");
		request.put("lozinka", "bla"); */
		
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
		
	
		/* RETRIEVEMESSAGES 
		request.put("command", "RETRIEVEMESSAGES");
		request.put("idRazgovora", 12); */
		
		
		/* RETRIEVEUSERPROFILE	
		request.put("command", "RETRIEVEUSERPROFILE");
		request.put("idKorisnika", 5); */
		
		/* SENDMESSAGE 
		request.put("command", "SENDMESSAGE");
		request.put("idRazgovora", 12);
		request.put("idPosiljatelja", 5);
		request.put("vrijeme", new Date());
		request.put("poruka", "evo ti na!"); */
		
		
		clientServerCommunication.sendText(request.toString());
		
		String line =clientServerCommunication.getText();
		System.out.println(line);
			

	}
}