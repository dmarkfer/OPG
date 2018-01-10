package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class EditProduct extends AbstractCommand {

	public EditProduct() {
		super("EDITPRODUCT", "Edits a product.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE oglas SET id_kategorije_oglasa='");
			sql.append(arguments.get("idKategorijeOglasa"));
			sql.append("', naziv_oglasa='");
			sql.append(arguments.get("nazivOglasa"));
			sql.append("', slika_oglasa='");
			sql.append(arguments.get("slikaOglasa"));
			sql.append("', opis_oglasa='");
			sql.append(arguments.get("opisOglasa"));
			sql.append("', cijena='");
			sql.append(arguments.get("cijena"));
			sql.append("', vrijeme='");
			sql.append(arguments.get("vrijeme"));
			sql.append("' WHERE id=");
			sql.append(arguments.get("idOglasa"));
			sql.append(";");
			
			statement.executeUpdate(sql.toString());
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}
	

}
