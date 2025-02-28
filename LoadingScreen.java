import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * A custom dialog to display a loading screen with a progress bar and a message.
 * The loading screen is shown on a separate thread using SwingWorker to prevent
 * blocking the main GUI thread.
 */
class LoadingScreen extends JDialog {

    /**
     * Creates a new instance of the LoadingScreen class.
     */
    public LoadingScreen() {
        super((Frame) null, "Loading", true);
    }

    /**
     * Shows the loading screen with a progress bar and a loading message.
     * The loading screen is displayed on a separate thread using SwingWorker
     * to prevent blocking the main GUI thread.
     */
    public void showLoadingScreen() {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true); 

        JLabel loadingLabel = new JLabel("Please wait, loading...");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(progressBar, BorderLayout.CENTER);
        add(loadingLabel, BorderLayout.PAGE_START);

        setSize(300, 100);
        setLocationRelativeTo(null); 

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                setVisible(true);
                return null;
            }
        };

        worker.execute();
    }

    /**
     * Closes the loading screen dialog.
     */
    public void closeLoadingScreen() {
        dispose();
    }
}
