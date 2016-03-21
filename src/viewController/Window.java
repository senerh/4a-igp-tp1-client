package viewController;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Client;

public class Window extends JFrame {

	private static final long serialVersionUID = -3918384978247030384L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 500;

	private Client client;

	private JPanel container;
	private AuthentificationForm authentificationForm;
	private MailBox mailBox;

	public Window(Client clientModel) {
		this.client = clientModel;
		this.setTitle("Client POP3");
		this.setSize(WIDTH, HEIGHT);
		
		addWindowListener(new WindowAdapter() {
			@Override
	        public void windowClosing(WindowEvent e) {
	            client.disconnect();
	            System.exit(-1);
	        }
		});

		authentificationForm = new AuthentificationForm(client);
		mailBox = new MailBox(client);

		container = new JPanel(new BorderLayout(0, 0));
		container.add(authentificationForm, BorderLayout.PAGE_START);
		container.add(mailBox, BorderLayout.PAGE_END);
		add(container);
		setResizable(false);
	}

}
