import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	
	private static final int PORT = 110;
	private String user;
	private String password;
	private int state;
	private InetAddress serverAddress;
	private BufferedReader in;
	private PrintStream out;
	
	public Client(String serverName, String user, String password) throws UnknownHostException {
		this.serverAddress = InetAddress.getByName(serverName);
		this.user = user;
		this.password = password;
	}
	
	private void connect() {
		
		Socket socket;

	    try {
	      socket = new Socket(serverAddress, PORT);
	      
	      System.out.println("Connexion avec le serveur.");

	      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	      out = new PrintStream(socket.getOutputStream());

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public void getMails() {
		connect();
		String msg = readLine();
		System.out.println(msg);
		
		out.println("APOP " + user + " " + password);
		readLine();
	}
	
	private String readLine() {
		String msg = null;
		try {
			msg = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
}
