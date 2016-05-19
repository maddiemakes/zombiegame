package game.settings;

import org.newdawn.slick.Color;
import org.newdawn.slick.Music;

public class SettingsGame {

    public static int     playerX                     = 228;
    public static int     playerY                     = 150;

    public static int     killCount                   = 0;
    public static int     zombiesSpawned              = 0;
    public static int     gunShootTime                = 0;
    public static int     gamePlayTime                = 0;
    public static int     startingWave                = 2000;
    public static int     currentWave                 = startingWave;
    public static int     zombiesBeforeThisWave       = 0;
    public static int     zombieSpawnDelay            = 5000 - (currentWave*50);
    public static int     zombieWaveDelay             = 40000;
    public static int     zombieWaveAlarm             = 12000;
    public static int     zombiesSpawnedThisWave      = 0;
    public static int     zombieWaveTimer             = 18000;
    public static int     delayBeforeFirstWave        = 10000;
    public static int     zombieSpawnTimer            = zombieWaveTimer;

    public static Color   healthBarColor              = new Color(52, 166, 163);

    public static float   musicVolume                 = 1;
    public static float   zombieAlarmVolume           = 1;
    public static float   playerVolume                = .5f;
    public static float   zombieVolume                = .5f;
    public static float   gunShootVolume              = .12f;
    public static float   gunReloadVolume             = .5f;
    public static float   gunSwapVolume               = 1;

    public static int     zombiesPerWave              = 10;
    public static int     minimumZombieSpawnDelay     = 200;
    public static int     zombieSpawnDelayDecrement   = currentWave * 50;
    public static int     targetFrameRate             = 60;
}
