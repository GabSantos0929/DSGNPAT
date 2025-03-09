package adapters;
import players.AudioPlayer;
import players.MP3Player;

public class MP3PlayerAdapter implements AudioPlayer {
    private MP3Player mp3Player = new MP3Player();

    @Override
    public void play(String fileName) {
        mp3Player.playMP3(fileName);
    }
}
