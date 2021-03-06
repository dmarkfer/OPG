package serverShell;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import serverCommands.ConfirmProductCategory;
import serverCommands.CreateComment;
import serverCommands.CreateConversation;
import serverCommands.CreateProduct;
import serverCommands.CreateProductCategory;
import serverCommands.CreateReport;
import serverCommands.CreateShipmentOffer;
import serverCommands.CreateVehicle;
import serverCommands.DeleteComment;
import serverCommands.DeleteProduct;
import serverCommands.DeleteProductCategory;
import serverCommands.DeleteShipmentOffer;
import serverCommands.DeleteUser;
import serverCommands.DeleteVehicle;
import serverCommands.EditComment;
import serverCommands.EditProduct;
import serverCommands.GetAdminTasks;
import serverCommands.GetConversations;
import serverCommands.Help;
import serverCommands.LoginUser;
import serverCommands.RegisterUser;
import serverCommands.RetrieveMessages;
import serverCommands.RetrieveProductCategories;
import serverCommands.RetrieveProductOffers;
import serverCommands.RetrieveProductOffersByVendor;
import serverCommands.RetrieveShipmentOffers;
import serverCommands.RetrieveUserProfile;
import serverCommands.RetrieveVehicles;
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
						new CreateProductCategory(),
						new EditComment(),
						new DeleteComment(),
						new SendMessage(),
						new RetrieveMessages(),
						new CreateReport(), 
						new EditProduct(), 
						new DeleteProduct(), 
						new RetrieveProductOffers(), 
						new DeleteProductCategory(),
						new RetrieveProductCategories(),
						new ConfirmProductCategory(),
						new GetConversations(),
						new CreateShipmentOffer(),
						new DeleteShipmentOffer(),
						new CreateVehicle(), 
						new RetrieveVehicles(),
						new DeleteVehicle(), 
						new RetrieveShipmentOffers(),
						new GetAdminTasks(),
						new RetrieveProductOffersByVendor()
		};
		
		for (ShellCommand shellCommand : cc) {
			commands.put(shellCommand.getCommandName(), shellCommand);
		}	
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket=new ServerSocket(PORT);
		socket.setSoTimeout(1000);
		BlockingQueue<Runnable> workingThreadQueue = new ArrayBlockingQueue<Runnable>(20);
		ExecutorService threadPool =new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, workingThreadQueue);
		
		while(true) {
			System.out.println("Waiting");
			ClientWorkerShell worker=null;
			
			try {
				EnvironmentImpl impl=new EnvironmentImpl(socket.accept(),commands);
				worker= new ClientWorkerShell(impl);
				System.out.println("conenction established");
				threadPool.execute(worker);
			}
			catch (Exception e) {
				System.out.println("No Connection");
			}
		}
	}
}
