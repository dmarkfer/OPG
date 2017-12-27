package serverShell;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.xml.crypto.Data;

import database.DatabaseConnection;

public interface Environment {
	
	public void sendText(String text);
	public String getText();
	public boolean sendImage(String path);
	public BufferedImage getImage();
	public void initializeStreams();
	public void close();
	public HashMap<String, ShellCommand> getCommands();
	public ShellCommand getCommand(String command);
	public void setDatabase(DatabaseConnection connection);
	public DatabaseConnection getDatabase();
}
