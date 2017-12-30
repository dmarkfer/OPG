package serverCommands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class CreateComment extends AbstractCommand {
	
	String[] columns = new String[] {"id_razgovora","id_ocjenjenoga","ocjena","komentar","vrijeme"};

	public CreateComment() {
		super("CREATECOMMENT", "Creates a new comment.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		Connection connection = environment.getDatabase();
		try {						
			Statement statement = connection.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ocjena VALUES (default,");
			for(int i = 0; i < columns.length; ++i) {
				sql.append("'" + arguments.get(columns[i]) + "',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(");");
			
			System.out.println(sql.toString());
			statement.executeUpdate(sql.toString());
			environment.sendText("true");
		} catch (SQLException e) {
			e.printStackTrace();
			environment.sendText("false");
		}
		
		return CommandStatus.CONTINUE;	
	}

}
