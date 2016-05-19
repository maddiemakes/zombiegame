package game.character;

import game.enums.Facing;
import game.level.LevelObject;
import game.state.LevelState;
import javafx.util.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public abstract class Character extends LevelObject {

    protected Facing facing;
    public HashMap<Facing,Image> sprites;
    public HashMap<Facing,Animation> movingAnimations;
    protected boolean moving = false;
    protected float originalMaxSpeed = 1;
    protected float originalDiagonalSpeed = 1;
    protected float maximumSpeed = 1;
    protected float diagonalSpeed = 1;
    protected int health = 10;
    public float offsetx;
    public float offsety;

    public Character(float x, float y) throws SlickException {
        super(x,y);
        //in case we forget to set the image, we don't want the game to crash, but it still has to be obvious that something was forgotten
        facing = Facing.DOWN;
        setSprite("data/images/placeholder/placeholder");
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void render(float offset_x, float offset_y) throws SlickException {

        offsetx = offset_x;
        offsety = offset_y;

        //draw a moving animation if we have one and we moved within the last 150 milliseconds
        if (health > 0) {
            if (movingAnimations != null && moving) {
                movingAnimations.get(facing).draw(x - 2 - offset_x, y - 2 - offset_y);
            } else {
                sprites.get(facing).draw(x - 2 - offset_x, y - 2 - offset_y);
            }
        }
    }

    protected void setSprite(String i) throws SlickException {

        //checks to see if we've already made these images before
        // this is useful in the case of zombies,
        // where we don't need to make new images for every zombie
        boolean exists = false;
        for (String name: LevelState.spriteList) {
            if (name.equals(i)) {
                exists = true;
            }
        }

        //if it's new, let's make it
        if (!exists) {
            LevelState.spriteList.add(i);
            sprites = new HashMap<Facing, Image>();
            sprites.put(Facing.DOWN, new Image(i + "_down_1.png"));
            sprites.put(Facing.UP, new Image(i + "_up_1.png"));
            sprites.put(Facing.LEFT, new Image(i + "_left_1.png"));
            sprites.put(Facing.RIGHT, new Image(i + "_right_1.png"));
            LevelState.spritesMaps.add(new Pair<>(i,sprites));
            System.out.println("Added " + i);
        }

        //if it's not new, let's find the old one
        else {
            for (Pair<String, HashMap<Facing,Image>> pair: LevelState.spritesMaps) {
                if (pair.getKey().equals(i)) {
                    sprites = pair.getValue();
                }
            }
        }
    }

    protected void setMovingAnimation(String url, int images, int frameDuration) throws SlickException {
        boolean exists = false;
        for (String name: LevelState.animationList) {
            if (name.equals(url)) {
                exists = true;
            }
        }

        if (!exists) {
            LevelState.animationList.add(url);

            movingAnimations = new HashMap<>();

            Animation facingDownAnimation = new Animation();
            Animation facingUpAnimation = new Animation();
            Animation facingRightAnimation = new Animation();
            Animation facingLeftAnimation = new Animation();
            for (int k = 2; k < images + 1; k++) {
                facingDownAnimation.addFrame(new Image(url + "_down_" + k + ".png"), frameDuration);
                facingUpAnimation.addFrame(new Image(url + "_up_" + k + ".png"), frameDuration);
                facingRightAnimation.addFrame(new Image(url + "_right_" + k + ".png"), frameDuration);
                facingLeftAnimation.addFrame(new Image(url + "_left_" + k + ".png"), frameDuration);
            }

            movingAnimations.put(Facing.DOWN, facingDownAnimation);
            movingAnimations.put(Facing.UP, facingUpAnimation);
            movingAnimations.put(Facing.RIGHT, facingRightAnimation);
            movingAnimations.put(Facing.LEFT, facingLeftAnimation);
            LevelState.animationMaps.add(new Pair<>(url, movingAnimations));
            System.out.println("Added " + url);
        }

        else {
            for (Pair<String, HashMap<Facing,Animation>> pair: LevelState.animationMaps) {
                if (pair.getKey().equals(url)) {
                    movingAnimations = pair.getValue();
                }
            }
        }
    }

    public boolean isMoving(){
        return moving;
    }

    public void setMoving(boolean b){
        moving = b;
    }

    public void moveLeft(int delta){
        x_velocity = -maximumSpeed;
        y_velocity = 0;
        moving = true;
        facing = Facing.LEFT;
    }

    public void moveRight(int delta){
        x_velocity = maximumSpeed;
        y_velocity = 0;
        moving = true;
        facing = Facing.RIGHT;
    }

    public void moveUp(int delta) {
        x_velocity = 0;
        y_velocity = -maximumSpeed;
        moving = true;
        facing = Facing.UP;
    }

    public void moveDown(int delta) {
        x_velocity = 0;
        y_velocity = maximumSpeed;
        moving = true;
        facing = Facing.DOWN;
    }

    public void moveUpLeft(int delta) {
        x_velocity = -diagonalSpeed;
        y_velocity = -diagonalSpeed;
        moving = true;
        facing = Facing.UP;
    }

    public void moveUpRight(int delta) {
        x_velocity = diagonalSpeed;
        y_velocity = -diagonalSpeed;
        moving = true;
        facing = Facing.UP;
    }

    public void moveDownLeft(int delta) {
        x_velocity = -diagonalSpeed;
        y_velocity = diagonalSpeed;
        moving = true;
        facing = Facing.DOWN;
    }

    public void moveDownRight(int delta) {
        x_velocity = diagonalSpeed;
        y_velocity = diagonalSpeed;
        moving = true;
        facing = Facing.DOWN;
    }

    public void damage(int damage) {
        health -= damage;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int i) {
        health = i;
    }

    public void setSpeed(int divisor) {
        maximumSpeed = originalMaxSpeed / divisor;
        diagonalSpeed = originalDiagonalSpeed / divisor;
    }
}