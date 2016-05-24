package game.physics;

import game.character.Player;
import game.character.Zombie;
import game.level.Level;
import game.level.LevelObject;
import game.level.object.AmmoPickup;
import game.level.object.HealthPickup;
import game.level.tile.*;
import game.character.Character;
import game.state.LevelState;
import game.weapons.Bullet;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

import static game.state.LevelState.*;

public class Physics {

    public void handlePhysics(Level level, int delta){
        handleCharacters(level,delta);
        handleLevelObjects(level,delta);
    }

    public static boolean checkCollision(LevelObject obj, Tile[][] mapTiles){
        //get only the tiles that matter
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            //if this tile has a bounding shape
            if(t.getBoundingShape() != null){
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())){
                    //if it's a special terrain tile, let us go through it
                    if (obj instanceof Character) {
                        if (t instanceof WasteTile || t instanceof WaterTile) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static String checkTerrainCollision(Character obj, Tile[][] mapTiles) {
        //get only the tiles that matter
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            //if this tile has a bounding shape then it's not air
            if(t.getBoundingShape() != null){
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())){
                    //return names of special tiles
                    if (t instanceof WasteTile) {
                        return "waste";
                    }
                    if (t instanceof WaterTile) {
                        return "water";
                    }
//                    if (t instanceof AmmoTile) {
//                        return "ammo";
//                    }
                }
            }
        }
        return "false";
    }

    //checks if bullets hit things
    public static void checkBulletCollision(Bullet bullet, Tile[][] mapTiles){
        //get only the tiles that matter
        ArrayList<Tile> tiles = bullet.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            //if this tile has a bounding shape
            //stops bullets from going offscreen
//            if (t.getClass().toString().equals("class game.level.tile.BorderTile")) {
            if (t instanceof BorderTile) {
                bullet.setHealth(0);
            }
        }
    }

    //not used yet
    public static boolean checkZombieCollision(Character zombie, Character zombie2) {
//        if (zombie.getType().equals("zombie") && zombie2.getType().equals("zombie")) {
        if (zombie instanceof Zombie && zombie2 instanceof Zombie) {
            if (zombie.getBoundingShape().checkCollision(zombie2.getBoundingShape())) {
                return true;
            }
//            for (Zombie zombie : list) {
//                if (zombie.getBoundingShape().checkCollision(obj.getBoundingShape())) {
//                    return true;
//                }
//            }
        }
        return false;
    }

    private void handleLevelObjects(Level level, int delta) {
        ArrayList<LevelObject> removeQueue = new ArrayList<>();
        for (LevelObject obj : level.getLevelObjects()) {
            handleGameObject(obj, level, delta);
            obj.setTimer(delta);
            if (obj.getTimer() < 0) {
                removeQueue.add(obj);
            }
        }
        level.removeObjects(removeQueue);
    }

    private void handleCharacters(Level level, int delta){
        for(Character c : level.getCharacters()){
            handleGameObject(c,level,delta);
            handleHealthCheck(c);

            //special cases for zombies
            if (c instanceof Zombie) {
                //special terrain check for zombies
                switch (checkTerrainCollision(c, level.getTiles())) {
                    case "water":
                        c.setSpeed(2);
                        break;
                    default:
                        c.setSpeed(1);
                        break;
                }
            }

            //special cases for the player
            if(c instanceof Player){

                //special terrain check
                switch (checkTerrainCollision(c, level.getTiles())) {
                    case "waste":
                        c.damage(1);
                        break;
                    case "water":
                        c.setSpeed(3);
                        break;
                    default:
                        c.setSpeed(1);
                        break;
                }

                ArrayList<LevelObject> removeQueue = new ArrayList<LevelObject>();

                //we have to check if he collides with anything special, such as objectives for example
                for(LevelObject obj : level.getLevelObjects()){

                    //if we walk over ammo
                    if(obj instanceof AmmoPickup){
                        //if it collides
                        if(obj.getBoundingShape().checkCollision(c.getBoundingShape())){
                            //give max ammo
                            playerGuns[0].maxAmmo();
                            playerGuns[1].maxAmmo();
                            //remove the object from the level
                            removeQueue.add(obj);
                        }
                    }

                    //if we walk over health
                    if (obj instanceof HealthPickup) {
                        if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
                            //add 50% of the maximum health to the player
                            c.setHealth((int) c.getHealth() + (int) (c.getMaxHealth() * 0.5));
                            if (c.getHealth() > c.getMaxHealth()) {
                                c.setHealth((int)c.getMaxHealth());
                                ((Player) c).healthBar.setHealthBar((Player)c);
                            }
                            //remove the object from the level
                            removeQueue.add(obj);
                        }
                    }
                }

                level.removeObjects(removeQueue);
            }
        }
    }

    //kills things that are dead
    private void handleHealthCheck(Character c) {
        if (c.getHealth() <= 0) {
            //kills zombies
            if (c instanceof Zombie) {
                Integer k = 0;
                List<Integer> dead = new ArrayList<>();
                for (Zombie zombie: zombies) {
                    if (zombie.getHealth() <= 0) {
                        dead.add(k);
                        try {
                            zombie.drop();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    }
                    k++;
                }
                for (Integer i: dead) {
                    if (zombies.size() > i && zombieControllers.size() > i) {
                        zombies.get(zombies.size()-1).playSound(LevelState.zombieDeath);

                        LevelState.killCount++;

                        zombies.set(i, zombies.get(zombies.size() - 1));
                        zombies.remove(zombies.size() - 1);

                        zombieControllers.set(i, zombieControllers.get(zombieControllers.size() - 1));
                        zombieControllers.remove(zombieControllers.size() - 1);
                    }
                }
                dead.clear();
            }

            //kills player and triggers game over
            else if (c instanceof Player && !gameOver) {
                music = gameOverMusic;
                music.loop();
                gameOver = true;
            }
        }
    }

    private void handleGameObject(LevelObject obj, Level level, int delta){

        //calculate how much we actually have to move
        float x_movement = obj.getXVelocity()*delta;
        float y_movement = obj.getYVelocity()*delta;

        //we have to calculate the step we have to take
        float step_y = 0;
        float step_x = 0;

        if(x_movement != 0){
            if(x_movement > 0)
                step_x = 1;
            else
                step_x = -1;
        }
        if(y_movement != 0) {
            if (y_movement > 0)
                step_y = 1;
            else
                step_y = -1;
        }

        //and then do little steps until we are done moving
        while(x_movement != 0 || y_movement != 0){

            //we first move in the x direction
            if(x_movement != 0){
                //when we do a step, we have to update the amount we have to move after this
                if((x_movement > 0 && x_movement < step_x) || (x_movement > step_x  && x_movement < 0)){
                    step_x = x_movement;
                    x_movement = 0;
                }else
                    x_movement -= step_x;

                //then we move the object one step
                obj.setX(obj.getX()+step_x);

                //if we collide with any of the bounding shapes of the tiles we have to revert to our original position
                if(checkCollision(obj,level.getTiles())){

                    //undo our step, and set the velocity and amount we still have to move to 0, because we can't move in that direction
                    obj.setX(obj.getX()-step_x);
                    obj.setXVelocity(0);
                    x_movement = 0;
                }

                //TODO
                //zombies don't walk perfectly on top of each other, but this also breaks them to where they get stuck together
//                if (checkZombieCollision(obj, zombies)) {
//                    obj.setX(obj.getX()-step_x);
//                    obj.setXVelocity(0);
//                    x_movement = 0;
//                }

            }
            //same thing for the vertical, or y movement
            if(y_movement != 0){
                if((y_movement > 0 && y_movement < step_y) || (y_movement > step_y  && y_movement < 0)){
                    step_y = y_movement;
                    y_movement = 0;
                }else
                    y_movement -= step_y;

                obj.setY(obj.getY()+step_y);

                if(checkCollision(obj,level.getTiles())){
                    obj.setY(obj.getY()-step_y);
                    obj.setYVelocity(0);
                    y_movement = 0;
                    break;
                }
            }
        }
    }

}