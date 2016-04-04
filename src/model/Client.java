package model;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Client extends Observable {

    private static final int PORT = 1026;
    private String user;
    private String password;
    private InetAddress serverAddress;
    private SSLSocket socket;
    private BufferedReader in;
    private PrintStream out;
    private String receivedMsg;
    private List<Mail> listReceivedMails;
    private String cryptPassword;
    
    public Client() {
    	listReceivedMails = new ArrayList<>();
    }

    public void connect(String serverName, String user, String password) throws IOException {
    	this.user = user;
    	this.password = password;
    	serverAddress = InetAddress.getByName(serverName);

        SSLSocketFactory fabrique = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) fabrique.createSocket(serverAddress, PORT);

        String[] ciphers = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(ciphers);

        System.out.println("Connexion avec le serveur.");

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());

        if (!receive()) {
            throw new IOException("Serveur non reconnu");
        } else {
            String key = receivedMsg.split(" ")[4];
            key = key.substring(1, key.length()-1);

            MessageDigest m;
            try {
                m = MessageDigest.getInstance("MD5");
                key += password;
                m.update(key.getBytes(),0,key.length());
                key = new BigInteger(1,m.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                throw new IOException("Erreur MD5");

            }
            cryptPassword = key;
        }
        if(!commandeAPOP()) {
            throw new IOException("Erreur d'authentification");
        }
    }

    public void disconnect() {
    	if (socket != null) {
    		commandeQUIT();
    		try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    }



    public void receiveMails() {
        int nbMessages = commandeSTAT();

        listReceivedMails.clear();

        for (int i = 1 ; i <= nbMessages ; i++) {
            listReceivedMails.add(commandeRETR(i));
        }
        
        setChanged();
	    notifyObservers();
    }

    private boolean receive() {
        try {
            receivedMsg = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(receivedMsg);
        return receivedMsg.startsWith("+OK");
    }

    private void send(String msg) {
        System.out.println(msg);
        out.println(msg);
    }

    private boolean commandeAPOP() {
        send("APOP " + user + " " + cryptPassword);
        return receive();
    }

    private int commandeSTAT() {
        send("STAT");
        receive();

        return Integer.parseInt(receivedMsg.split(" ")[1]);
    }

    public void commandeRESET() {
        send("RSET");
        receive();
    }

    private int[] commandeLIST() {
        send("LIST");

        receive();
        int nbMessages = Integer.parseInt(receivedMsg.split(" ")[1]);

        int[] tabTailleMessage = new int[0];
        for (int i = 0 ; i < nbMessages ; i++) {
            receive();
            tabTailleMessage[i] = Integer.parseInt(receivedMsg.split(" ")[1]);
        }
        receive();

        return tabTailleMessage;
    }

    private Mail commandeRETR(int numeroMessage) {
        send("RETR " + numeroMessage);
        receive();

        String content = "";
        receive();

        while (!receivedMsg.equals(".")) {
            content = content + "\n" + receivedMsg;
            receive();
        }

        return new Mail(content, numeroMessage);
    }
    
    public boolean commandeDELE(int numeroMessage) {
    	send("DELE " + numeroMessage);
        return receive();
    }
    
    private boolean commandeQUIT() {
    	send("QUIT");
        return receive();
    }

	public List<Mail> getReceivedMails() {
		return listReceivedMails;
	}

}
