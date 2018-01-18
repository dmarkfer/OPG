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

public class GetAdminTasks extends AbstractCommand {
	
	String[] columns = new String[] {"idKategorije", "idKorisnika", "nazivKategorije", "komentar"};
	String[] columns2 = new String[] {"id", "id_korisnika", "naziv_kategorije", "komentar"};
	
	String[] columns3 = new String[] {"idKorisnika", "ime", "prezime"};
	String[] columns4 = new String[] {"id", "ime", "prezime"}; 
	
	String[] columns5 = new String[] {"idPrijave", "idKorisnika", "tipPrijave", "idPrijavljeneStavke", "idVrstePrijave", "komentar"};
	String[] columns6 = new String[] {"id", "id_korisnika", "tip_prijave", "id_prijavljene_stavke", "id_vrste_prijave", "komentar"}; 

	public GetAdminTasks() {
		super("GETADMINTASKS", "Retrieves admin tasks.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM nova_kategorija_oglasa;");
			
			JSONArray categories = new JSONArray();
			ResultSet kategorije = statement.executeQuery(sql.toString());
			while(kategorije.next()) {
				JSONObject category = new JSONObject();
				for(int i = 0; i < columns.length; ++i) {
					category.put(columns[i], kategorije.getString(columns2[i]));
				}
				categories.put(category);
			}
			
			returnObject.put("noveKategorije", categories);
			
			sql = new StringBuilder();
			sql.append("SELECT id,ime,prezime FROM korisnik WHERE potvrden = B'0';");
			
			JSONArray users = new JSONArray();
			ResultSet korisnici = statement.executeQuery(sql.toString());
			while(korisnici.next()) {
				JSONObject user = new JSONObject();
				for(int i = 0; i < columns3.length; ++i) {
					user.put(columns3[i], korisnici.getString(columns4[i]));
				}
				users.put(user);
			}
			
			returnObject.put("noviKorisnici", users);	
			
			sql = new StringBuilder();
			sql.append("SELECT * FROM prijava;");
			
			JSONArray reports = new JSONArray();
			ResultSet prijave = statement.executeQuery(sql.toString());
			while(prijave.next()) {
				JSONObject report = new JSONObject();
				for(int i = 0; i < columns5.length; ++i) {
					report.put(columns5[i], prijave.getString(columns6[i]));
				}
				reports.put(report);
			}
			
			returnObject.put("prijave", reports);	
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
