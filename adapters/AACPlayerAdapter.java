package adapters;
import players.AudioPlayer;
import players.AACPlayer;

public class AACPlayerAdapter implements AudioPlayer {
    private AACPlayer aacPlayer = new AACPlayer();

    @Override
    public void play(String fileName) {
        aacPlayer.playAAC(fileName);
    }
}
