package factory;

import players.AudioPlayer;
import adapters.WAVPlayerAdapter;

public class WAVPlayerFactory extends PlayerFactory {
    @Override
    public AudioPlayer createPlayer() {
        return new WAVPlayerAdapter(); // Returns an Adapter instead of WAVPlayer
    }
}