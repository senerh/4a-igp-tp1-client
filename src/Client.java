import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Client {

	private static final int PORT = 110;
	private String user;
	private String password;
	private InetAddress serverAddress;
	private Socket socket;
	private BufferedReader in;
	private PrintStream out;

	public Client(String serverName, String user, String password) throws UnknownHostException, IOException {
		this.serverAddress = InetAddress.getByName(serverName);
		this.user = user;
		this.password = password;
		connect();
	}

	public void connect() throws IOException {
		socket = new Socket(serverAddress, PORT);

		System.out.println("Connexion avec le serveur.");

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
	}

	private void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getMails() {
		String msg;
		msg = receive();

		send("APOP " + user + " " + password);
		msg = receive();
		int nbMails = Integer.parseInt(msg.split(" ")[3]);

		List<String> listMessages = new ArrayList<String>();
		for (int i=1; i<=nbMails; i++) {
			send("RETR " + i);
			msg = receive();
			listMessages.add(msg);
			send("DELE " + i);
			msg = receive();
			send("QUIT");
			msg = receive();
		}

		return listMessages;
	}

	private String receive() {
		String msg = null;
		try {
			msg = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(msg);
		return msg;
	}

	private void send(String msg) {
		System.out.println(msg);
		out.println(msg);
	}
}
