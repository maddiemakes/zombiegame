package game.controller;

import game.character.Character;
import game.character.Zombie;
import game.level.Level;
import game.level.LevelObject;

public class ZombieController {
    //zombie will head towards another character
    //we pass the ZombieAI a character for it to head towards
    private Zombie zombie;

    public ZombieController(Zombie zombie){
        this.zombie = zombie;
    }

    public void handleWalk(LevelObject obj, Level level, int delta) {
        //get location of object
        //move zombie towards object
        //if zombie runs into "level" then go around
        float obj_x_location = obj.getX();
        float obj_y_location = obj.getY();

        float x_movement = zombie.getXVelocity()*delta;
        float y_movement = zombie.getYVelocity()*delta;
        float x_location = zombie.getX();
        float y_location = zombie.getY();

        //if y location isn't within X
        //  if y location < move up
        //  else move down
        //if x location isn't within X
        //  if x location > move right
        //  else move left

        if (obj_x_location > (x_location + 10)) {
            if (obj_y_location > y_location+10) {
                zombie.moveDownRight(delta);
            } else if (obj_y_location < y_location-10) {
                zombie.moveUpRight(delta);
            } else {
                zombie.moveRight(delta);
            }
        } else if (obj_x_location < (x_location - 10)) {
            if (obj_y_location > y_location+10) {
                zombie.moveDownLeft(delta);
            } else if (obj_y_location < y_location-10) {
                zombie.moveUpLeft(delta);
            } else {
                zombie.moveLeft(delta);
            }
        } else if (obj_y_location > y_location+10) {
            zombie.moveDown(delta);
        } else if (obj_y_location < y_location-10) {
            zombie.moveUp(delta);
        }

    }
}
