import java.net.UnknownHostException;


public class Main {
	
	public static void main(String args[]) {
		Client client = null;
		try {
			client = new Client("134.214.119.113", "user", "password");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		client.getMails();
	}

}
