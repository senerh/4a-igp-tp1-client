package Client;

import Server.Commun;
import Server.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {

    private static final int PORT = 1025;
    private String user;
    private String password;
    private InetAddress serverAddress;
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private String receivedMsg;

    public ClientModel() {
        super();
    }

    public ClientModel(String serverName, String user, String password) throws IOException {
        this.serverAddress = InetAddress.getByName(serverName);
        this.user = user;
        this.password = password;
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, PORT);

        System.out.println("Connexion avec le serveur.");

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());

        if (!receive()) {
            throw new IOException("Serveur non reconnu");
        }
        if(!commandeAPOP()) {
            throw new IOException("Erreur d'authentification");
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMails() {
        int nbMessages = commandeSTAT();

        List<String> listMessages = new ArrayList<String>();

        for (int i = 1 ; i <= nbMessages ; i++) {
            listMessages.add(commandeRETR(i));
        }

        return listMessages;
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
        send("APOP " + user + " " + password);
        return receive();
    }

    private int commandeSTAT() {
        send("STAT ");
        receive();

        return Integer.parseInt(receivedMsg.split(" ")[1]);
    }

    private int[] commandeLIST() {
        send("LIST ");

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

    public String commandeRETR(int numeroMessage) {
        send("RETR " + numeroMessage);
        receive();

        String mail = "";
        receive();

        while (!receivedMsg.equals(".")) {
            mail = mail + receivedMsg;
            receive();
        }

        return mail;
    }



}
