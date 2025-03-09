    package factory;

    import players.AudioPlayer;
    // import adapters.MP3PlayerAdapter;
    // import adapters.OGGPlayerAdapter;
    // import adapters.WAVPlayerAdapter;
    // import adapters.FLACPlayerAdapter;
    // import adapters.AACPlayerAdapter;

    public abstract class PlayerFactory {
        public abstract AudioPlayer createPlayer(); // Returns an adapter
    }