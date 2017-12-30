package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateFarmer extends AbstractCommand {
	
	String[] columns = new String[] {"id_korisnika","naziv_opg","oib_opg",
			"adresa_opg","slika_opg","opis_opg","iban"};

	public CreateFarmer() {
		super("CREATEFARMER", "Creates a farmer from existing or new user.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO poljoprivrednik VALUES (");
			for(int i = 0; i < columns.length; ++i) {
				sql.append("'" + arguments.get(columns[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			statement.executeUpdate(sql.toString());
			environment.sendText("true");
		} catch (SQLException e) {
			environment.sendText("false");
		}
		
		return CommandStatus.CONTINUE;
	}

}
