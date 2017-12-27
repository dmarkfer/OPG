package serverCommands;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;

public class Terminate extends AbstractCommand {
	
	public Terminate() {
		super("TERMINATE", "Command terminates a connection between client and server");
	}

	@Override
	public CommandStatus execute(Environment environment, String arguments) {
		environment.close();
		return CommandStatus.EXIT;
	}
}
