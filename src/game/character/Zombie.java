package game.character;

import game.physics.AABoundingRect;
import org.newdawn.slick.SlickException;

public class Zombie extends Character {

    protected int attack;

    public Zombie(float x, float y) throws SlickException {
        super(x, y);
        setSprite("data/images/zombie/zombie");
        setMovingAnimation("data/images/zombie/zombie", 3, 100);

        //TODO move these around to fix the box to fit to the character sprite
        boundingShape = new AABoundingRect(x, y, 32, 32);

        maximumSpeed = 0.06f;
        diagonalSpeed = 0.04f;
        health = 10;
        type = "zombie";
        attack = 5;
    }

    public int getAttack() {
        return attack;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x,y);
    }


}