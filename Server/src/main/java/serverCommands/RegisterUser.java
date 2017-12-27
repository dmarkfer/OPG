package serverCommands;

import java.sql.Connection;
import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class RegisterUser extends AbstractCommand {

	public RegisterUser() {
		super("REGISTER USER", "Command registers new user.");
	}

	@Override
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments) {
		Connection connection = environment.getDatabase();		
		
		return CommandStatus.CONTINUE;
	}
}
