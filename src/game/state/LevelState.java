package game.state;

import game.Game;
import game.character.Player;
import game.character.Zombie;
import game.controller.MouseAndKeyBoardPlayerController;
import game.controller.PlayerController;
import game.controller.ZombieController;
import game.enums.Facing;
import game.level.Level;
import game.physics.Physics;

import game.weapons.Bullet;
import game.weapons.Gun;
import game.weapons.Pistol;
import game.weapons.Rifle;
import javafx.util.Pair;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Music;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Point;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LevelState extends BasicGameState {
    public StateBasedGame game;
    public  static  Level   level;
    private         String  startinglevel;
    public  static  int     containerHeight;
    public  static  int     containerWidth;
    private         Physics physics = new Physics();

    private Font font;
    private TrueTypeFont ttf;
    public  static  List<String> spriteList = new ArrayList<>();
    public  static  List<Pair<String, HashMap<Facing,Image>>> spritesMaps = new ArrayList<>();
    public  static  List<String> animationList = new ArrayList<>();
    public  static  List<Pair<String, HashMap<Facing,Animation>>> animationMaps = new ArrayList<>();

    public  static Player    player;
    private static int       playerX = 228;
    private static int       playerY = 150;
    public  static Gun       playerGun;
    public  static Gun[]     playerGuns = new Gun[2];
    private PlayerController playerController;

    public  static List<Zombie>             zombies = new ArrayList<>();
    public  static List<ZombieController>   zombieControllers = new ArrayList<>();
    public  static List<Bullet>             bullets = new ArrayList<>();


    public  static boolean spawnNew = false;
    public  static boolean attackMe = true;
    public  static boolean paused   = false;
    public  static boolean gameOver = false;

    public  static int killCount                = 0;
    public  static int zombiesSpawned           = 0;
    public  static int gunShootTime             = 0;
    public  static int gamePlayTime             = 0;
    private static int startingWave             = 100;
    private static int currentWave              = startingWave;
    private static int zombiesBeforeThisWave    = 0;
    private static int zombieSpawnDelay         = 5000 - (currentWave*50);
    private static int zombieWaveDelay          = 40000;
    private static int zombieWaveAlarm          = 12000;
    private static int zombiesSpawnedThisWave   = 0;
    private static int zombieWaveTimer          = 18000;
    private static int zombieSpawnTimer         = zombieWaveTimer;

    public  static Music music;
    public  static Music openingMenuMusic;
    public  static Music gameOverMusic;
    public  static Sound zombieAlarm;
    public  static Sound[] zombieHurt = new Sound[6];
    public  static Sound[] zombieDeath = new Sound[5];
    public  static Sound[] playerHurt = new Sound[7];


    public LevelState(String startingLevel){
        this.startinglevel = startingLevel;
    }

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        game = sbg;
        font = new Font("Verdana", Font.BOLD, 10);
        ttf = new TrueTypeFont(font, true);
        //at the start of the game we don't have a player yet
        player = new Player(playerX, playerY);
        Pistol pistol = new Pistol();
        Rifle rifle = new Rifle();
        playerGuns[0] = pistol;
        playerGuns[1] = rifle;
        playerGun = playerGuns[0];

        //gets size of the screen
        containerHeight = container.getHeight();
        containerWidth = container.getWidth();

        //once we initialize our level, we want to load the right level
        level = new Level(startinglevel, player);

        //and we create a controller, for now we use the MouseAndKeyBoardPlayerController
        playerController = new MouseAndKeyBoardPlayerController(player);

        //this sets all of our music and sounds
        zombieAlarm = new Sound("data/audio/sounds/alerts/missile_alarm.ogg");
        zombieHurt = new Sound[]{
                new Sound("data/audio/sounds/zombies/zombie-3.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-5.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-6.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-7.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-10.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-12.ogg"),};
        zombieDeath = new Sound[]{
                new Sound("data/audio/sounds/zombies/zombie-4.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-9.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-10.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-16.ogg"),
                new Sound("data/audio/sounds/zombies/zombie-20.ogg"),};
        playerHurt = new Sound[]{
                new Sound("data/audio/sounds/player/mrk_breathing_hurt10.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt11.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt12.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt13.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt14.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt15.ogg"),
                new Sound("data/audio/sounds/player/mrk_breathing_hurt16.ogg"),};

        openingMenuMusic = new Music("data/audio/music/menu_theme_by_dubwolfer.ogg");
        gameOverMusic = new Music("data/audio/music/game_over_theme_by_dubwolfer.ogg");
        music = openingMenuMusic;
        music.setVolume(30);
        music.loop();
    }

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

        //Pause the music if the alarm is playing and don't play before the first wave
        if (zombieAlarm.playing() || gamePlayTime < 10000) {
            music.pause();
        } else if (!music.playing()) {
            zombieAlarm.stop();
            music.resume();
        }

        //every update we have to handle the input from the player
        playerController.handleInput(container.getInput(), delta);

        //Pause timers when we pause the game or die
        if (!paused && !gameOver) {
            gamePlayTime += delta;
            gunShootTime -= delta;
            zombieSpawnTimer -= delta;
            player.hurtTimer -= delta;
            zombieWave(delta);
        }

        //press P to spawn zombies
        if (spawnNew) {
            spawnZombie();
        }

        //this gets zombies off your case
        for (ZombieController controller: zombieControllers) {
            if (attackMe) {
                controller.handleWalk(player, level, delta);
            }
            else {
                controller.handleWalk(zombies.get(zombies.size() - 1), level, delta);
            }
        }

        //handle our physics
        physics.handlePhysics(level, delta);

        //handle our special terrains
        switch (Physics.checkTerrainCollision(player, level.getTiles())) {
            case "lava":
                player.damage(1);
                break;
            case "water":
                player.setSpeed(3);
                break;
            case "ammo":
                playerGuns[0].maxAmmo();
                playerGuns[1].maxAmmo();
                break;
            default:
                player.setSpeed(1);
                break;
        }

        //special terrain for zombies too
        for (Zombie zombie: zombies) {
            switch (Physics.checkTerrainCollision(zombie, level.getTiles())) {
                case "lava":
                    zombie.damage(1);
                    break;
                case "water":
                    zombie.setSpeed(3);
                    break;
                default:
                    zombie.setSpeed(1);
                    break;
            }
        }


        //take care of our bullets
        int k = 0;
        List<Integer> gone = new ArrayList<>();
        for (Bullet bullet: bullets) {

            //see if our bullets hit a zombie, and if so, hurt them
            Physics.checkBulletCollision(bullet,level.getTiles());
            for (Zombie zombie: zombies) {
                if (bullet.getBoundingShape().checkCollision(zombie.getBoundingShape())) {
                    zombie.damage(playerGun.getDamage());
                    bullet.damage(1);
                }
            }

            //kill the bullet if it's dead
            if (bullet.getHealth() < 1) {
                if (bullets.size() > k ) {
                    gone.add(k);
                }
                bullet.kill();
            }
            k++;

        }

        //remove bullets that hit stuff
        for (Integer i: gone) {
            if (bullets.size() > i) {
                bullets.set(i, bullets.get(bullets.size() - 1));
                bullets.remove(bullets.size() - 1);
            }
        }
    }

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {

        g.scale(Game.SCALE, Game.SCALE);

        //render the level
        level.render();

        //displays our dev stuff onscreen
        g.setFont(ttf);
        g.drawString("Health: " + player.getHealth(), 5, 15);
        g.drawString("Kills: " + killCount, 5, 27);
        g.drawString("Zombies remaining: " + (zombiesSpawned - killCount), 5, 39);
        g.drawString("Zombies spawn: " + spawnNew, 5, 51);
        g.drawString("Attack me: " + attackMe, 5, 63);
        g.drawString("Ammo: " + playerGun.getCurrentAmmo(), 5, 75);
        g.drawString("Clip: " + playerGun.getClip(), 5, 87);
        g.drawString("Wave: " + currentWave, 5, 99);

        //game over screen
        if (gameOver) {
            g.drawString("GAME OVER", containerWidth / 6, 20);
            g.drawString("Restart?", containerWidth / 6 + 10, 100);
        }

        //next wave screen
        if (zombieAlarm.playing() && zombieWaveTimer > 2000 && zombieWaveTimer < zombieWaveAlarm) {
            if (zombiesSpawned > 0) {
                g.drawString("Wave " + (currentWave + 1), containerWidth / 6, 100);
            }
            else {
                g.drawString("Wave " + currentWave, containerWidth / 6, 100);
            }
        }
        //KEEP THESE IN ORDER. HEALTH BAR COLORING DEPENDS ON IT
        g.setColor(new Color(52, 166, 163));
        g.draw(player.getHealthBaseRect());
        player.getHealthBar().draw(g, player);
        //COLOR IS WHITE BEYOND HERE
    }

    //this method is overriden from basicgamestate and will trigger once you press any key on your keyboard
    public void keyPressed(int key, char code){
        //if the key is escape, close our application
        if(key == Input.KEY_ESCAPE){
            //System.exit(0);
            game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));


        }
    }

    public int getID() {
        //this is the id for changing states
        return 1;
    }

    public static Point getMousePos() {
        return new Point(Mouse.getX(), Mouse.getY());
    }

    public static void spawnZombie() {
        Random rand = new Random();
        try {
            int x;
            int y;
            //spawn zombie randomly
            do {
                x = rand.nextInt(LevelState.containerWidth);
            } while (x > player.offsetx - containerWidth/2 && x < player.offsetx + containerWidth/2);
            do {
                y = rand.nextInt(LevelState.containerHeight);
            } while (y > player.offsety - containerHeight/2 && y < player.offsety + containerHeight/2);
            zombies.add(new Zombie(x, y));

            //if our zombie hits something, kill it and try again
            if (Physics.checkCollision(zombies.get(zombies.size() - 1), level.getTiles())
                    && Physics.checkTerrainCollision(zombies.get(zombies.size() - 1), level.getTiles()).equals("false")) {
                zombies.remove(zombies.size() - 1);
            } else {
                zombiesSpawned++;
                LevelState.level.addCharacter(zombies.get(zombies.size() - 1));
                zombieControllers.add(new ZombieController(zombies.get(zombies.size() - 1)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void zombieWave(int delta) {

        int zombiesPerWave = 10;
        //if we have more to spawn this wave, let's do it
        if (zombiesSpawnedThisWave < (currentWave * zombiesPerWave - 1) && zombieSpawnTimer <= 0) {

            //get the number of zombies spawned this wave and then spawn one
            zombiesSpawnedThisWave = zombiesSpawned - zombiesBeforeThisWave;
            spawnZombie();

            //we need to reset these timers
            zombieSpawnTimer = zombieSpawnDelay;
            zombieWaveTimer = zombieWaveDelay;
        }

        //if we're out of zombies to kill, prepare for next wave
        else if (zombiesSpawned - killCount == 0) {
            zombieWaveTimer -= delta;

            //this plays our alarm before a wave starts
            if (zombieWaveTimer <= zombieWaveAlarm && !zombieAlarm.playing()) { //&& !waveStarted) {
                zombieAlarm.play();
            }

            //start new wave
            if (zombieWaveTimer <= 0) {
                currentWave++;
                zombiesSpawnedThisWave = 0;
                zombiesBeforeThisWave = zombiesSpawned;
                if (zombieSpawnDelay >= 200) {
                    zombieSpawnDelay -= currentWave * 50;
                }
                zombieWaveTimer = zombieWaveDelay;
            }
        }

    }

    //restarts the game (@game over)
    public static void restart() {
        paused = false;
        gameOver = false;
        killCount = 0;
        for (Zombie zombie: zombies) {
            zombie.setHealth(0);
        }
        zombieControllers.clear();
        zombies.clear();
        bullets.clear();
        spawnNew = false;
        attackMe = true;
        zombiesSpawned = 0;
        gunShootTime = 100;
        gamePlayTime = 0;
        currentWave = startingWave;
        zombiesBeforeThisWave = 0;
        zombieSpawnDelay = 5000 - (currentWave*50);
        zombiesSpawnedThisWave = 0;
        zombieWaveTimer = 18000;
        zombieSpawnTimer = zombieWaveTimer;
        player.reset();
        player.setX(playerX);
        player.setY(playerY);
        Pistol pistol = new Pistol();
        Rifle rifle = new Rifle();
        playerGuns[0] = pistol;
        playerGuns[1] = rifle;
        playerGun = playerGuns[0];
        music = openingMenuMusic;
        music.loop();
    }
}