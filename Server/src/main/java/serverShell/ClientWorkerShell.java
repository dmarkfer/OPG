package serverShell;

public class ClientWorkerShell implements Runnable {
	private EnvironmentImpl environment;
	public ClientWorkerShell(EnvironmentImpl environment) {
		this.environment=environment;
	}
	

	@Override
	public void run() {
		environment.sendText("Hi. Write help for list of commands.");
		ShellCommand command;
		while(true) {
			String inputCommand=environment.getText().toUpperCase().trim();
			if (inputCommand==null) {
				environment.close();
				return;
			}
			command=environment.getCommand(inputCommand);
			
			if (command==null) {
				environment.sendText("Unsupported command.");
				continue;
			}
			
			if (!command.execute(environment).toString().equalsIgnoreCase("Continue")) {
				break;
			}
		}
	}
}
