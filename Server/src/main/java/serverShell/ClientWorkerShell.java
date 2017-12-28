package serverShell;

import java.util.HashMap;

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
			String inputLine=environment.getText().toUpperCase().trim();
			String inputCommand=getCommand(inputLine);
			HashMap<String, String> arguments=Parser.parse(inputLine);
			
			if (inputLine==null||inputLine.equals("NULL")) {
				environment.close();
				return;
			}
			for (String string : arguments.keySet()) {
				System.out.println(string + " " + arguments.get(string));
			}
			command=environment.getCommand(inputCommand);
			
			if (command==null) {
				environment.sendText("Unsupported command.");
				continue;
			}
			
			if (!command.execute(environment,arguments).toString().equalsIgnoreCase("Continue")) {
				break;
			}
		}
	}


	private String getCommand(String inputLine) {
		if (inputLine.contains("#")) {
			return inputLine.substring(0, inputLine.indexOf("#"));
		}
		return inputLine;
	}
}
