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

public class RetrieveMessages extends AbstractCommand {
	
	String[] columns = new String[] {"idPoruka", "idRazgovora", "vrijeme", "poruka", "idPosiljatelja"};
	String[] columns2 = new String[] {"id", "id_razgovora", "vrijeme", "poruka", "id_posiljatelja"}; 

	public RetrieveMessages() {
		super("RETRIEVEMESSAGES", "Retrieves messages from conversation.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM poruka WHERE id_razgovora=");
			sql.append(arguments.get("idRazgovora"));
			sql.append(";");
			
			JSONArray messages = new JSONArray();
			ResultSet poruke = statement.executeQuery(sql.toString());
			while(poruke.next()) {
				JSONObject message = new JSONObject();
				for(int i = 0; i < columns.length; ++i) {
					message.put(columns[i], poruke.getString(columns2[i]));
				}
				messages.put(message);
			}
			
			returnObject.put("poruke", messages);
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
