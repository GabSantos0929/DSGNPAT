package factory;

import players.AudioPlayer;
import adapters.FLACPlayerAdapter;

public class FLACPlayerFactory extends PlayerFactory {
    @Override
    public AudioPlayer createPlayer() {
        return new FLACPlayerAdapter(); // Returns an Adapter instead of FLACPlayer
    }
}