package serverShell;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import serverCommands.CreateComment;
import serverCommands.CreateConversation;
import serverCommands.CreateFarmer;
import serverCommands.CreateProduct;
import serverCommands.CreateProductCategory;
import serverCommands.CreateReport;
import serverCommands.DeleteComment;
import serverCommands.DeleteUser;
import serverCommands.EditComment;
import serverCommands.Help;
import serverCommands.LoginUser;
import serverCommands.RegisterUser;
import serverCommands.RetrieveMessages;
import serverCommands.RetrieveUserProfile;
import serverCommands.SendMessage;
import serverCommands.Terminate;

public class Server {
private static final int PORT=8080;
private static HashMap<String, ShellCommand> commands;


	static {
		commands=new HashMap<>();
		ShellCommand[] cc= {
						new Help(),
						new Terminate(),
						new RegisterUser(),
						new LoginUser(),
						new DeleteUser(),
						new RetrieveUserProfile(),
						new CreateComment(),
						new CreateConversation(),
						new CreateProduct(),
						new CreateFarmer(),
						new CreateProductCategory(),
						new EditComment(),
						new DeleteComment(),
						new SendMessage(),
						new RetrieveMessages(),
						new CreateReport()
		};
		
		for (ShellCommand shellCommand : cc) {
			commands.put(shellCommand.getCommandName(), shellCommand);
		}	
	}
	
	public static void main(String[] args) throws IOException {
		int timeout=0;
		ServerSocket socket=new ServerSocket(PORT);
		socket.setSoTimeout(1000);
		BlockingQueue<Runnable> workingThreadQueue = new ArrayBlockingQueue<Runnable>(20);
		ExecutorService threadPool =new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, workingThreadQueue);
		
		while(true) {
			//System.out.println("Waiting");
			ClientWorkerShell worker=null;
			
			try {
				EnvironmentImpl impl=new EnvironmentImpl(socket.accept(),commands);
				worker= new ClientWorkerShell(impl);
				System.out.println("conenction established");
				threadPool.execute(worker);
				timeout=0;
			}
			
			catch (Exception e) {
				timeout++;
				//System.out.println("No Connection");
				if (timeout==10000) {
					threadPool.shutdown();
					while (threadPool.isShutdown()) {}
					socket.close();
					return;
				}
			}
		}
	}
}
