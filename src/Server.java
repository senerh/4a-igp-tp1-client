import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	final static int port = 110;

	public static void main(String[] args) {
		try {
			ServerSocket socketServeur = new ServerSocket(port);
			System.out.println("Lancement du serveur");

			Socket socketClient = socketServeur.accept();
			String message = "";

			//System.out.println("Connexion avec : "+socketClient.getInetAddress());

			while (1==1) {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socketClient.getInputStream()));
				PrintStream out = new PrintStream(socketClient.getOutputStream());
				out.println("Bonjour ma petite gueule.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
