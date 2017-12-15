package serverShell;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public interface Environment {
	
	public void sendText(String text);
	public String getText();
	public boolean sendImage(String path);
	public BufferedImage getImage();
	public void initializeStreams();
	public void close();
	public HashMap<String, ShellCommand> getCommands();
	public ShellCommand getCommand(String command);
}
