package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateConversation extends AbstractCommand {
	
	String[] columns = new String[] {"idOglasa", "idOglasaPrijevoza", "idPrijevoznikaIliPoljoprivrednika", 
			"gotovRazgovor", "kolicina", "cijena", "idKupca" };

	public CreateConversation() {
		super("CREATECONVERSATION", "Creates a conversation.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		arguments.put("gotovRazgovor", 0);
		arguments.put("kolicina", 0);
		arguments.put("cijena", 0);
		
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO razgovor VALUES (default,");
			
			for(int i = 0; i < columns.length; ++i) {
				if(!arguments.has(columns[i])) {
					sql.append("null,");
				} else {
					sql.append("'" + arguments.get(columns[i]) + "',");
				}
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
						
			ResultSet id = statement.executeQuery("SELECT MAX(id) AS id FROM razgovor;");
			id.next();
			returnObject.put("idRazgovora", id.getString("id"));
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			environment.sendText("false");
		}
		
		return CommandStatus.CONTINUE;	
	}

}
