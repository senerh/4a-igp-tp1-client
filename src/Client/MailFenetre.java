package Client;

import javax.swing.*;

public class MailFenetre extends JFrame {
    private ClientModel clientModel;
    private JPanel container = new JPanel();

    public MailFenetre(){
        this.setTitle("Boite mail");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        container.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10 ));
        this.setContentPane(container);
        this.setVisible(true);
    }
}
