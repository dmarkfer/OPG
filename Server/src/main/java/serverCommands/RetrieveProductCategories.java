package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RetrieveProductCategories extends AbstractCommand {
	
	String[] columns = new String[] {"idKategorije", "naziv"};
	String[] columns2 = new String[] {"id", "naziv"}; 

	public RetrieveProductCategories() {
		super("RETRIEVEPRODUCTCATEGORIES", "Retrieves product categories.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM kategorija_oglasa;");
			
			JSONArray categories = new JSONArray();
			ResultSet kategorije = statement.executeQuery(sql.toString());
			while(kategorije.next()) {
				JSONObject category = new JSONObject();
				for(int i = 0; i < columns.length; ++i) {
					category.put(columns[i], kategorije.getString(columns2[i]));
				}
				categories.put(category);
			}
			
			returnObject.put("kategorije", categories);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}
	

}
