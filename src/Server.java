import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	final static int port = 1025;

	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket socketServeur = new ServerSocket(port);
			System.out.println("Lancement du serveur");

			Socket socketClient = socketServeur.accept();

			//System.out.println("Connexion avec : "+socketClient.getInetAddress());

			while (true) {
				/*BufferedReader in = new BufferedReader(
						new InputStreamReader(socketClient.getInputStream()));*/
				PrintStream out = new PrintStream(socketClient.getOutputStream());
				out.println("Bonjour ma petite gueule.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
