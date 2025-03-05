import javax.swing.*;
import java.awt.*;
import java.io.File;

class GUI {
    private MediaPlayer mediaPlayer;
    private JLabel statusLabel;
    private File selectedFile;
    
    GUI() {
        mediaPlayer = new MediaPlayer();
        JFrame frame = new JFrame("Media Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel uploadPanel = new JPanel();
        JButton uploadButton = new JButton("Upload File");
        uploadPanel.add(uploadButton);
        
        JPanel controlPanel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        
        JPanel formatPanel = new JPanel();
        JButton displayFormatsButton = new JButton("Supported Formats");
        formatPanel.add(displayFormatsButton);
        
        statusLabel = new JLabel("Status: Waiting for file...");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(uploadPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(formatPanel);
        mainPanel.add(statusLabel);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                if (mediaPlayer.isSupported(selectedFile)) {
                    statusLabel.setText("File selected: " + selectedFile.getName());
                } else {
                    statusLabel.setText("Unsupported file format");
                    selectedFile = null;
                }
            }
        });
        
        playButton.addActionListener(e -> {
            if (selectedFile != null) {
                statusLabel.setText("Playing: " + selectedFile.getName());
                mediaPlayer.play(selectedFile);
            } else {
                statusLabel.setText("No file selected.");
            }
        });
        
        pauseButton.addActionListener(e -> {
            if (selectedFile != null) {
                statusLabel.setText("Media paused");
                mediaPlayer.pause();
            } else {
                statusLabel.setText("No file selected.");
            }
        });
        
        stopButton.addActionListener(e -> {
            if (selectedFile != null) {
                statusLabel.setText("Stopped playing");
                mediaPlayer.stop();
                selectedFile = null;
            } else {
                statusLabel.setText("No file selected.");
            }
        });
        
        displayFormatsButton.addActionListener(e -> {
            mediaPlayer.displaySupportedFormats();
            statusLabel.setText("Supported formats: " + mediaPlayer.getSupportedFormats());
        });
    }
}
