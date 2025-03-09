package adapters;
import players.AudioPlayer;
import players.OGGPlayer;

public class OGGPlayerAdapter implements AudioPlayer {
    private OGGPlayer oggPlayer = new OGGPlayer();

    @Override
    public void play(String fileName) {
        oggPlayer.playOGG(fileName);
    }
}
