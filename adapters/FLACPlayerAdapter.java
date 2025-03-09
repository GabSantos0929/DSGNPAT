package adapters;
import players.AudioPlayer;
import players.FLACPlayer;

public class FLACPlayerAdapter implements AudioPlayer {
    private FLACPlayer flacPlayer = new FLACPlayer();

    @Override
    public void play(String fileName) {
        flacPlayer.decodeFLAC(fileName);
    }
}
