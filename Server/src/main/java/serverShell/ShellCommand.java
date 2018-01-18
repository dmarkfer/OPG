package serverShell;



import org.json.JSONObject;

public interface ShellCommand {
	public String getCommandName();
	public String getCommandDescription();
	public CommandStatus execute(Environment environment, JSONObject arguments);
}
