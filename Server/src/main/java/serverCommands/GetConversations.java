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

public class GetConversations extends AbstractCommand {
	
	String[] columns = new String[] {"idRazgovora", "idOglasa", "idOglasaPrijevoza", "sudionik", "gotovRazgovor", "kolicina", "cijena"};
	String[] columns2 = new String[] {"id", "id_oglasa", "id_oglasa_prijevoz", "id_prijevoznika", "gotov_razgovor", "kolicina", "cijena"};
	
	String[] columns3 = new String[] {"idPoruka", "idRazgovora", "vrijeme", "poruka", "idPosiljatelja"};
	String[] columns4 = new String[] {"id", "id_razgovora", "vrijeme", "poruka", "id_posiljatelja"};

	public GetConversations() {
		super("GETCONVERSATIONS", "Retrieves user conversations.");
	}

	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		Connection connection = environment.getDatabase();
		JSONObject returnObject = new JSONObject();
		try {						
			Statement statement = connection.createStatement();
			Statement statement2 = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM razgovor WHERE id_prijevoznika=");
			sql.append(arguments.get("idKorisnika"));
			sql.append(" OR id_kupca=");
			sql.append(arguments.get("idKorisnika"));
			sql.append(";");
			
			JSONArray conversations = new JSONArray();
			ResultSet razgovori = statement.executeQuery(sql.toString());
			while(razgovori.next()) {
				JSONObject conversation = new JSONObject();
				for(int i = 0; i < columns.length; ++i) {
					conversation.put(columns[i], razgovori.getString(columns2[i]));
				}
				if (conversation.get("sudionik").equals(arguments.get("idKorisnika").toString())) {
					conversation.put("sudionik", razgovori.getString("id_kupca"));
				}
				
				sql = new StringBuilder();
				sql.append("SELECT * FROM poruka WHERE id_razgovora=");
				sql.append(conversation.get("idRazgovora"));
				sql.append(" ORDER BY vrijeme DESC LIMIT 1");
				
				JSONObject message = new JSONObject();
				ResultSet poruka = statement2.executeQuery(sql.toString());
				if(poruka.next()) {
					for(int i = 0; i < columns3.length; ++i) {
						message.put(columns3[i], poruka.getString(columns4[i]));
					}
					conversation.put("zadnjaPoruka", message);
				}
				
				conversations.put(conversation);
			}
			
			returnObject.put("razgovori", conversations);
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
