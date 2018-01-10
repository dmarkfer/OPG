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
			sql.append(arguments.get("idOKupca"));
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
