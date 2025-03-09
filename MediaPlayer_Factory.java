import java.io.File;
import java.util.HashMap;
import java.util.Map;

// Product Interface
interface Player {
    void play(String fileName);
}

// Concrete Products
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

abstract class PlayerFactory {
    abstract Player createPlayer();
}

class MP3PlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new MP3Player();
    }
}

class WAVPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new WAVPlayer();
    }
}

class FLACPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new FLACPlayer();
    }
}

class AACPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new AACPlayer();
    }
}

class OGGPlayerFactory extends PlayerFactory {
    @Override
    Player createPlayer() {
        return new OGGPlayer();
    }
}

public class MediaPlayer {
    private Map<String, PlayerFactory> factoryMap = new HashMap<>();
    private Player currentPlayer;

    public MediaPlayer() {
        factoryMap.put("mp3", new MP3PlayerFactory());
        factoryMap.put("wav", new WAVPlayerFactory());
        factoryMap.put("flac", new FLACPlayerFactory());
        factoryMap.put("aac", new AACPlayerFactory());
        factoryMap.put("ogg", new OGGPlayerFactory());
    }

    public void play(File file) {
        String format = detectFormat(file);
        if (format == null) {
            System.out.println("Unsupported file format: " + file.getName());
            return;
        }

        PlayerFactory factory = factoryMap.get(format);
        if (factory != null) {
            currentPlayer = factory.createPlayer();
            currentPlayer.play(file.getName());
        }
    }

    public void pause() {
        System.out.println("Paused");
    }

    public void stop() {
        System.out.println("Stopped");
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
