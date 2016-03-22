package viewController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Client;

public class AuthentificationForm extends JPanel {

	private static final long serialVersionUID = -2969449160002375526L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 100;
	
	private JLabel userLabel = new JLabel("User");
    private JTextField userField = new JTextField();
     
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField();
     
    private JLabel serverLabel = new JLabel("Server name");
    private JTextField serverField = new JTextField();
    
    private JButton validButton = new JButton();
    
    private Client clientModel;
    
    public AuthentificationForm(Client clientModel) {
    	this.clientModel = clientModel;
    	setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	build();
    }
    
    private void build() {
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(400, 100));
        setBackground(Color.LIGHT_GRAY);
        
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        validButton.setText("Valid");
        validButton.addActionListener(new Controller());

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(userLabel)
                        .addComponent(passwordLabel)
                        .addComponent(serverLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(userField)
                        .addComponent(passwordField)
                        .addComponent(serverField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(validButton))
                );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userLabel)
                        .addComponent(userField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(serverLabel)
                        .addComponent(serverField)
                        .addComponent(validButton))
        );
    }
	
	private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            String user = userField.getText();
            String password = new String(passwordField.getPassword());
            String server = serverField.getText();

            try {
                clientModel.disconnect();
                clientModel.connect(server, user, password);
                clientModel.receiveMails();
            } catch (IOException e) {
            	JOptionPane.showMessageDialog(AuthentificationForm.this,
                        "Vérifiez que les données saisies sont correctes et que le serveur est démarré.",
                        "Erreur lors du la connexion au serveur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
