import java.io.File;
import java.util.HashMap;
import java.util.Map;

import players.AudioPlayer;

import factory.PlayerFactory;
import factory.MP3PlayerFactory;
import factory.WAVPlayerFactory;
import factory.FLACPlayerFactory;
import factory.AACPlayerFactory;
import factory.OGGPlayerFactory;


class MediaPlayer {
    private Map<String, PlayerFactory> factoryMap = new HashMap<>();
    private AudioPlayer currentPlayer;

    public MediaPlayer() {
        // Register factories that return adapters
        factoryMap.put("mp3", new MP3PlayerFactory());
        factoryMap.put("wav", new WAVPlayerFactory());
        factoryMap.put("flac", new FLACPlayerFactory());
        factoryMap.put("aac", new AACPlayerFactory());
        factoryMap.put("ogg", new OGGPlayerFactory());
    }
    
    
    void play(File file) {
        String format = detectFormat(file);
        if (format == null) {
            System.out.println("Unsupported file format: " + file.getName());
            return;
        }

        PlayerFactory factory = factoryMap.get(format);
        if (factory != null) {
            currentPlayer = factory.createPlayer(); // Uses Adapter
            currentPlayer.play(file.getName());
        }
    }
    
    void pause() {
        System.out.println("Paused");
    }
    
    void stop() {
        System.out.println("Stopped");
    }

    public void displaySupportedFormats() {
    System.out.println("Supported formats: " + String.join(", ", factoryMap.keySet()));
    }

    boolean isSupported(File file) {
        return detectFormat(file) != null;
    }

    public String getSupportedFormats() {
    return String.join(", ", factoryMap.keySet());
    }

    private String detectFormat(File file) {
        String fileName = file.getName().toLowerCase();
        for (String format : factoryMap.keySet()) {
            if (fileName.endsWith("." + format)) {
                return format;
            }
        }
        return null;
    }
}