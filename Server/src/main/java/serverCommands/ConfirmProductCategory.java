package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class ConfirmProductCategory extends AbstractCommand {

	public ConfirmProductCategory() {
		super("CONFIRMPRODUCTCATEGORY", "Confrims suggested product category.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT naziv_kategorije FROM nova_kategorija_oglasa WHERE id=");
			sql.append(arguments.get("idKategorije"));
			sql.append(";");
			
			ResultSet kategorija = statement.executeQuery(sql.toString());
			kategorija.next();		
			
			sql = new StringBuilder();
			sql.append("INSERT INTO kategorija_oglasa VALUES (default,'");
			sql.append(kategorija.getString("naziv_kategorije"));
			sql.append("');");
			
			statement.executeUpdate(sql.toString());
			
			sql = new StringBuilder();
			sql.append("DELETE FROM nova_kategorija_oglasa WHERE id=");
			sql.append(arguments.get("idKategorije"));
			sql.append(";");
			
			statement.executeUpdate(sql.toString());
			
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
