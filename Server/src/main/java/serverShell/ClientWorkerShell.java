package serverShell;

public class ClientWorkerShell implements Runnable {
	private EnvironmentImpl environment;
	private static final String EMPTYARGUMENT="";
	private static final String BRAKECHARACTER="$$";
	public ClientWorkerShell(EnvironmentImpl environment) {
		this.environment=environment;
	}
	

	@Override
	public void run() {
		environment.sendText("Hi. Write help for list of commands.");
		ShellCommand command;
		String arguments[];
		
		while(true) {
			String inputCommand=environment.getText().toUpperCase().trim();
			if (inputCommand==null) {
				environment.close();
				return;
			}
			arguments=parseInput(inputCommand);
			command=environment.getCommand(arguments[0]);
			
			if (command==null) {
				environment.sendText("Unsupported command.");
				continue;
			}
			
			if (!command.execute(environment,arguments[1]).toString().equalsIgnoreCase("Continue")) {
				break;
			}
		}
	}


	private String[] parseInput(String inputCommand) {
		String args[]=new String[2];
		
		if (inputCommand.contains(BRAKECHARACTER)) {
			args=inputCommand.split(BRAKECHARACTER);
			return args;
		}
		args[0]=inputCommand;
		args[1]=EMPTYARGUMENT;
		return args;
	}
}
