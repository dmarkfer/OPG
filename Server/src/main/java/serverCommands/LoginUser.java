package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class LoginUser extends AbstractCommand {
	
	String[] columns = new String[] {"id", "uloga", "kupac", "prijevoznik", "poljoprivrednik"};
	String[] columns2 = new String[] {"idKorisnika", "uloga", "kupac", "prijevoznik", "poljoprivrednik"};

	public LoginUser() {
		super("LOGINUSER", "Command makes a user login.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM korisnik WHERE email='");
			sql.append(arguments.get("email"));
			sql.append("';");
			
			ResultSet user = statement.executeQuery(sql.toString());
			user.next();
			String ispravnaLozinka = user.getString("lozinka");
			
			if(ispravnaLozinka.equals(arguments.get("lozinka"))) {
				for(int i = 0; i < columns.length; ++i) {
					returnObject.put(columns2[i], user.getString(columns[i]));
				}
				returnObject.put("success", true);
				environment.sendText(returnObject.toString());
			} else {
				returnObject.put("success", false);
				environment.sendText(returnObject.toString());
			}
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}

}
