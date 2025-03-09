import java.io.File;
import java.util.ArrayList;
import java.util.List;

class MediaPlayer {
    // private List<String> supportedFormats = new ArrayList<>();
    // private Object currentPlayer;

    private List<AudioPlayer> supportedFormats = new ArrayList<>();
    
    // MediaPlayer() {
    //     supportedFormats.add("mp3");
    //     supportedFormats.add("wav");
    //     supportedFormats.add("flac");
    //     supportedFormats.add("aac");
    //     supportedFormats.add("ogg");
    // }

        //add new format adapters here
        MediaPlayer() {
        supportedFormats.add(new MP3PlayerAdapter);
        supportedFormats.add(new WAVPlayerAdapter);
        supportedFormats.add(new FLACPlayerAdapter);
        supportedFormats.add(new AACPlayerAdapter);
        supportedFormats.add(new OGGPlayerAdapter);
    }
    
    void play(File file) {
        String format = detectFormat(file);
        if (format == null) {
            System.out.println("Unsupported file format: " + file.getName());
            return;
        }
        System.out.println("Playing: " + file.getName());

        for (AudioPlayer player: players) 
        {
            if(player.getFormat().equals(format))
            {
                supportedFormats.play(file.getName());
                return;
            }
        }
        
        // switch (format) {
        //     case "mp3":
        //         currentPlayer = new MP3Player();
        //         ((MP3Player) currentPlayer).playMP3(file.getName());
        //         break;
        //     case "wav":
        //         currentPlayer = new WAVPlayer();
        //         ((WAVPlayer) currentPlayer).playWAV(file.getName());
        //         break;
        //     case "flac":
        //         currentPlayer = new FLACPlayer();
        //         ((FLACPlayer) currentPlayer).decodeFLAC(file.getName());
        //         break;
        //     case "aac":
        //         currentPlayer = new AACPlayer();
        //         ((AACPlayer) currentPlayer).playAAC(file.getName());
        //         break;
        //     case "ogg":
        //         currentPlayer = new OGGPlayer();
        //         ((OGGPlayer) currentPlayer).playOGG(file.getName());
        //         break;
        // }
    }
    
    void pause() {
        System.out.println("Paused");
    }
    
    void stop() {
        System.out.println("Stopped");
    }
    
    void displaySupportedFormats() {
        System.out.println("Supported formats: " + supportedFormats);
    }

    boolean isSupported(File file) {
        return detectFormat(file) != null;
    }

    String getSupportedFormats() {
        return String.join(", ", supportedFormats);
    }
    
    // private String detectFormat(File file) {
    //     String fileName = file.getName().toLowerCase();
    //     for (String format : supportedFormats) {
    //         if (fileName.endsWith("." + format)) {
    //             return format;
    //         }
    //     }
    //     return null;
    // }

    private String detectFormat(File file) {
        String fileName = file.getName().toLowerCase();
        for (AudioPlayer player : players) {
            if (fileName.endsWith("." + player.getFormat())) {
                return player.getFormat();
            }
        }
        return null;
    }
}

// class MP3Player {
//     void playMP3(String fileName) { System.out.println("Playing MP3 file: " + fileName); }
// }

// class WAVPlayer {
//     void playWAV(String fileName) { System.out.println("Playing WAV file: " + fileName); }
// }

// class FLACPlayer {
//     void decodeFLAC(String fileName) { System.out.println("Playing FLAC file: " + fileName); }
// }

// class AACPlayer {
//     void playAAC(String fileName) { System.out.println("Playing AAC file: " + fileName); }
// }

// class OGGPlayer {
//     void playOGG(String fileName) { System.out.println("Playing OGG file: " + fileName); }
// }
