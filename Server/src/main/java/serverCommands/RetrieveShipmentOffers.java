package serverCommands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RetrieveShipmentOffers extends AbstractCommand {
	
	String[] columns = new String[] {"idOglasaPrijevoza", "idOglasa", "idKupca", "polaziste", "odrediste", "vrijeme"};
	String[] columns2 = new String[] {"id", "id_oglasa", "id_kupca", "polaziste", "odrediste", "vrijeme"}; 
	
	String[] columns3 = new String[] {"idOglasa", "nazivOglasa", "slikaOglasa", "cijena", "vrijeme", "idKategorije", "opisOglasa", "idPoljoprivrednika"};
	String[] columns4 = new String[] {"id", "naziv_oglasa", "slika_oglasa", "cijena", "vrijeme", "id_kategorije_oglasa", "opis_oglasa", "id_poljoprivrednika"};
	
	Set<Integer> idOglasaPrijevoza = new TreeSet<>();

	public RetrieveShipmentOffers() {
		super("RETRIEVESHIPMENTOFFERS", "Retrieves shipment offers.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			Statement statement2 = connection.createStatement();
			
			JSONArray offers = new JSONArray();
			if(arguments.has("nazivPolazista")) {			
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM oglas_prijevoz WHERE polaziste LIKE '%");
				sql.append(arguments.get("nazivPolazista"));
				sql.append("%';");
				
				
				ResultSet oglasi = statement.executeQuery(sql.toString());
				while(oglasi.next()) {
					JSONObject offer = new JSONObject();
					for(int i = 0; i < columns.length; ++i) {
						offer.put(columns[i], oglasi.getString(columns2[i]));
					}
					
					idOglasaPrijevoza.add(oglasi.getInt("id"));
					
					sql = new StringBuilder();
					sql.append("SELECT * FROM oglas WHERE id=");
					sql.append(offer.get("idOglasa"));
					sql.append(";");
					
					ResultSet oglasP = statement2.executeQuery(sql.toString());
					oglasP.next();
					
					JSONObject offerP = new JSONObject();
					for(int i = 0; i < columns3.length; ++i) {
						offerP.put(columns3[i], oglasP.getString(columns4[i]));
					}
					
					offer.put("oglasProizvoda", offerP);
					offers.put(offer);
				}
			}
			if(arguments.has("nazivOdredista")) {			
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM oglas_prijevoz WHERE odrediste LIKE '%");
				sql.append(arguments.get("nazivOdredista"));
				sql.append("%';");
				
				ResultSet oglasi = statement.executeQuery(sql.toString());
				while(oglasi.next()) {
					if(idOglasaPrijevoza.contains(oglasi.getInt("id"))) continue;
					JSONObject offer = new JSONObject();
					for(int i = 0; i < columns.length; ++i) {
						offer.put(columns[i], oglasi.getString(columns2[i]));
					}
					
					sql = new StringBuilder();
					sql.append("SELECT * FROM oglas WHERE id=");
					sql.append(offer.get("idOglasa"));
					sql.append(";");
					
					ResultSet oglasP = statement2.executeQuery(sql.toString());
					oglasP.next();
					
					JSONObject offerP = new JSONObject();
					for(int i = 0; i < columns3.length; ++i) {
						offerP.put(columns3[i], oglasP.getString(columns4[i]));
					}
					
					offer.put("oglasProizvoda", offerP);					
					offers.put(offer);
				}				
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
