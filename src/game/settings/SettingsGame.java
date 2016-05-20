package game.settings;

import game.state.LevelState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Music;

public class SettingsGame {

    public  static int     playerX                     = 228;
    public  static int     playerY                     = 150;

    public  static int     killCount                   = 0;
    public  static int     zombiesSpawned              = 0;
    public  static int     gunShootTime                = 0;
    public  static int     gamePlayTime                = 0;
    public  static int     startingWave                = 10;
    public  static int     currentWave                 = startingWave;
    public  static int     zombiesBeforeThisWave       = 0;
    public  static int     zombieSpawnDelay            = 5000 - (currentWave*50);
    public  static int     zombieWaveDelay             = 40000;
    public  static int     zombieWaveAlarm             = 12000;
    public  static int     zombiesSpawnedThisWave      = 0;
    public  static int     zombieWaveTimer             = 18000;
    public  static int     delayBeforeFirstWave        = 10000;
    public  static int     zombieSpawnTimer            = zombieWaveTimer;
    public  static int     zombiesPerWave              = 100;
    public  static int     minimumZombieSpawnDelay     = 200;
    public  static int     zombieSpawnDelayDecrement   = currentWave * 50;

    //(these are for when the others get muted)
    private static boolean muted                       = false;
    private static float   musicVolumeMax              = 1;
    private static float   zombieAlarmVolumeMax        = 1;
    private static float   playerVolumeMax             = .5f;
    private static float   zombieVolumeMax             = .5f;
    private static float   gunShootVolumeMax           = .12f;
    private static float   gunReloadVolumeMax          = .5f;
    private static float   gunSwapVolumeMax            = 1;

    //actual volumes
    public  static float   musicVolume       = musicVolumeMax;
    public  static float   zombieAlarmVolume = zombieAlarmVolumeMax;
    public  static float   playerVolume      = playerVolumeMax;
    public  static float   zombieVolume      = zombieVolumeMax;
    public  static float   gunShootVolume    = gunShootVolumeMax;
    public  static float   gunReloadVolume   = gunReloadVolumeMax;
    public  static float   gunSwapVolume     = gunSwapVolumeMax;

    public  static int     targetFrameRate             = 60;
    public  static Color   healthBarColor              = new Color(52, 166, 163);

    public static void mute() {
        if (muted) {
            musicVolume       = musicVolumeMax;
            zombieAlarmVolume = zombieAlarmVolumeMax;
            playerVolume      = playerVolumeMax;
            zombieVolume      = zombieVolumeMax;
            gunShootVolume    = gunShootVolumeMax;
            gunReloadVolume   = gunReloadVolumeMax;
            gunSwapVolume     = gunSwapVolumeMax;
            LevelState.music.setVolume(musicVolume);
        }
        else {
            musicVolume       = 0;
            zombieAlarmVolume = 0;
            playerVolume      = 0;
            zombieVolume      = 0;
            gunShootVolume    = 0;
            gunReloadVolume   = 0;
            gunSwapVolume     = 0;
            LevelState.music.setVolume(musicVolume);
        }
        muted = !muted;
    }
}
