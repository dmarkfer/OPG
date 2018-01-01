package serverShell;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public class EnvironmentImpl implements Environment {
	private static final String FAIL="fail";
	private Socket accessPoint=null;
	private PrintWriter writeTo=null;
	private BufferedReader readFrom=null;
	private DataOutputStream writeToByte=null;
	private DataInputStream readFromByte=null;
	private StringBuilder buffer=new StringBuilder();
	private HashMap<String, ShellCommand> commands;
	private Connection connection=null;
	
	public EnvironmentImpl(Socket socket, HashMap<String, ShellCommand> commands) {
		accessPoint=socket;
		this.commands=commands;
		initializeStreams();
		createDatabaseConnection();
	}
	
	private void createDatabaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager
					.getConnection("jdbc:postgresql://165.227.175.217:5432/fangladb", //magic numbers, sifra
									"development", "vladimirPutin");

		} catch (Exception e) {
			System.out.println("Database can't open");
			System.exit(0);
		}
	}

	@Override
	public void sendText(String text) {
		writeTo.println(text);
	}

	@Override
	public String getText() {
		try {		
			buffer.append(readFrom.readLine()+"\n");
			while(readFrom.ready()) {
				buffer.append(readFrom.readLine()+"\n");
			}
			
		} catch (IOException e) {
			System.out.println("Unable to get text from client");
			return FAIL;
		}
		String text=buffer.toString();
		buffer.setLength(0);
		return text;
	}

	@Override
	public boolean sendImage(String path) {
		byte[] imageInBytes;
		try {
			imageInBytes=convertImageToByte(path);
			writeToByte.writeInt(imageInBytes.length);
			writeToByte.write(imageInBytes);
		} catch (IOException e) {
			System.out.println("Unable to read picture");
			return false;
		}
		return true;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage fromByte=null;
		try {
			int len=readFromByte.readInt();
			if (len>0) {
				byte[] image=new byte[len];
				readFromByte.readFully(image,0,image.length);
				InputStream input=new ByteArrayInputStream(image);
				fromByte=ImageIO.read(input);
			}
		} catch (IOException e) {
			System.out.println("Reading image from stream error.");
			return null;
		}
		return fromByte;
	}


	@Override
	public void initializeStreams() {
		try {
			writeTo=new PrintWriter(accessPoint.getOutputStream(),true);
			readFrom=new BufferedReader(new InputStreamReader(accessPoint.getInputStream(), StandardCharsets.UTF_8));
			writeToByte=new DataOutputStream(accessPoint.getOutputStream());
			readFromByte=new DataInputStream(accessPoint.getInputStream());
			
		}catch (IOException e) {
			System.out.println("Couldn't initialize IO streams. Terminating connection");
			System.exit(-1);
			
		}
	}
	
	@Override
	public void close() {
		try {
			writeTo.close();
			readFrom.close();
			readFromByte.close();
			writeToByte.close();
			accessPoint.close();
		} catch (IOException Ignorable) {}
	}
	
	private byte[] convertImageToByte(String path) throws IOException {
		BufferedImage original=ImageIO.read(this.getClass().getResource("/resources/testPic.jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ImageIO.write(original, "jpg", baos);
		baos.flush();
		byte[] array=baos.toByteArray();
		baos.close();
		return array;
	}
	
	public HashMap<String, ShellCommand> getCommands() {
		return commands;
	}

	@Override
	public ShellCommand getCommand(String command) {
		if (command!=null && commands.containsKey(command)) {
			return commands.get(command.toUpperCase());
		}
		return null;
	}
	
	@Override
	public Connection getDatabase() {
		return connection;
	}
}
