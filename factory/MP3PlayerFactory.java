package factory;

import players.AudioPlayer;
import adapters.MP3PlayerAdapter;

public class MP3PlayerFactory extends PlayerFactory {
    @Override
    public AudioPlayer createPlayer() {
        return new MP3PlayerAdapter(); // Returns an Adapter instead of MP3Player
    }
}