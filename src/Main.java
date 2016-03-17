import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;


public class Main {
	
	public static void main(String args[]) {
		Client client = null;
		try {
			client = new Client("134.214.119.118", "HAKAN", "SNIPER");
		} catch (UnknownHostException e) {
			System.out.println("L'adresse serveur " + "" + " n'est pas joignable.");
		} catch (IOException e) {
			System.out.println("Erreur lors de la connexion TCP au serveur.");
		}
		List<String> listMails = client.getMails();
		
		for (String mail : listMails) {
			System.out.println(mail);
		}
	}

}
