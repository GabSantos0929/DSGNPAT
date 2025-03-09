import java.io.File;
import java.util.HashMap;
import java.util.Map;

// ===============================
// PRODUCT INTERFACE
// ===============================
interface Player {
    void play(String fileName);
}

// ===============================
// CONCRETE PRODUCTS
// ===============================

class MP3Player implements Player {
    @Override
    public void play(String fileName) {
        System.out.println("Playing MP3 file: " + fileName);
    }
}

class WAVPlayer implements Player {
    @Override
    public void play(String fileName) {
        System.out.println("Playing WAV file: " + fileName);
    }
}

class FLACPlayer implements Player {
    @Override
    public void play(String fileName) {
        System.out.println("Playing FLAC file: " + fileName);
    }
}

class AACPlayer implements Player {
    @Override
    public void play(String fileName) {
        System.out.println("Playing AAC file: " + fileName);
    }
}

class OGGPlayer implements Player {
    @Override
    public void play(String fileName) {
        System.out.println("Playing OGG file: " + fileName);
    }
}

// ===============================
// CREATOR (ABSTRACT FACTORY)
// ===============================

abstract class PlayerFactory {
    abstract Player createPlayer(); // Factory Method
}

// ===============================
// CONCRETE FACTORIES
// ===============================

class MP3PlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new MP3Player(); // Returns an MP3Player instance
    }
}

class WAVPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new WAVPlayer(); // Returns a WAVPlayer instance
    }
}

class FLACPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new FLACPlayer(); // Returns a FLACPlayer instance
    }
}

class AACPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new AACPlayer(); // Returns an AACPlayer instance
    }
}

class OGGPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new OGGPlayer(); // Returns an OGGPlayer instance
    }
}

// ===============================
// CLIENT CLASS
// ===============================

public class MediaPlayer {
    // Map to store file format and corresponding factory
    private Map<String, PlayerFactory> factoryMap = new HashMap<>();
    private Player currentPlayer;

    // ===============================
    // CONSTRUCTOR
    // ===============================
    public MediaPlayer() {
        // Register concrete factories in the map (Dependency Injection)
        factoryMap.put("mp3", new MP3PlayerFactory());
        factoryMap.put("wav", new WAVPlayerFactory());
        factoryMap.put("flac", new FLACPlayerFactory());
        factoryMap.put("aac", new AACPlayerFactory());
        factoryMap.put("ogg", new OGGPlayerFactory());
    }

    // ===============================
    // PLAY METHOD
    // ===============================
    public void play(File file) {
        String format = detectFormat(file); // Detect file format
        if (format == null) {
            System.out.println("Unsupported file format: " + file.getName());
            return;
        }

        // Use the corresponding factory to create a player
        PlayerFactory factory = factoryMap.get(format);
        if (factory != null) {
            currentPlayer = factory.createPlayer(); // Factory method call
            currentPlayer.play(file.getName()); // Polymorphic call to the play() method
        }
    }

    // ===============================
    // PAUSE AND STOP METHODS
    // ===============================
    public void pause() {
        System.out.println("Paused");
    }

    public void stop() {
        System.out.println("Stopped");
    }

    // ===============================
    // HELPER METHODS
    // ===============================
    private String detectFormat(File file) {
        String fileName = file.getName().toLowerCase();
        for (String format : factoryMap.keySet()) {
            if (fileName.endsWith("." + format)) {
                return format;
            }
        }
        return null;
    }

    public boolean isSupported(File file) {
        return detectFormat(file) != null;
    }

    public void displaySupportedFormats() {
        System.out.println("Supported formats: " + String.join(", ", factoryMap.keySet()));
    }

    public String getSupportedFormats() {
        return String.join(", ", factoryMap.keySet());
    }
}
