package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RetrieveUserProfile extends AbstractCommand {
	
	String[] columns = new String[] {"ime","prezime","lozinka","email","telefon",
			"poljoprivrednik","kupac","prijevoznik"};

	String[] columnsopg = new String[] {"naziv_opg","oib_opg","adresa_opg","slika_opg","opis_opg","iban"};
	String[] columnsopg2 = new String[] {"nazivOPG", "OIBOPG", "adresaOPG", "slikaOPG", "opisOPG", "IBAN"};
	
	String[] columnsVozilo = new String[] {"registarska_oznaka", "slika_vozila", "opis_vozila", "id_kategorije_vozila"};
	String[] columnsVozilo2 = new String[] {"registarskaOznaka", "slikaVozila", "opisVozila", "idKategorijeVozila"};
	
	public RetrieveUserProfile() {
		super("RETRIEVEUSERPROFILE", "Retrieves user profile info.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM korisnik WHERE id=");
			sql.append(arguments.get("idKorisnika"));
			sql.append(";");
			
			ResultSet userProfile = statement.executeQuery(sql.toString());
			userProfile.next();
			for(int i = 0; i < columns.length; ++i) {
				returnObject.put(columns[i], userProfile.getString(columns[i]));
			}
			
			if(returnObject.getString("poljoprivrednik").equals("1")) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append("SELECT * FROM poljoprivrednik WHERE id_korisnika=");
				sql2.append(arguments.get("idKorisnika"));
				sql2.append(";");
				
				ResultSet opgProfile = statement.executeQuery(sql2.toString());
				opgProfile.next();
				for(int i = 0; i < columnsopg.length; ++i) {
					returnObject.put(columnsopg2[i], opgProfile.getString(columnsopg[i]));
				}
			}
			
			if(returnObject.getString("prijevoznik").equals("1")) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append("SELECT opis_prijevoza FROM prijevoznik WHERE id_korisnika=");
				sql2.append(arguments.get("idKorisnika"));
				sql2.append(";");
				
				ResultSet prijevoznikProfile = statement.executeQuery(sql2.toString());
				prijevoznikProfile.next();
				returnObject.put("opisPrijevoza", prijevoznikProfile.getString("opis_prijevoza"));
				
				StringBuilder sql3 = new StringBuilder();
				sql3.append("SELECT * FROM vozilo WHERE id_korisnika=");
				sql3.append(arguments.get("idKorisnika"));
				sql3.append(";");
				
				prijevoznikProfile = statement.executeQuery(sql3.toString());
				JSONArray vozila = new JSONArray();
				while(prijevoznikProfile.next()) {
					JSONObject vozilo = new JSONObject();
					for(int i = 0; i < columnsVozilo.length; ++i) {
						vozilo.put(columnsVozilo2[i], prijevoznikProfile.getString(columnsVozilo[i]));
					}
					vozila.put(vozilo);
				}
				returnObject.put("vozila", vozila);
			}
			
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			environment.sendText("false");
		}
		return CommandStatus.CONTINUE;
	}

}
