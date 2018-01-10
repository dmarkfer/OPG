package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateProduct extends AbstractCommand {
	
	String[] columns = new String[] {"idKorisnika", "idKategorijeOglasa", "nazivOglasa", "slikaOglasa", 
			"opisOglasa", "cijena", "vrijeme"};

	public CreateProduct() {
		super("CREATEPRODUCT", "Creates a new advertisement for a product.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments)  {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
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
			
			ResultSet id = statement.executeQuery("SELECT MAX(id) AS id FROM oglas;");
			id.next();
			returnObject.put("idOglasa", id.getString("id"));
			environment.sendText(returnObject.toString());			
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}

}
