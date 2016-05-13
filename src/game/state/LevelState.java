package game.state;

import game.Game;
import game.character.Player;
import game.character.Zombie;
import game.controller.MouseAndKeyBoardPlayerController;
import game.controller.PlayerController;
import game.controller.ZombieController;
import game.level.Level;
import game.physics.Physics;

import game.weapons.Bullet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelState extends BasicGameState {

    public static Level  level;
    private String startinglevel;
    public static Player player;
    public static int containerHeight;
    public static int containerWidth;
    private PlayerController playerController;
    public static List<Zombie> zombies = new ArrayList<>();
    public static List<ZombieController> zombieControllers = new ArrayList<>();
    public static List<Bullet> bullets = new ArrayList<>();
    private Physics physics = new Physics();
    public static boolean spawnNew = false;
    public static boolean attackMe = false;


    public LevelState(String startingLevel){
        this.startinglevel = startingLevel;
    }

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

        //at the start of the game we don't have a player yet
        player = new Player(228,150);

        containerHeight = container.getHeight();
        containerWidth = container.getWidth();

        //once we initialize our level, we want to load the right level
        level = new Level(startinglevel, player);

        //and we create a controller, for now we use the MouseAndKeyBoardPlayerController
        playerController = new MouseAndKeyBoardPlayerController(player);
    }

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        //every update we have to handle the input from the player
        if (spawnNew) {
            Random rand = new Random();
            try {
                zombies.add(new Zombie(rand.nextInt(LevelState.containerHeight), rand.nextInt(LevelState.containerWidth)));
            } catch (SlickException e) {
                e.printStackTrace();
            }
            LevelState.level.addCharacter(zombies.get(zombies.size() - 1));
            zombieControllers.add(new ZombieController(zombies.get(zombies.size() - 1)));
            System.out.println("Zombie spawned.");
        }

        playerController.handleInput(container.getInput(), delta);
        for (ZombieController controller: zombieControllers) {
            if (attackMe) {
                controller.handleWalk(player, level, delta);
            }
            else {
                controller.handleWalk(zombies.get(zombies.size() - 1), level, delta);
            }
        }
        physics.handlePhysics(level, delta);

        //make arraylist of bullets
        //every time a bullet reaches x or y of 0, we delete it
        int k = 0;
        List<Integer> gone = new ArrayList<>();
        for (Bullet bullet: bullets) {
            bullet.render(player.getX(), player.getY());
            for (Zombie zombie: zombies) {
                if (bullet.getX() >= zombie.getX() && bullet.getX() <= zombie.getX() + 10 && bullet.getY() >= zombie.getY() && bullet.getY() <= zombie.getY() +10) {
                    zombie.damage(1);
                    System.out.println("Health: " + zombie.getHealth());
                }
            }
            if (bullet.getX() < 16 || bullet.getY() < 16) {
                gone.add(k);
            }
            k++;
        }
        for (Integer i: gone) {
            bullets.set(i, bullets.get(bullets.size() - 1));
            bullets.remove(bullets.size() - 1);
        }
    }

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(Game.SCALE, Game.SCALE);
        //render the level
        level.render();
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

}