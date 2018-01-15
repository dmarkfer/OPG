package testClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONArray;
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
		
		/* CONFIRMPRODUCTCATEGORY 
		request.put("command", "CONFIRMPRODUCTCATEGORY");
		request.put("idKategorije", 1); */
		 
		 /* CREATECOMMENT
		request.put("command", "CREATECOMMENT");
		request.put("idOcjenjenog", 18);
		request.put("idOcjenjivaca", 17);
		request.put("ocjena", 3);
		request.put("komentar", "blA");
		request.put("vrijeme", new Date()); */
		
		/* CREATECONVERSATION 
		request.put("command", "CREATECONVERSATION");
		request.put("idPrijevoznikaIliPoljoprivrednika", 5);
		request.put("idKupca", 10);
		request.put("idOglasa", 4);
		request.put("idOglasaPrijevoza", 1); */
		
		/* CREATEPRODUCT
		request.put("command", "CREATEPRODUCT");
		request.put("idKorisnika", 10);
		request.put("idKategorijeOglasa", 1);
		request.put("nazivOglasa", "jabkeee");
		request.put("slikaOglasa", "slik");
		request.put("opisOglasa", "opis");
		request.put("cijena", 12);
		request.put("vrijeme", new Date()); */
		
		/* CREATEPRODUCTCATEGORY 
		request.put("command", "CREATEPRODUCTCATEGORY");
		request.put("idKorisnika", 10);
		request.put("nazivKategorije", "Jagode2");
		request.put("komentar", "blabla"); */
		
		/* CREATEREPORT 
		request.put("command", "CREATEREPORT");
		request.put("idKorisnika", 10);
		request.put("tipPrijave", 2);
		request.put("idPrijavljeneStavke", 4);
		request.put("idVrstePrijave", 1);
		request.put("komentar", "komentar"); */
		
		/* CREATESHIPMENTOFFER
		request.put("command", "CREATESHIPMENTOFFER");
		request.put("idOglasa", 4);
		request.put("idKupca", 5);
		request.put("polaziste", "polazisze");
		request.put("odrediste", "dest");
		request.put("vrijeme", new Date()); */
		
		/* CREATEVEHICLE 
		request.put("command", "CREATEVEHICLE");
		request.put("idKorisnika", 5);
		request.put("registarskaOznaka", "zg3432423");
		request.put("idKategorijeVozila", 1);
		request.put("opisVozila", "ops");
		request.put("slikaVozila", "ddfewewf.jpg");	*/			
 
		/* DELETECOMMENT
		request.put("command", "DELETECOMMENT");
		request.put("idOcjene", 4); */
		
		/* DELETEPRODUCT 
		request.put("command", "DELETEPRODUCT");
		request.put("idOglasa", 6); */
		
		/* DELETEPRODUCTCATEGORY 
		request.put("command", "DELETEPRODUCTCATEGORY");
		request.put("idKategorije", 3); */
		
		/* DELETESHIPMENTOFFER 
		request.put("command", "DELETESHIPMENTOFFER");
		request.put("idOglasaPrijevoza", 3); */
		
		/* DELETEUSER 
		request.put("command", "DELETEUSER");
		request.put("idKorisnika", 19); */
		
		/* DELETEVEHICLE
		request.put("command", "DELETEVEHICLE");
		request.put("idVozila", 7); */
		
		/* EDITCOMMENT 
		request.put("command", "EDITCOMMENT");
		request.put("idOcjene", 4);
		request.put("ocjena", 5);
		request.put("komentar", "novo");
		request.put("vrijeme", new Date()); */
		
		/* EDITPRODUCT 
		request.put("command", "EDITPRODUCT");
		request.put("idOglasa", 7);
		request.put("idKategorijeOglasa", 2);
		request.put("nazivOglasa", "jabkeee!!!");
		request.put("slikaOglasa", "slik!");
		request.put("opisOglasa", "opis!");
		request.put("cijena", 13);
		request.put("vrijeme", new Date()); */
		
		/* GETADMINTASKS
		request.put("command", "GETADMINTASKS"); */
		
		/* GETCONVERSATIONS 
		request.put("command", "GETCONVERSATIONS");
		request.put("idKorisnika", 5); */
		
		/* LOGINUSER 
		request.put("command", "LOGINUSER");
		request.put("email", "domagoj.kralj@gmail.com");
		request.put("lozinka", "0000"); */
		
		/* REGISTERUSER	
		request.put("command", "REGISTERUSER");
		request.put("ime", "Meho");
		request.put("prezime", "Puzić");
		request.put("lozinka", "Puzić");
		request.put("email", "mail@bla.hr");
		request.put("telefon", "654746");
		request.put("poljoprivrednik", 1);
		request.put("kupac", 1);
		request.put("prijevoznik", 1);
		JSONObject request2 = new JSONObject();
		request2.put("nazivOPG", "opgg");
		request2.put("OIBOPG", "45452");
		request2.put("adresaOPG", "oddpgg");
		request2.put("slikaOPG", "slki");
		request2.put("opisOPG", "opsfad");
		request2.put("IBAN", "456565435");
		request.put("poljoprivrednikJSON", request2);
		JSONArray array = new JSONArray();
		JSONObject request3 = new JSONObject();
		request3.put("registarskaOznaka", "zg454534DFD");
		request3.put("idKategorijeVozila", 1);
		request3.put("opisVozila", "opisddfas");
		request3.put("slikaVozila", "slikica");
		array.put(request3);
		request3 = new JSONObject();
		request3.put("registarskaOznaka", "st454534DFD");
		request3.put("idKategorijeVozila", 1);
		request3.put("opisVozila", "bldfldl");
		request3.put("slikaVozila", "sliketina");
		array.put(request3);
		request.put("vozila", array);
		request.put("opisPrijevoza", "opisprijevza");  */
		
	
		/* RETRIEVEMESSAGES 
		request.put("command", "RETRIEVEMESSAGES");
		request.put("idRazgovora", 12); */
		
		/* RETRIEVEPRODUCTCATEGORIES
		request.put("command", "RETRIEVEPRODUCTCATEGORIES"); */
		
		/* RETRIEVEPRODUCTOFFERS 
		request.put("command", "RETRIEVEPRODUCTOFFERS");
		request.put("idKategorijeOglasa", 1);
		request.put("naziv", "ab");
		request.put("brojTrazenihOglasa", 3); */
		
		/* RETRIEVESHIPMENTOFFERS 
		request.put("command", "RETRIEVESHIPMENTOFFERS");
		request.put("nazivMjesta", "ij"); */
		
		/* RETRIEVEUSERPROFILE	
		request.put("command", "RETRIEVEUSERPROFILE");
		request.put("idKorisnika", 5); */
		
		/* RETRIEVEVEHICLES 
		request.put("command", "RETRIEVEVEHICLES");
		request.put("idKorisnika", 5); */
		
		/* SENDMESSAGE 
		request.put("command", "SENDMESSAGE");
		request.put("idRazgovora", 6);
		request.put("idPosiljatelja", 23);
		request.put("vrijeme", new Date());
		request.put("poruka", "evo ti na!"); */
		
		
		clientServerCommunication.sendText(request.toString());
		
		String line =clientServerCommunication.getText();
		System.out.println(line);
			

	}
}