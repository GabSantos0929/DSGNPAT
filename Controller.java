import javax.swing.SwingUtilities;

/**
 * The main entry point of the vending machine application.
 * This class contains the main method to start the GUI.
 */
public class Controller {

    /**
     * The main method that serves as the entry point of the application.
     * It starts the GUI by invoking the GUI constructor in the Event Dispatch Thread (EDT).
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
