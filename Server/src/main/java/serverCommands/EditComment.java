package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class EditComment extends AbstractCommand {

	public EditComment() {
		super("EDITCOMMENT", "Comment editing.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ocjena SET komentar='");
			sql.append(arguments.get("komentar"));
			sql.append("', ocjena='");
			sql.append(arguments.get("ocjena"));
			sql.append("', vrijeme='");
			sql.append(arguments.get("vrijeme"));
			sql.append("' WHERE id=");
			sql.append(arguments.get("idOcjene"));
			sql.append(";");
			
			statement.executeUpdate(sql.toString());
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}

}
