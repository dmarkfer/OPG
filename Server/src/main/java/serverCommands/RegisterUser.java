package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RegisterUser extends AbstractCommand {

	public RegisterUser() {
		super("REGISTERUSER", "Command registers new user.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		
		arguments = new HashMap<>();
		arguments.put("ime", "Jovan1");
		arguments.put("prezime", "Jovan2");
		arguments.put("lozinka", "Jovan3");
		arguments.put("email", "Jovan4");
		arguments.put("telefon", "Jovan5");
		arguments.put("uloga", "1");
		arguments.put("blokiran", "1");
		arguments.put("poljoprivrednik", "1");
		arguments.put("kupac", "1");
		arguments.put("prijevoznik", "1");
		arguments.put("potvrden", "1");
		
		
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO korisnik VALUES (default,");
			sql.append("'" + arguments.get("ime") + "',");
			sql.append("'" + arguments.get("prezime") + "',");
			sql.append("'" + arguments.get("lozinka") + "',");
			sql.append("'" + arguments.get("email") + "',");
			sql.append("'" + arguments.get("telefon") + "',");
			sql.append("'" + arguments.get("uloga") + "',");
			sql.append("'" + arguments.get("blokiran") + "',");
			sql.append("'" + arguments.get("poljoprivrednik") + "',");
			sql.append("'" + arguments.get("kupac") + "',");
			sql.append("'" + arguments.get("prijevoznik") + "',");
			sql.append("'" + arguments.get("potvrden") + "'");
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return CommandStatus.CONTINUE;
	}
}
