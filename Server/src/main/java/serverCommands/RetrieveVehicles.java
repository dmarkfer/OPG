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

public class RetrieveVehicles extends AbstractCommand {
	
	String[] dbColumns = new String[] {
			"id",
			"registarska_oznaka",
			"slika_vozila",
			"opis_vozila",
			"id_kategorije_vozila"
	};
	
	String[] dtoColumns = new String[] {
			"idVozila",
			"registarskaOznaka",
			"slikaVozila",
			"opisVozila",
			"idKategorijeVozila"
	};
	

	public RetrieveVehicles() {
		super("RETRIEVEVEHICLES", "Retrieves vehicles.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		
		try {
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT * FROM vozilo WHERE id_korisnika=");
			sql.append(arguments.get("idKorisnika"));
			sql.append(";");
			
			ResultSet vehicles = statement.executeQuery(sql.toString());
			
			JSONArray vehs = new JSONArray();
			while(vehicles.next()) {
				JSONObject veh = new JSONObject();
				for(int i=0; i<dbColumns.length; ++i) {
					veh.put(dtoColumns[i], vehicles.getString(dbColumns[i]));
				}
				vehs.put(veh);
			}
			
			returnObject.put("vozila", vehs);
			returnObject.put("success", true);
			
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		
		return CommandStatus.CONTINUE;
	}
}
