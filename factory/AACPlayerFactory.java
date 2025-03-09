package factory;

import players.AudioPlayer;
import adapters.AACPlayerAdapter;

public class AACPlayerFactory extends PlayerFactory {
    @Override
    public AudioPlayer createPlayer() {
        return new AACPlayerAdapter(); // Returns an Adapter instead of AACPlayer
    }
}