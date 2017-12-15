package serverShell;

public abstract class AbstractCommand implements ShellCommand{
	private final String commandName;
	private final String commandDescription;
	
	public AbstractCommand(String commandName, String commandDescription) {
		this.commandName = commandName;
		this.commandDescription = commandDescription;
	}

	public String getCommandName() {
		return commandName;
	}

	public String getCommandDescription() {
		return commandDescription;
	}
}
