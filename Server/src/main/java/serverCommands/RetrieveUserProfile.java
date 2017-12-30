package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RetrieveUserProfile extends AbstractCommand {
	
	String[] columns = new String[] {"ime","prezime","lozinka","email","telefon","uloga",
			"blokiran","poljoprivrednik","kupac","prijevoznik","potvrden"};

	public RetrieveUserProfile() {
		super("RETRIEVEUSERPROFILE", "Retrieves user profile info.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM korisnik WHERE id=");
			sql.append(arguments.get("id"));
			sql.append(";");
			
			ResultSet userProfile = statement.executeQuery(sql.toString());
			userProfile.next();
			
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < columns.length; ++i) {
				result.append(columns[i] + "=" + userProfile.getString(columns[i]) + "##");
			}
			result.delete(result.length()-2, result.length());
			environment.sendText(result.toString());
		} catch (SQLException e) {
			environment.sendText("false");
		}
		return CommandStatus.CONTINUE;
	}

}
