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
	
	String[] columns5 = new String[] {"drzava", "grad", "ulica", "postanskiBroj", "idMjesta", "latitude", "longitude", "brojUlaza"};
	String[] columns6 = new String[] {"drzava", "grad", "ulica", "postanski_broj", "place_id", "latitude", "longitude", "broj_ulaza"};
	
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
				sql.append("SELECT id FROM adresa WHERE grad LIKE '%");
				sql.append(arguments.get("nazivPolazista"));
				sql.append("%';");
				
				
				StringBuilder idAdresa = new StringBuilder();
				ResultSet adrese = statement.executeQuery(sql.toString());
				while(adrese.next()) {
					idAdresa.append(adrese.getString("id") + ",");
				}
				if(idAdresa.length() > 0) { 
					idAdresa.deleteCharAt(idAdresa.length()-1);
					
					sql = new StringBuilder();
					sql.append("SELECT * FROM oglas_prijevoz WHERE polaziste IN (");
					sql.append(idAdresa);
					sql.append(");");				
					
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
						
						sql = new StringBuilder();
						sql.append("SELECT * FROM adresa WHERE id=");
						sql.append(offer.get("polaziste"));
						sql.append(";");
						
						ResultSet polaziste = statement2.executeQuery(sql.toString());
						polaziste.next();
						
						JSONObject polazisteJSON = new JSONObject();
						for(int i = 0; i < columns5.length; ++i) {
							polazisteJSON.put(columns5[i], polaziste.getString(columns6[i]));
						}
						
						offer.put("polaziste", polazisteJSON);
						
						sql = new StringBuilder();
						sql.append("SELECT * FROM adresa WHERE id=");
						sql.append(offer.get("odrediste"));
						sql.append(";");
						
						ResultSet odrediste = statement2.executeQuery(sql.toString());
						odrediste.next();
						
						JSONObject odredisteJSON = new JSONObject();
						for(int i = 0; i < columns5.length; ++i) {
							odredisteJSON.put(columns5[i], odrediste.getString(columns6[i]));
						}
						
						offer.put("odrediste", odredisteJSON);	
						offers.put(offer);
					}
				}
			}
			if(arguments.has("nazivOdredista")) {	
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT id FROM adresa WHERE grad LIKE '%");
				sql.append(arguments.get("nazivOdredista"));
				sql.append("%';");
				
				
				StringBuilder idAdresa = new StringBuilder();
				ResultSet adrese = statement.executeQuery(sql.toString());
				while(adrese.next()) {
					idAdresa.append(adrese.getString("id") + ",");
				}
				if(idAdresa.length() > 0) { 
					idAdresa.deleteCharAt(idAdresa.length()-1);
					
					sql = new StringBuilder();
					sql.append("SELECT * FROM oglas_prijevoz WHERE odrediste IN (");
					sql.append(idAdresa);
					sql.append(");");	
					
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
						
						sql = new StringBuilder();
						sql.append("SELECT * FROM adresa WHERE id=");
						sql.append(offer.get("polaziste"));
						sql.append(";");
						
						ResultSet polaziste = statement2.executeQuery(sql.toString());
						polaziste.next();
						
						JSONObject polazisteJSON = new JSONObject();
						for(int i = 0; i < columns5.length; ++i) {
							polazisteJSON.put(columns5[i], polaziste.getString(columns6[i]));
						}
						
						offer.put("polaziste", polazisteJSON);
						
						sql = new StringBuilder();
						sql.append("SELECT * FROM adresa WHERE id=");
						sql.append(offer.get("odrediste"));
						sql.append(";");
						
						ResultSet odrediste = statement2.executeQuery(sql.toString());
						odrediste.next();
						
						JSONObject odredisteJSON = new JSONObject();
						for(int i = 0; i < columns5.length; ++i) {
							odredisteJSON.put(columns5[i], odrediste.getString(columns6[i]));
						}
						
						offer.put("odrediste", odredisteJSON);	
						offers.put(offer);
					}
				}				
			}		
			
			returnObject.put("oglasi", offers);	
			returnObject.put("success", true);
			environment.sendText(returnObject.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObject.put("success", false);
			environment.sendText(returnObject.toString());
		}
		return CommandStatus.CONTINUE;
	}

}
