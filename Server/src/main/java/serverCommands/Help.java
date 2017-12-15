package serverCommands;

import java.util.HashMap;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;
import serverShell.ShellCommand;

public class Help extends AbstractCommand{
	
	public Help() {
		super("HELP", "Prints all commands");
	}
	@Override
	public CommandStatus execute(Environment environment) {
		HashMap<String, ShellCommand> commands=environment.getCommands();
		StringBuffer output=new StringBuffer();
		
		for (String c : commands.keySet()) {
			ShellCommand command=commands.get(c);
			output.append(String.format("%-10s\t\t%s\n", command.getCommandName(),command.getCommandDescription()));
		}
		
		environment.sendText(output.toString());
		return CommandStatus.CONTINUE;
	}

}
