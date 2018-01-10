package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class DeleteProductCategory extends AbstractCommand {

	public DeleteProductCategory() {
		super("DELETEPRODUCTCATEGORY", "Deletes an existing product category.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM kategorija_oglasa WHERE id=");
			sql.append(arguments.get("idKategorije"));
			sql.append(";");
			
			statement.execute(sql.toString());
			
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}

}
