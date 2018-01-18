package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateVehicle extends AbstractCommand {
	
	
	public CreateVehicle() {
		super("CREATEVEHICLE", "Create vehicle.");
	}
	
	
	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {
			Statement statement = connection.createStatement();
			StringBuilder sql = new StringBuilder();
			
			sql.append("INSERT INTO vozilo VALUES (default,'");
			sql.append(arguments.get("registarskaOznaka"));
			sql.append("','");
			sql.append(arguments.get("slikaVozila"));
			sql.append("','");
			sql.append(arguments.get("opisVozila"));
			sql.append("',");
			sql.append(arguments.get("idKategorijeVozila"));
			sql.append(",");
			sql.append(arguments.get("idKorisnika"));
			sql.append(");");
			
			statement.execute(sql.toString());
			
			sql = new StringBuilder();
			sql.append("SELECT id FROM vozilo WHERE registarska_oznaka='");
			sql.append(arguments.get("registarskaOznaka"));
			sql.append("';");
			
			ResultSet id = statement.executeQuery(sql.toString());
			id.next();
			returnObject.put("idVozila", id.getString("id"));
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
