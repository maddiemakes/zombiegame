package game.controller;

import game.character.Player;

import game.settings.SettingsGame;
import game.state.LevelState;
import org.newdawn.slick.Input;

import java.util.Set;

import static game.settings.SettingsControls.*;
import static game.state.LevelState.*;

public class MouseAndKeyBoardPlayerController extends PlayerController {

    private int shot;

    public MouseAndKeyBoardPlayerController(Player player) {
        super(player);
    }

    public void handleInput(Input i, int delta) {
        //handle any input from the keyboard
        handleKeyboardInput(i,delta);
        player.setMouseQuadrant();
    }

    private void handleKeyboardInput(Input i, int delta){
        //we can both use the WASD or arrow keys to move around, obviously we can't move both left and right simultaneously
        //controls when not dead
        if (player.getHealth() > 0) {

            //move player around
            if (i.isKeyDown(walkLeft1) || i.isKeyDown(walkLeft2)) {
                if (i.isKeyDown(walkUp1) || i.isKeyDown(walkUp2)) {
                    player.moveUpLeft(delta);
                } else if (i.isKeyDown(walkDown1) || i.isKeyDown(walkDown2)) {
                    player.moveDownLeft(delta);
                } else {
                    player.moveLeft(delta);
                }
            } else if (i.isKeyDown(walkRight1) || i.isKeyDown(walkRight2)) {
                if (i.isKeyDown(walkUp1) || i.isKeyDown(walkUp2)) {
                    player.moveUpRight(delta);
                } else if (i.isKeyDown(walkDown1) || i.isKeyDown(walkDown2)) {
                    player.moveDownRight(delta);
                } else {
                    player.moveRight(delta);
                }
            } else if (i.isKeyDown(walkUp1) || i.isKeyDown(walkUp2)) {
                player.moveUp(delta);
            } else if (i.isKeyDown(walkDown1) || i.isKeyDown(walkDown2)) {
                player.moveDown(delta);
            } else {
                //we don't move if we don't press left or right, this will have the effect that our player decelerates
                player.setMoving(false);
                player.setXVelocity(0);
                player.setYVelocity(0);
            }

            //reload
            if (i.isKeyPressed(reload)) {
                playerGun.reload();
            }

            //shoot when we click
            if (i.isMouseButtonDown(shoot)) {
                if ((shot > 0 || playerGun.getSemi() == 0) && playerGun.getClip() > 0) {
                    playerGun.shoot(delta);
                    shot--;
                }
                else if (playerGun.getClip() <= 0 ) {
                    playerGun.reload();
                }
            }

            //this is for controlling weapon fire (semi/automatic/burst)
            if (!(i.isMouseButtonDown(shoot))) {
                shot = playerGun.getSemi();
            }

            //swap weapons
            if (i.isKeyPressed(weaponSwap)) {
                playerGun.swapGuns();
            }

            if (SettingsGame.devSettings) {
                System.out.println("dev?");
                //invincibility!
                if (i.isKeyPressed(invincibility)) {
                    System.out.println("invincible");
                    player.invincible = !player.invincible;
                }
            }
        }
        //else if we're dead, stop us from moving
        else {
            //we don't move if we don't press left or right, this will have the effect that our player decelerates
            player.setMoving(false);
            player.setXVelocity(0);
            player.setYVelocity(0);

            //restart the game when we click restart
            if (gameOver) {
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    double mouseX = LevelState.getMousePos().getX();
                    double mouseY = LevelState.getMousePos().getY();

                    if (mouseX >= 555 && mouseX <= 680 && mouseY >= 441 && mouseY <= 465) {
                        LevelState.restart();
                    }

                }
            }
        }

        if (i.isKeyPressed(mute)) {
            SettingsGame.mute();
        }

        if (i.isKeyPressed(devSettings)) {
            SettingsGame.devSettings = !SettingsGame.devSettings;
        }

        if (SettingsGame.devSettings) {
            //restart the game
            if (i.isKeyPressed(restart)) {
                LevelState.restart();
            }

            //spawn a zombie
            if (i.isKeyPressed(spawnZombie)) {
                LevelState.spawnZombie();
            }

            //toggle zombies spawning as fast as they can
            if (i.isKeyPressed(apocalypse)) {
                LevelState.spawnNew = !LevelState.spawnNew;
            }

            //toggle zombies attacking you
            if (i.isKeyPressed(invisibility)) {
                LevelState.attackMe = !LevelState.attackMe;
            }
        }

    }

}
