package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Fenetre extends JFrame {
    private ClientModel clientModel;
    private JPanel container = new JPanel(new BorderLayout(0, 0));
    private JPanel form = new JPanel();
    private JPanel mailBox = new JPanel();


    private JLabel userLabel = new JLabel("User");
    private JTextField userField = new JTextField();

    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField();

    private JLabel serverLabel = new JLabel("Server name");
    private JTextField serverField = new JTextField();

    private JLabel mailLabel = new JLabel();

    private JButton validButton = new JButton();

    public Fenetre(){
        this.setTitle("Client POP3");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10 ));
        form.setPreferredSize(new Dimension(400, 100));
        form.setBackground(Color.LIGHT_GRAY);

        mailBox.setPreferredSize(new Dimension(400, 400));
        mailBox.setBackground(Color.ORANGE);

        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);

        validButton.setText("Valid");
        validButton.addActionListener(new validListener());

        layout.setHorizontalGroup( layout.createSequentialGroup()
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

        container.add(form, BorderLayout.PAGE_START);
        container.add(mailBox, BorderLayout.PAGE_END);
        mailBox.add(mailLabel);
        add(container);
        this.setVisible(true);
    }

    private class validListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            String user = userField.getText();
            String password = new String(passwordField.getPassword());
            String server = serverField.getText();

            try {
                clientModel = new ClientModel(server, user, password);
                clientModel.connect();
                String msg ="";
                for(String mail : clientModel.getMails()){
                    msg = msg+mail;
                }
                mailLabel.setText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
