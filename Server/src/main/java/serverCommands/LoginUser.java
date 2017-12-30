package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class LoginUser extends AbstractCommand {

	public LoginUser() {
		super("LOGINUSER", "Command makes a user login.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT lozinka FROM korisnik WHERE id=");
			sql.append(arguments.get("id"));
			sql.append(";");
			
			ResultSet lozinka = statement.executeQuery(sql.toString());
			lozinka.next();
			String ispravnaLozinka = lozinka.getString(1);
			
			if(ispravnaLozinka.equals(arguments.get("lozinka"))) {
				environment.sendText("true");
			} else {
				environment.sendText("false");
			}
		} catch (SQLException e) {
			environment.sendText("false");
		}
		return CommandStatus.CONTINUE;
	}

}
