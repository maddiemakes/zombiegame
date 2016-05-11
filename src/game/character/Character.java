package game.character;

import game.enums.Facing;
import game.level.LevelObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public abstract class Character extends LevelObject {

    protected float x;
    protected float y;
    protected Facing facing;
    protected HashMap<Facing,Image> sprites;
    protected HashMap<Facing,Animation> movingAnimations;
    protected long lastTimeMoved;
    protected boolean                   moving = false;
    protected float                     accelerationSpeed = 1;
    protected float                     decelerationSpeed = 1;
    protected float                     maximumSpeed = 1;

    public Character(float x, float y) throws SlickException {
        super(x,y);
//        this.x = x;
//        this.y = y;
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

//    public void render(){
    public void render(float offset_x, float offset_y){

        //draw a moving animation if we have one and we moved within the last 150 milliseconds
        if(movingAnimations != null && lastTimeMoved+150 > System.currentTimeMillis()){
            movingAnimations.get(facing).draw(x-2-offset_x,y-2-offset_y);
        }else{
            sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);
        }
    }

//    public void render(float offset_x, float offset_y){

        //draw a moving animation if we have one and we moved within the last 150 miliseconds
//        if(movingAnimations != null && moving){
//            movingAnimations.get(facing).draw(x-2-offset_x,y-2-offset_y);
//        }else{
//            sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);
//        }
//    }

    protected void setSprite(String i) throws SlickException {
        sprites = new HashMap<Facing,Image>();
        sprites.put(Facing.DOWN, new Image(i + "_down_1.png"));
        sprites.put(Facing.UP, new Image(i + "_up_1.png"));
        sprites.put(Facing.LEFT , new Image(i + "_left_1.png"));
        sprites.put(Facing.RIGHT , new Image(i + "_right_1.png"));
    }

//    protected void setMovingAnimation(Image[] images, int frameDuration){
    protected void setMovingAnimation(String url, int images, int frameDuration) throws SlickException {
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
    }

    public boolean isMoving(){
        return moving;
    }

    public void setMoving(boolean b){
        moving = b;
    }

    //move towards x_velocity = 0
    public void decelerate(int delta) {
        if(x_velocity > 0){
            x_velocity -= decelerationSpeed * delta;
            if(x_velocity < 0)
                x_velocity = 0;
        }else if(x_velocity < 0){
            x_velocity += decelerationSpeed * delta;
            if(x_velocity > 0)
                x_velocity = 0;
        }
    }

    public void jump(){
        if(onGround)
            y_velocity = -0.4f;
    }

////    public void moveLeft(int delta){
////        //if we aren't already moving at maximum speed
////        if(x_velocity > -maximumSpeed){
////            //accelerate
////            x_velocity -= accelerationSpeed*delta;
////            if(x_velocity < -maximumSpeed){
////                //and if we exceed maximum speed, set it to maximum speed
////                x_velocity = -maximumSpeed;
////            }
////        }
////        moving = true;
////        facing = Facing.LEFT;
////    }
//
//    public void moveRight(int delta){
//        if(x_velocity < maximumSpeed){
//            x_velocity += accelerationSpeed*delta;
//            if(x_velocity > maximumSpeed){
//                x_velocity = maximumSpeed;
//            }
//        }
//        moving = true;
//        facing = Facing.RIGHT;
//    }
//
//    public void moveUp(int delta) {
//        if(y_velocity > -maximumSpeed) {
//            y_velocity -= accelerationSpeed*delta;
//            if (y_velocity < -maximumSpeed) {
//                y_velocity = -maximumSpeed;
//            }
//        }
//        moving = true;
//        facing = Facing.UP;
//    }
//
//    public void moveDown(int delta) {
//        if(y_velocity < maximumSpeed) {
//            y_velocity += accelerationSpeed*delta;
//            if (y_velocity > maximumSpeed) {
//                y_velocity = maximumSpeed;
//            }
//        }
//        moving = true;
//        facing = Facing.DOWN;
//    }

    public void moveLeft(int delta){
        facing = Facing.LEFT;
        x = x - (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveRight(int delta){
        facing = Facing.RIGHT;
        x = x + (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveUp(int delta) {
        facing = Facing.UP;
        y = y - (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveDown(int delta) {
        facing = Facing.DOWN;
        y = y + (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

}