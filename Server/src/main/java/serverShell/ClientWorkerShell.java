package serverShell;


import org.json.JSONObject;

public class ClientWorkerShell implements Runnable {
	private EnvironmentImpl environment;
	
	public ClientWorkerShell(EnvironmentImpl environment) {
		this.environment=environment;
	}
	

	@Override
	public void run() {
		ShellCommand command;
		
		while(true) {
			String inputLine=environment.getText().trim();
			
			if (inputLine.equals("null")||inputLine.equalsIgnoreCase("fail")) {
				environment.close();
				return;
			}
			
			JSONObject input=new JSONObject(inputLine);
			String inputCommand=input.getString("command").toUpperCase();
			command=environment.getCommand(inputCommand);
			
			if (command==null) {
				environment.sendText("Unsupported command.");
				continue;
			}
			
			if (!command.execute(environment,input).toString().equalsIgnoreCase("Continue")) {
				break;
			}
		}
	}
}
