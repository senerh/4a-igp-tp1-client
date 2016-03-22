package viewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Client;
import model.Mail;

public class MailBox extends JPanel implements Observer {

	private static final long serialVersionUID = -4726250233795096089L;
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 375;
	
	private Client client;
	
	private JButton btPrevious;
	private JButton btNext;
	private JButton btDelete;
	private JTextArea lblMail;
	
	private JPanel header;
	
	private int currentMail;
	private List<Mail> listMails;
	
	public MailBox(Client client) {
		this.client = client;
		this.client.addObserver(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		build();
		controller();
	}
	
	private void build() {
		header = new JPanel(new BorderLayout(0, 0));
		setLayout(new BorderLayout(0, 0));
		btPrevious = new JButton("Precedent");
		btNext = new JButton("Suivant");
		btDelete = new JButton("Supprimer");
		lblMail = new JTextArea("");
		lblMail.setLineWrap(true);
		lblMail.setEditable(false);
		
		header.add(btPrevious, BorderLayout.LINE_START);
		header.add(btDelete, BorderLayout.CENTER);
		header.add(btNext, BorderLayout.LINE_END);
		
		btNext.setVisible(false);
		btPrevious.setVisible(false);
		btDelete.setVisible(false);
		
		add(header, BorderLayout.PAGE_START);
		add(lblMail, BorderLayout.CENTER);
	}
	
	private void controller() {
		btPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentMail--;
				btNext.setVisible(true);
				if (currentMail <= 0) {
					btPrevious.setVisible(false);
				}
				lblMail.setText(listMails.get(currentMail).getContent());
			}
		});
		
		btNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentMail++;
				btPrevious.setVisible(true);
				if (currentMail >= listMails.size() - 1) {
					btNext.setVisible(false);
				}
				lblMail.setText(listMails.get(currentMail).getContent());
			}
		});
		
		btDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.commandeDELE(listMails.get(currentMail).getNumeroMail());
				listMails.remove(currentMail);
				if (listMails.size() == 0) {
					JOptionPane.showMessageDialog(MailBox.this,
		                    "Message supprimé.",
		                    "Vous n'avez plus aucun message.",
		                    JOptionPane.INFORMATION_MESSAGE);
					btPrevious.setVisible(false);
					btNext.setVisible(false);
					btDelete.setVisible(false);
					lblMail.setVisible(false);
				} else if (listMails.size() == 1) {
					JOptionPane.showMessageDialog(MailBox.this,
		                    "Message supprimé.",
		                    "Information",
		                    JOptionPane.INFORMATION_MESSAGE);
					btPrevious.setVisible(false);
					btNext.setVisible(false);
					btDelete.setVisible(true);
					currentMail = 0;
					lblMail.setText(listMails.get(currentMail).getContent());
				} else {
					JOptionPane.showMessageDialog(MailBox.this,
		                    "Message supprimé.",
		                    "Information",
		                    JOptionPane.INFORMATION_MESSAGE);
					btPrevious.setVisible(false);
					btNext.setVisible(true);
					btDelete.setVisible(true);
					currentMail = 0;
					lblMail.setText(listMails.get(currentMail).getContent());
				}
			}
		});
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		listMails = client.getReceivedMails();
		currentMail = 0;
		int nbMails = listMails.size();
		if (nbMails == 0) {
			JOptionPane.showMessageDialog(MailBox.this,
                    "Aucun message reçu",
                    "Vous n'avez aucun message.",
                    JOptionPane.INFORMATION_MESSAGE);
			btPrevious.setVisible(false);
			btNext.setVisible(false);
			btDelete.setVisible(false);
		} else if (nbMails == 1) {
			JOptionPane.showMessageDialog(MailBox.this,
                    "1 message reçu",
                    "Vous avez 1 message.",
                    JOptionPane.INFORMATION_MESSAGE);
			btPrevious.setVisible(false);
			btNext.setVisible(false);
			btDelete.setVisible(true);
			lblMail.setText(listMails.get(currentMail).getContent());
		} else {
			JOptionPane.showMessageDialog(MailBox.this,
                    nbMails + " messages reçus",
                    "Vous avez " + nbMails + " messages.",
                    JOptionPane.INFORMATION_MESSAGE);
			btPrevious.setVisible(false);
			btNext.setVisible(true);
			btDelete.setVisible(true);
			lblMail.setText(listMails.get(currentMail).getContent());
		}
	}
}
