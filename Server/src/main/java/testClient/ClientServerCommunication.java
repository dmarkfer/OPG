package testClient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

public class ClientServerCommunication {
	private static final String FAIL="fail";
	
	private Socket accessPoint=null;
	private PrintWriter writeTo=null;
	private BufferedReader readFrom=null;
	private DataOutputStream writeToByte=null;
	private DataInputStream readFromByte=null;
	
	public ClientServerCommunication(Socket accessPoint) {
		
		this.accessPoint=accessPoint;
		initalizeStreams();

	}

	private void initalizeStreams() {
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

	public void sendText(String text) {
		writeTo.println(text);
	}
	
	public String getText() {
		StringBuilder buffer=new StringBuilder();
		try {		
			buffer.append(readFrom.readLine()+"\n");
			while(readFrom.ready()) {
				buffer.append(readFrom.readLine()+"\n");
			}
			
		} catch (IOException e) {
			System.out.println("Unable to get text from client");
			return FAIL;
		}
		return buffer.toString();
	}
	
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
	
	private byte[] convertImageToByte(String path) throws IOException {
		BufferedImage original=ImageIO.read(this.getClass().getResource("/resources/testPic.jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ImageIO.write(original, "jpg", baos);
		baos.flush();
		byte[] array=baos.toByteArray();
		baos.close();
		return array;
	}
	
	public void close() {
		try {
			writeTo.close();
			readFrom.close();
			readFromByte.close();
			writeToByte.close();
			accessPoint.close();
		} catch (IOException Ignorable) {}

	}
}