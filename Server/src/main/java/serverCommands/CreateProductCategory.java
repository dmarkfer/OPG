package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateProductCategory extends AbstractCommand {
	
	String[] columns = new String[] {"naziv"};

	public CreateProductCategory() {
		super("CREATEPRODUCTCATEGORY", "Command creates a new product category. This command is used by admin.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO kategorija_oglasa VALUES (default,");
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
