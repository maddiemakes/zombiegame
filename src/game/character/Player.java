package game.character;

import game.physics.AABoundingRect;
import org.newdawn.slick.SlickException;

public class Player extends Character {

    public Player(float x, float y) throws SlickException {
        super(x, y);
        setSprite("data/images/player/player");
        setMovingAnimation("data/images/player/player", 3, 100);

        //TODO move these around to fix the box to fit to the character sprite
        boundingShape = new AABoundingRect(x+6, y+6, 18, 26);

        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+6,y+6);
    }


}