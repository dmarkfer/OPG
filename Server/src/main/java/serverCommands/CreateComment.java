package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateComment extends AbstractCommand {
	
	String[] columns = new String[] {"idOcjenjivaca", "idOcjenjenog", "ocjena", "komentar", "vrijeme"};

	public CreateComment() {
		super("CREATECOMMENT", "Creates a new comment.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ocjena VALUES (default,");
			for(int i = 0; i < columns.length; ++i) {
				sql.append("'" + arguments.get(columns[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			
			sql = new StringBuilder();
			sql.append("SELECT id FROM ocjena WHERE vrijeme='");
			sql.append(arguments.get("vrijeme"));
			sql.append("';");
			
			ResultSet id = statement.executeQuery(sql.toString());
			id.next();
			returnObject.put("idOcjene", id.getString("id"));
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			environment.sendText("false");
		}
		
		return CommandStatus.CONTINUE;	
	}

}
