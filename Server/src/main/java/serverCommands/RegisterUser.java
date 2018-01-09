package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RegisterUser extends AbstractCommand {
	
	String[] columns = new String[] {"ime","prezime","lozinka","email","telefon","uloga",
			"blokiran","poljoprivrednik","kupac","prijevoznik","potvrden"};
	
	String[] columnsOpg = new String[] {"idKorisnika", "nazivOPG", "OIBOPG", "adresaOPG", "slikaOPG", "opisOPG", "IBAN"};
	
	String[] columnsPrijevoz = new String[] {"registarskaOznaka", "slikaVozila", "opisVozila", "idKategorijeVozila","idKorisnika"};	

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
				sql.append("INSERT INTO poljoprivrednik VALUES(");
				for(int i = 0; i < columnsOpg.length; ++i) {
					sql.append("'" + arguments.get(columnsOpg[i]) + "',");
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
				
				sql = new StringBuilder();
				sql.append("INSERT INTO vozilo VALUES(default,");
				for(int i = 0; i < columnsPrijevoz.length; ++i) {
					sql.append("'" + arguments.get(columnsPrijevoz[i]) + "',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(");");
				
				statement.executeUpdate(sql.toString());
				
				sql = new StringBuilder();
				sql.append("SELECT id FROM vozilo WHERE id_korisnika=");
				sql.append(arguments.get("idKorisnika"));
				sql.append(";");
				
				id = statement.executeQuery(sql.toString());
				id.next();
				returnObject.put("idVozila", id.getString("id"));
			}
			
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}
}
