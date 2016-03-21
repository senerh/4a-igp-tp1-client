import javax.swing.SwingUtilities;

import model.Client;
import viewController.Window;

public class Main {
    public static void main(String args[]) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Client clientModel = new Client();
                Window window = new Window(clientModel);
                window.setVisible(true);
            }
        });
    }
}
