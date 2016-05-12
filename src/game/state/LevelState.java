package game.state;

import game.Game;
import game.character.Player;
import game.character.Zombie;
import game.controller.MouseAndKeyBoardPlayerController;
import game.controller.PlayerController;
import game.controller.ZombieController;
import game.level.Level;
import game.physics.Physics;

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
    private Player player;
    public static int containerHeight;
    public static int containerWidth;
    private PlayerController playerController;
    public static List<Zombie> zombies = new ArrayList<>();
    public static List<ZombieController> zombieControllers = new ArrayList<>();
    private Physics physics = new Physics();


    public LevelState(String startingLevel){
        this.startinglevel = startingLevel;
    }

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

        //at the start of the game we don't have a player yet
        player = new Player(228,150);
//        zombie = new Zombie(532,184);
//        for (int k=0; k < 5; k++) {
//            Random rand = new Random();
//            zombies.add(new Zombie(rand.nextInt(container.getHeight()), rand.nextInt(container.getWidth())));
//            zombieControllers.add(new ZombieController(zombies.get(k)));
//        }
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
        for (ZombieController controller: zombieControllers) {
            controller.handleWalk(player, level, delta);
        }
        physics.handlePhysics(level, delta);
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