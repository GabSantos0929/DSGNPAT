package adapters;
import players.AudioPlayer;
import players.WAVPlayer;

public class WAVPlayerAdapter implements AudioPlayer {
    private WAVPlayer wavPlayer = new WAVPlayer();

    @Override
    public void play(String fileName) {
        wavPlayer.playWAV(fileName);
    }
}
