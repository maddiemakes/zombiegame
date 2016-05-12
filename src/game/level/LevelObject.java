package game.level;

import game.physics.AABoundingRect;
import game.physics.BoundingShape;

public abstract class LevelObject {

    protected float x;
    protected float y;
    protected BoundingShape boundingShape;

    protected float    x_velocity = 0;
    protected float    y_velocity = 0;
    protected float    maximumFallSpeed = 1;

//    protected boolean  onGround = true;

    public LevelObject(float x, float y){
        this.x = x;
        this.y = y;

        //default bounding shape is a 16 by 16 box
        boundingShape = new AABoundingRect(x,y,16,16);
    }

    public float getYVelocity() {
        return y_velocity;
    }

    public void setYVelocity(float f){
        y_velocity = f;
    }

    public float getXVelocity(){
        return x_velocity;
    }

    public void setXVelocity(float f){
        x_velocity = f;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float f){
        x = f;
        updateBoundingShape();
    }

    public void setY(float f){
        y = f;
        updateBoundingShape();
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x,y);
    }

    public BoundingShape getBoundingShape(){
        return boundingShape;
    }

}