package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateProduct extends AbstractCommand {
	
	String[] columns = new String[] {"id_poljoprivrednika","id_kategorije_oglasa",
			"naziv_oglasa","slika_oglasa","opis_oglasa","cijena","vrijeme"};

	public CreateProduct() {
		super("CREATEPRODUCT", "Creates a new advertisement for a product.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments)  {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO oglas VALUES (default,");
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
