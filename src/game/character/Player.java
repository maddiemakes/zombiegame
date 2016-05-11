package game.character;

import game.enums.Facing;
import game.physics.AABoundingRect;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Character {

    public Player(float x, float y) throws SlickException {
        super(x, y);
//        setSprite(new Image("data/images/player_down_1.png"));
        setSprite("data/images/player/player");
//        setMovingAnimation(new Image[]{new Image("data/img/characters/player/player_1.png"),new Image("data/img/characters/player/player_2.png"),
//                        new Image("data/img/characters/player/player_3.png"),new Image("data/img/characters/player/player_4.png")}
//                ,125);
        setMovingAnimation("data/images/player/player", 3, 100);

        boundingShape = new AABoundingRect(x + 3, y, 26, 32);

        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+3,y);
    }


    public void moveUpLeft(int delta) {
        x = x - (0.15f*delta);
        y = y - (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveUpRight(int delta) {
        x = x + (0.15f*delta);
        y = y - (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveDownLeft(int delta) {
        x = x - (0.15f*delta);
        y = y + (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }

    public void moveDownRight(int delta) {
        x = x + (0.15f*delta);
        y = y + (0.15f*delta);
        lastTimeMoved = System.currentTimeMillis();
    }
}