package game.physics;

import game.character.Zombie;
import game.level.Level;
import game.level.LevelObject;
import game.level.tile.Tile;
import game.character.Character;
import game.state.LevelState;
import game.weapons.Bullet;

import java.util.ArrayList;
import java.util.List;

import static game.state.LevelState.zombieControllers;
import static game.state.LevelState.zombies;

public class Physics {

    public void handlePhysics(Level level, int delta){
        handleCharacters(level,delta);
    }

    public static boolean checkCollision(LevelObject obj, Tile[][] mapTiles){
        //get only the tiles that matter
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            //if this tile has a bounding shape
            if(t.getBoundingShape() != null){
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())){
                    if (obj.getClass().toString().startsWith("class game.character.")) {
                        if (t.getClass().toString().equals("class game.level.tile.LavaTile")
                                || t.getClass().toString().equals("class game.level.tile.WaterTile")) {
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
                    if (t.getClass().toString().equals("class game.level.tile.LavaTile")) {
                        return "lava";
                    }
                    if (t.getClass().toString().equals("class game.level.tile.WaterTile")) {
                        return "water";
                    }
                }
            }
        }
        return "false";
    }

    public static void checkBulletCollision(Bullet bullet, Tile[][] mapTiles){
        //get only the tiles that matter
        ArrayList<Tile> tiles = bullet.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            //if this tile has a bounding shape
            if (t.getClass().toString().equals("class game.level.tile.BorderTile")) {
                bullet.setHealth(0);
            }
        }
    }

    private boolean checkZombieCollision(LevelObject obj, List<Zombie> list) {
        if (obj.getType().equals("zombie")) {
            for (Zombie zombie : list) {
                if (zombie.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void handleCharacters(Level level, int delta){
        for(Character c : level.getCharacters()){
            handleGameObject(c,level,delta);
            handleHealthCheck(c);
        }
    }

    private void handleHealthCheck(Character c) {
        if (c.getHealth() <= 0) {
            if (c.type.equals("zombie")) {
                Integer k = 0;
                List<Integer> dead = new ArrayList<>();
                for (Zombie zombie: zombies) {
                    if (zombie.getHealth() <= 0) {
                        dead.add(k);
                    }
                    k++;
                }
                for (Integer i: dead) {
                    if (zombies.size() > i && zombieControllers.size() > i) {
                        LevelState.killCount++;

                        zombies.set(i, zombies.get(zombies.size() - 1));
                        zombies.remove(zombies.size() - 1);

                        zombieControllers.set(i, zombieControllers.get(zombieControllers.size() - 1));
                        zombieControllers.remove(zombieControllers.size() - 1);
                    }
                }
                dead.clear();
            }
        }
    }

    private void handleGameObject(LevelObject obj, Level level, int delta){

        //calculate how much we actually have to move
        float x_movement = obj.getXVelocity()*delta;
        float y_movement   = obj.getYVelocity()*delta;

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