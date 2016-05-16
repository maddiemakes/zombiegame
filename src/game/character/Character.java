package game.character;

import game.enums.Facing;
import game.level.LevelObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public abstract class Character extends LevelObject {

    protected Facing facing;
    protected HashMap<Facing,Image> sprites;
    protected HashMap<Facing,Animation> movingAnimations;
    protected boolean moving = false;
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
        } else {
            movingAnimations.get(facing).stop();
            sprites.get(facing).destroy();
        }
    }

    protected void setSprite(String i) throws SlickException {
        sprites = new HashMap<Facing,Image>();
        sprites.put(Facing.DOWN, new Image(i + "_down_1.png"));
        sprites.put(Facing.DOWNLEFT, new Image(i + "_down_1.png"));
        sprites.put(Facing.DOWNRIGHT, new Image(i + "_down_1.png"));
        sprites.put(Facing.UP, new Image(i + "_up_1.png"));
        sprites.put(Facing.UPLEFT, new Image(i + "_up_1.png"));
        sprites.put(Facing.UPRIGHT, new Image(i + "_up_1.png"));
        sprites.put(Facing.LEFT , new Image(i + "_left_1.png"));
        sprites.put(Facing.RIGHT , new Image(i + "_right_1.png"));
    }

    protected void setMovingAnimation(String url, int images, int frameDuration) throws SlickException {
        movingAnimations = new HashMap<>();

        Animation facingDownAnimation = new Animation();
        Animation facingDownLeftAnimation = new Animation();
        Animation facingDownRightAnimation = new Animation();
        Animation facingUpAnimation = new Animation();
        Animation facingUpLeftAnimation = new Animation();
        Animation facingUpRightAnimation = new Animation();
        Animation facingRightAnimation = new Animation();
        Animation facingLeftAnimation = new Animation();
        for (int k = 2; k < images + 1; k++) {
            facingDownAnimation.addFrame(new Image(url + "_down_" + k + ".png"), frameDuration);
            facingDownLeftAnimation.addFrame(new Image(url + "_down_" + k + ".png"), frameDuration);
            facingDownRightAnimation.addFrame(new Image(url + "_down_" + k + ".png"), frameDuration);
            facingUpAnimation.addFrame(new Image(url + "_up_" + k + ".png"), frameDuration);
            facingUpLeftAnimation.addFrame(new Image(url + "_up_" + k + ".png"), frameDuration);
            facingUpRightAnimation.addFrame(new Image(url + "_up_" + k + ".png"), frameDuration);
            facingRightAnimation.addFrame(new Image(url + "_right_" + k + ".png"), frameDuration);
            facingLeftAnimation.addFrame(new Image(url + "_left_" + k + ".png"), frameDuration);
        }

        movingAnimations.put(Facing.DOWN, facingDownAnimation);
        movingAnimations.put(Facing.DOWNLEFT, facingDownLeftAnimation);
        movingAnimations.put(Facing.DOWNRIGHT, facingDownRightAnimation);
        movingAnimations.put(Facing.UP, facingUpAnimation);
        movingAnimations.put(Facing.UPLEFT, facingUpLeftAnimation);
        movingAnimations.put(Facing.UPRIGHT, facingUpRightAnimation);
        movingAnimations.put(Facing.RIGHT, facingRightAnimation);
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);
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
        facing = Facing.UPLEFT;
    }

    public void moveUpRight(int delta) {
        x_velocity = diagonalSpeed;
        y_velocity = -diagonalSpeed;
        moving = true;
        facing = Facing.UPRIGHT;
    }

    public void moveDownLeft(int delta) {
        x_velocity = -diagonalSpeed;
        y_velocity = diagonalSpeed;
        moving = true;
        facing = Facing.DOWNLEFT;
    }

    public void moveDownRight(int delta) {
        x_velocity = diagonalSpeed;
        y_velocity = diagonalSpeed;
        moving = true;
        facing = Facing.DOWNRIGHT;
    }

    public void damage(int damage) {
        health = health-damage;
    }

    public int getHealth() {
        return health;
    }

}