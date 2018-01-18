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

public class RegisterUser extends AbstractCommand {
	
	String[] columns = new String[] {"ime","prezime","lozinka","email","telefon","uloga",
			"blokiran","poljoprivrednik","kupac","prijevoznik","potvrden"};
	
	String[] columnsOpg = new String[] {"idKorisnika", "nazivOPG", "OIBOPG", "adresaOPG", "slikaOPG", "opisOPG", "IBAN"};
	
	String[] columnsPrijevoz = new String[] {"registarskaOznaka", "slikaVozila", "opisVozila", "idKategorijeVozila","idKorisnika"};
	
	String[] columnsAdresa = new String[] {"drzava", "grad", "ulica", "postanskiBroj", "idMjesta", "latitude", "longitude", "brojUlaza"};

	public RegisterUser() {
		super("REGISTERUSER", "Command registers new user.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {	
		
		arguments.put("uloga", 0);
		arguments.put("blokiran", 0);
		arguments.put("potvrden", 0);
		
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO korisnik VALUES (default,");
			for(int i = 0; i < columns.length; ++i) {
				sql.append("'" + arguments.get(columns[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			
			sql = new StringBuilder();
			sql.append("SELECT id FROM korisnik WHERE email='");
			sql.append(arguments.get("email"));
			sql.append("';");
			
			ResultSet id = statement.executeQuery(sql.toString());
			id.next();
			returnObject.put("idKorisnika", id.getString("id"));
			arguments.put("idKorisnika", id.getString("id"));
			
			if(arguments.get("poljoprivrednik").equals(1)) {
				sql = new StringBuilder();
				sql.append("INSERT INTO adresa VALUES(default,");
				JSONObject adresaJSON = arguments.getJSONObject("poljoprivrednikJSON").getJSONObject("adresaOPG");
				for(int i = 0; i < columnsAdresa.length; ++i) {
					if(!adresaJSON.has(columnsAdresa[i])) {
						sql.append("null,");
						continue;
					}
					sql.append("'" + adresaJSON.get(columnsAdresa[i]) + "',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(");");
				
				statement.executeUpdate(sql.toString());
				
				ResultSet idAdrese = statement.executeQuery("SELECT MAX(id) AS id FROM adresa;");
				idAdrese.next();
				arguments.getJSONObject("poljoprivrednikJSON").put("adresaOPG", idAdrese.getString("id"));
								
				sql = new StringBuilder();
				sql.append("INSERT INTO poljoprivrednik VALUES(");
				JSONObject poljoprivrednikJSON = arguments.getJSONObject("poljoprivrednikJSON");
				poljoprivrednikJSON.put("idKorisnika", arguments.get("idKorisnika"));
				for(int i = 0; i < columnsOpg.length; ++i) {
					sql.append("'" + poljoprivrednikJSON.get(columnsOpg[i]) + "',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(");");
				
				statement.executeUpdate(sql.toString());
			}
			
			if(arguments.get("prijevoznik").equals(1)) {
				sql = new StringBuilder();
				sql.append("INSERT INTO prijevoznik VALUES(");
				sql.append(arguments.get("idKorisnika"));
				sql.append(",'");
				sql.append(arguments.get("opisPrijevoza"));
				sql.append("');");
				
				statement.executeUpdate(sql.toString());
				
				JSONArray vozila = arguments.getJSONArray("vozila");
				
				for(int i = 0; i < vozila.length(); ++i) {
					JSONObject vozilo = vozila.getJSONObject(i);
					vozilo.put("idKorisnika", arguments.get("idKorisnika"));
					sql = new StringBuilder();
					sql.append("INSERT INTO vozilo VALUES(default,");
					for(int j = 0; j < columnsPrijevoz.length; ++j) {
						sql.append("'" + vozilo.get(columnsPrijevoz[j]) + "',");
					}
					sql.deleteCharAt(sql.length()-1);
					sql.append(");");
					
					statement.executeUpdate(sql.toString());
				}
			}
			
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}
}
