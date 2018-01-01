package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class DeleteUser extends AbstractCommand {

	public DeleteUser() {
		super("DELETEUSER", "Deletes specified user.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM korisnik WHERE id=");
			sql.append(arguments.get("id"));
			sql.append(";");
			
			statement.executeUpdate(sql.toString());
			environment.sendText("true");
		} catch (SQLException e) {
			environment.sendText("false");
		}
		return CommandStatus.CONTINUE;
	}

}
