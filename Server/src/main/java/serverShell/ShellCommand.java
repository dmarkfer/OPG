package serverShell;

public interface ShellCommand {
	public String getCommandName();
	public String getCommandDescription();
	public CommandStatus execute(Environment environment);
}
