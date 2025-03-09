package factory;

import players.AudioPlayer;
import adapters.OGGPlayerAdapter;

public class OGGPlayerFactory extends PlayerFactory {
    @Override
    public AudioPlayer createPlayer() {
        return new OGGPlayerAdapter(); // Returns an Adapter instead of OGGPlayer
    }
}