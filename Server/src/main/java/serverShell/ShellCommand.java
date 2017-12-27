package serverShell;

import java.util.HashMap;

public interface ShellCommand {
	public String getCommandName();
	public String getCommandDescription();
	public CommandStatus execute(Environment environment, HashMap<String, String> arguments);
}
