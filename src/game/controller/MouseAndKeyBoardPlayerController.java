package game.controller;

import game.character.Player;

import game.character.Zombie;
import game.level.Level;
import game.state.LevelState;
import game.weapons.Bullet;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.Random;

import static game.state.LevelState.*;

public class MouseAndKeyBoardPlayerController extends PlayerController {

    private int shot;

    public MouseAndKeyBoardPlayerController(Player player) {
        super(player);
    }

    public void handleInput(Input i, int delta) {
        //handle any input from the keyboard
        handleKeyboardInput(i,delta);
        //TODO
        player.setMouseQuadrant();
    }

    private void handleKeyboardInput(Input i, int delta){
        //we can both use the WASD or arrow keys to move around, obviously we can't move both left and right simultaneously
        //controls when not dead
        if (player.getHealth() > 0) {
            if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
                if (i.isKeyDown(Input.KEY_W) || i.isKeyDown(Input.KEY_UP)) {
                    player.moveUpLeft(delta);
                } else if (i.isKeyDown(Input.KEY_S) || i.isKeyDown(Input.KEY_DOWN)) {
                    player.moveDownLeft(delta);
                } else {
                    player.moveLeft(delta);
                }
            } else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
                if (i.isKeyDown(Input.KEY_W) || i.isKeyDown(Input.KEY_UP)) {
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
            } else {
                //we don't move if we don't press left or right, this will have the effect that our player decelerates
                player.setMoving(false);
                player.setXVelocity(0);
                player.setYVelocity(0);
            }

            if (i.isKeyPressed(Input.KEY_R)) {
                playerGun.reload();
            }

            if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                if ((shot > 0 || playerGun.getSemi() == 0) && playerGun.getClip() > 0) {
                    playerGun.shoot(delta);
                    shot--;
                }
                else if (playerGun.getClip() <= 0 ) {
                    playerGun.reload();
                }
            }

            if (!(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))) {
                shot = playerGun.getSemi();
            }

            if (i.isKeyPressed(Input.KEY_Q)) {
                if (playerGun == playerGuns[0]) {
                    playerGun = playerGuns[1];
                } else {
                    playerGun = playerGuns[0];
                }
            }

            if (i.isKeyPressed(Input.KEY_I)) {
                player.invincible = !player.invincible;
            }
        }
        //else if we're dead, stop us from moving
        else {
            //we don't move if we don't press left or right, this will have the effect that our player decelerates
            player.setMoving(false);
            player.setXVelocity(0);
            player.setYVelocity(0);
        }

        //everything below here is a menu key
        if(i.isKeyPressed(Input.KEY_Z)) {
            LevelState.spawnZombie();
        }

        if (i.isKeyPressed(Input.KEY_P)) {
            LevelState.spawnNew = !LevelState.spawnNew;
        }

        if (i.isKeyPressed(Input.KEY_O)) {
            LevelState.attackMe = !LevelState.attackMe;
        }

    }

}
