package game.controller;

import game.character.Player;

import game.character.Zombie;
import game.state.LevelState;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Random;

import static game.state.LevelState.zombies;
import static game.state.LevelState.zombieControllers;

public class MouseAndKeyBoardPlayerController extends PlayerController {

    public MouseAndKeyBoardPlayerController(Player player) {
        super(player);
    }

    public void handleInput(Input i, int delta) {
        //handle any input from the keyboard
        handleKeyboardInput(i,delta);
    }

    private void handleKeyboardInput(Input i, int delta){
        //we can both use the WASD or arrow keys to move around, obviously we can't move both left and right simultaneously
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)){
            if(i.isKeyDown(Input.KEY_W) || i.isKeyDown(Input.KEY_UP)) {
                player.moveUpLeft(delta);
            } else if (i.isKeyDown(Input.KEY_S) || i.isKeyDown(Input.KEY_DOWN)) {
                player.moveDownLeft(delta);
            } else {
                player.moveLeft(delta);
            }
        }else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)){
            if(i.isKeyDown(Input.KEY_W) || i.isKeyDown(Input.KEY_UP)) {
                player.moveUpRight(delta);
            } else if (i.isKeyDown(Input.KEY_S) || i.isKeyDown(Input.KEY_DOWN)) {
                player.moveDownRight(delta);
            } else {
                player.moveRight(delta);
            }
        } else if (i.isKeyDown(Input.KEY_W) || i.isKeyDown(Input.KEY_UP)) {
            player.moveUp(delta);
        } else if (i.isKeyDown(Input.KEY_S) || i.isKeyDown(Input.KEY_DOWN)) {
            player.moveDown(delta);
        }else{
            //we don't move if we don't press left or right, this will have the effect that our player decelerates
            player.setMoving(false);
            player.setXVelocity(0);
            player.setYVelocity(0);
        }

        if(i.isKeyPressed(Input.KEY_Z)) {
            Random rand = new Random();
            try {
                zombies.add(new Zombie(rand.nextInt(LevelState.containerHeight), rand.nextInt(LevelState.containerWidth)));
            } catch (SlickException e) {
                e.printStackTrace();
            }
            LevelState.level.addCharacter(zombies.get(zombies.size()-1));
            zombieControllers.add(new ZombieController(zombies.get(zombies.size()-1)));

        }
    }

}
