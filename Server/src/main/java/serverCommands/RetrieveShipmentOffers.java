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

public class RetrieveShipmentOffers extends AbstractCommand {
	
	String[] columns = new String[] {"idOglasaPrijevoza", "idOglasa", "idKupca", "polaziste", "odrediste", "vrijeme"};
	String[] columns2 = new String[] {"id", "id_oglasa", "id_kupca", "polaziste", "odrediste", "vrijeme"}; 

	public RetrieveShipmentOffers() {
		super("RETRIEVESHIPMENTOFFERS", "Retrieves shipment offers.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM oglas_prijevoz WHERE polaziste LIKE '%");
			sql.append(arguments.get("nazivMjesta"));
			sql.append("%' OR odrediste LIKE '%");
			sql.append(arguments.get("nazivMjesta"));
			sql.append("%';");
			
			JSONArray offers = new JSONArray();
			ResultSet oglasi = statement.executeQuery(sql.toString());
			while(oglasi.next()) {
				JSONObject offer = new JSONObject();
				for(int i = 0; i < columns.length; ++i) {
					offer.put(columns[i], oglasi.getString(columns2[i]));
				}
				offers.put(offer);
			}
			
			returnObject.put("oglasi", offers);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}

}
