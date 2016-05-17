package game.state;

import game.Game;
import game.character.Player;
import game.character.Zombie;
import game.controller.MouseAndKeyBoardPlayerController;
import game.controller.PlayerController;
import game.controller.ZombieController;
import game.enums.Facing;
import game.level.Level;
import game.level.tile.Tile;
import game.physics.Physics;

import game.weapons.Bullet;
import game.weapons.Gun;
import game.weapons.Pistol;
import javafx.util.Pair;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LevelState extends BasicGameState {

    public static Level  level;
    public static int killCount = 0;
    private String startinglevel;
    public static Player player;
    public static Gun playerGun;
    public static int containerHeight;
    public static int containerWidth;
    private PlayerController playerController;
    public static List<Zombie> zombies = new ArrayList<>();
    public static List<ZombieController> zombieControllers = new ArrayList<>();
    public static List<Bullet> bullets = new ArrayList<>();
    private Physics physics = new Physics();
    public static boolean spawnNew = false;
    public static boolean attackMe = false;
    public static int zombiesSpawned;
    private Font font = new Font("Verdana", Font.BOLD, 10);
    private TrueTypeFont ttf = new TrueTypeFont(font, true);
    public static List<String> spriteList = new ArrayList<>();
    public static List<Pair<String, HashMap<Facing,Image>>> spritesMaps = new ArrayList<>();
    public static List<String> animationList = new ArrayList<>();
    public static List<Pair<String, HashMap<Facing,Animation>>> animationMaps = new ArrayList<>();


    public LevelState(String startingLevel){
        this.startinglevel = startingLevel;
    }

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

        //at the start of the game we don't have a player yet
        player = new Player(228,150);
        Pistol pistol = new Pistol();
        playerGun = pistol;


        containerHeight = container.getHeight();
        containerWidth = container.getWidth();

        //once we initialize our level, we want to load the right level
        level = new Level(startinglevel, player);

        //and we create a controller, for now we use the MouseAndKeyBoardPlayerController
        playerController = new MouseAndKeyBoardPlayerController(player);
    }

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        //every update we have to handle the input from the player
        playerController.handleInput(container.getInput(), delta);

        //press P to spawn zombies
        if (spawnNew) {
            Random rand = new Random();
            try {
                int x;
                int y;
                do {
                    x = rand.nextInt(LevelState.containerWidth);
                } while (x > player.offsetx - 640 && x < player.offsetx + 640);
                do {
                    y = rand.nextInt(LevelState.containerHeight);
                } while (y > player.offsety - 360 && y < player.offsety + 360);
                zombies.add(new Zombie(x, y));
                if (Physics.checkCollision(zombies.get(zombies.size() - 1), level.getTiles())
                        || Physics.checkTerrainCollision(zombies.get(zombies.size() - 1), level.getTiles()).equals("false")) {
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

        //this gets zombies off your case
        for (ZombieController controller: zombieControllers) {
            if (attackMe) {
                controller.handleWalk(player, level, delta);
            }
            else {
                controller.handleWalk(zombies.get(zombies.size() - 1), level, delta);
            }
        }

        physics.handlePhysics(level, delta);

        switch (Physics.checkTerrainCollision(player, level.getTiles())) {
            case "lava":
                player.damage(1);
                break;
            case "water":
//                player.setXVelocity(player.getXVelocity()/3);
//                player.setYVelocity(player.getYVelocity()/3);
                player.setSpeed(3);
                break;
            default:
                player.setSpeed(1);
                break;
        }

        for (Zombie zombie: zombies) {
            switch (Physics.checkTerrainCollision(zombie, level.getTiles())) {
                case "lava":
                    zombie.damage(1);
                    break;
                case "water":
//                    zombie.setXVelocity(zombie.getXVelocity()/3);
//                    zombie.setYVelocity(zombie.getYVelocity()/3);
                    zombie.setSpeed(3);
                    break;
                default:
                    zombie.setSpeed(1);
                    break;
            }
        }


        //make arraylist of bullets
        //every time bullet hits stuff, we hurt it
        //when bullet is dead, we kill it
        int k = 0;
        List<Integer> gone = new ArrayList<>();
        for (Bullet bullet: bullets) {
//            bullet.render(player.getX(), player.getY());
            Physics.checkBulletCollision(bullet,level.getTiles());
            for (Zombie zombie: zombies) {
                if (bullet.getBoundingShape().checkCollision(zombie.getBoundingShape())) {
                    zombie.damage(playerGun.getDamage());
                    bullet.damage(1);
                }
            }

            if (bullet.getHealth() < 1) {
                if (bullets.size() > k ) {
                    gone.add(k);
                }
                bullet.kill();
            }
            k++;
        }
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
        g.setFont(ttf);
        g.drawString("Health: " + player.getHealth(), 5, 15);
        g.drawString("Kills: " + killCount, 5, 27);
        g.drawString("Zombies remaining: " + (zombiesSpawned - killCount), 5, 39);
        g.drawString("Zombies spawn: " + spawnNew, 5, 51);
        g.drawString("Attack me: " + attackMe, 5, 63);
    }

    //this method is overriden from basicgamestate and will trigger once you press any key on your keyboard
    public void keyPressed(int key, char code){
        //if the key is escape, close our application
        if(key == Input.KEY_ESCAPE){
            System.exit(0);
        }
    }

    public int getID() {
        //this is the id for changing states
        return 0;
    }

    public static Point getMousePos() {
        return new Point(Mouse.getX(), Mouse.getY());
    }
}