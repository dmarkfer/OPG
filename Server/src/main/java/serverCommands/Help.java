package serverCommands;

import java.util.HashMap;

import org.json.JSONObject;

import serverShell.AbstractCommand;
import serverShell.CommandStatus;
import serverShell.Environment;
import serverShell.ShellCommand;

public class Help extends AbstractCommand{
	
	public Help() {
		super("HELP", "Prints all commands");
	}
	@Override
	public CommandStatus execute(Environment environment, JSONObject arguments) {
		HashMap<String, ShellCommand> commands=environment.getCommands();
		
		if (arguments.length()!=1) {
			String wantedCommand=arguments.getString("wantedCommand").toUpperCase();
			String output=String.format("%-20s\t\t%s\n", wantedCommand, commands.get(wantedCommand).getCommandDescription());
			environment.sendText(output);
			return CommandStatus.CONTINUE;
		}
		
		StringBuffer output=new StringBuffer();
		for (String c : commands.keySet()) {
			ShellCommand command=commands.get(c);
			output.append(String.format("%-20s\t\t%s\n", command.getCommandName(),command.getCommandDescription()));
		}
		
		environment.sendText(output.toString());
		return CommandStatus.CONTINUE;
	}

}
