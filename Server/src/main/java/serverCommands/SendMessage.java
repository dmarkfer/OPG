package serverCommands;
 
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
 
import org.json.JSONObject;
 
import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;
 
public class SendMessage extends AbstractCommand {

	String[] columns = new String[] {"idRazgovora", "vrijeme", "poruka", "idPosiljatelja"};

	public SendMessage() {
		super("SENDMESSAGE", "Sends a message");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {            
			Statement statement = connection.createStatement();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO poruka VALUES (default,");
			for(int i = 0; i < columns.length; ++i) {
				sql.append("'" + arguments.get(columns[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");

			statement.executeUpdate(sql.toString());

			returnObject.put("sucess", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("sucess", false);
			environment.sendText(returnObject.toString());
		}

		return CommandStatus.CONTINUE;  
	}

}
