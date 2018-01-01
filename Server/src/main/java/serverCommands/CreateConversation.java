package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateConversation extends AbstractCommand {
	
	String[] columns = new String[] {"id_oglasa","id_oglasa_prijevoz","id_prijevoznika","gotov_razgovor","kolicina","cijena"};

	public CreateConversation() {
		super("CREATECONVERSATION", "Creates a conversation.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO razgovor VALUES (default,");
			for(int i = 0; i < columns.length; ++i) {
				if(arguments.get(columns[i]) == null) {
					sql.append("null,");
				} else {
					sql.append("'" + arguments.get(columns[i]) + "',");
				}
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			System.out.println(sql.toString());
			statement.executeUpdate(sql.toString());
			environment.sendText("true");
		} catch (SQLException e) {
			e.printStackTrace();
			environment.sendText("false");
		}
		
		return CommandStatus.CONTINUE;	
	}

}
