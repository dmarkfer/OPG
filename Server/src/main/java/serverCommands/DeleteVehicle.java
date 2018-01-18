package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class DeleteVehicle extends AbstractCommand {
	
	public DeleteVehicle() {
		super("DELETEVEHICLE", "Delete vehicle.");
	}
	
	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM vozilo WHERE id=");
			sql.append(arguments.get("idVozila"));
			sql.append(";");
			
			statement.execute(sql.toString());
			
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
