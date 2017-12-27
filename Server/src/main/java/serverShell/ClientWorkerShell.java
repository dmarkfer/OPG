package serverShell;

public class ClientWorkerShell implements Runnable {
	private EnvironmentImpl environment;
	private static final String BRAKECHARACTER="$$";
	public ClientWorkerShell(EnvironmentImpl environment) {
		this.environment=environment;
	}
	

	@Override
	public void run() {
		environment.sendText("Hi. Write help for list of commands.");
		ShellCommand command;
		String arguments[]; // funkcija za parsanje argumenta -> najbolje napraviti da ima neki brake carracter, poslani string 
		
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
			
			if (!command.execute(environment,"").toString().equalsIgnoreCase("Continue")) {
				break;
			}
		}
	}
}
