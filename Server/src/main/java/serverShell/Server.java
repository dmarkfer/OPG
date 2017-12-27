package serverShell;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import database.DatabaseConnection;
import serverCommands.Help;
import serverCommands.Terminate;

public class Server {
private static final int PORT=8080;
private static HashMap<String, ShellCommand> commands;


	static {
		commands=new HashMap<>();
		ShellCommand[] cc= {
						new Help(),
						new Terminate()
		};
		
		for (ShellCommand shellCommand : cc) {
			commands.put(shellCommand.getCommandName(), shellCommand);
		}	
	}
	
	public static void main(String[] args) throws IOException {
		int timeout=0;
		ServerSocket socket=new ServerSocket(PORT);
		socket.setSoTimeout(1000);
		
		while(true) {
			System.out.println("Waiting");
			ClientWorkerShell worker=null;
			
			try {
				EnvironmentImpl impl=new EnvironmentImpl(socket.accept(),commands);
				worker= new ClientWorkerShell(impl);
				System.out.println("conenction established");
				Thread thread= new Thread(worker);
				thread.start();
				timeout=0;
			}
			
			catch (Exception e) {
				timeout++;
				System.out.println("No Connection");
				if (timeout==1000) {
					socket.close();
					System.exit(0);
				}
			}
		}
	}
}
