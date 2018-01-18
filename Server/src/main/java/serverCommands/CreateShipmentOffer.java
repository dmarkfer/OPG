package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateShipmentOffer extends AbstractCommand {
	
	String[] columnsAdresa = new String[] {"drzava", "grad", "ulica", "postanskiBroj", "idMjesta", "latitude", "longitude", "brojUlaza"};
	
	
	public CreateShipmentOffer() {
		super("CREATESHIPMENTOFFER", "Create shipment offer.");
	}
	
	
	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO adresa VALUES(default,");
			JSONObject adresaJSON = arguments.getJSONObject("polaziste");
			for(int i = 0; i < columnsAdresa.length; ++i) {
				sql.append("'" + adresaJSON.get(columnsAdresa[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			
			ResultSet idAdrese = statement.executeQuery("SELECT MAX(id) AS id FROM adresa;");
			idAdrese.next();
			arguments.put("polaziste", idAdrese.getString("id"));
			
			sql = new StringBuilder();
			sql.append("INSERT INTO adresa VALUES(default,");
			adresaJSON = arguments.getJSONObject("odrediste");
			for(int i = 0; i < columnsAdresa.length; ++i) {
				sql.append("'" + adresaJSON.get(columnsAdresa[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			
			idAdrese = statement.executeQuery("SELECT MAX(id) AS id FROM adresa;");
			idAdrese.next();
			arguments.put("odrediste", idAdrese.getString("id"));
			
			sql = new StringBuilder();
			sql.append("INSERT INTO oglas_prijevoz VALUES (default,");
			sql.append(arguments.get("idOglasa"));
			sql.append(",");
			sql.append(arguments.get("idKupca"));
			sql.append(",'");
			sql.append(arguments.get("polaziste"));
			sql.append("','");
			sql.append(arguments.get("odrediste"));
			sql.append("','");
			sql.append(arguments.get("vrijeme"));
			sql.append("');");
			
			statement.execute(sql.toString());
			
			sql = new StringBuilder();
			sql.append("SELECT id FROM oglas_prijevoz WHERE id_oglasa=");
			sql.append(arguments.get("idOglasa"));
			sql.append(" AND id_kupca=");
			sql.append(arguments.get("idKupca"));
			sql.append(" AND polaziste='");
			sql.append(arguments.get("polaziste"));
			sql.append("' AND odrediste='");
			sql.append(arguments.get("odrediste"));
			sql.append("' AND vrijeme='");
			sql.append(arguments.get("vrijeme"));
			sql.append("';");
			
			ResultSet id = statement.executeQuery(sql.toString());
			id.next();
			returnObject.put("idOglasaPrijevoza", id.getString("id"));
			returnObject.put("success", true);
			
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}

}
