package game.character;

import game.Game;
import game.enums.Facing;
import game.physics.AABoundingRect;
import game.state.LevelState;
import org.newdawn.slick.SlickException;

public class Player extends Character {

    public static double rotate;
    public static boolean invincible = false;

    public Player(float x, float y) throws SlickException {
        super(x, y);
        setSprite("data/images/player/player");
        setMovingAnimation("data/images/player/player", 3, 100);

        boundingShape = new AABoundingRect(x+6, y+21, 18, 10);

        originalMaxSpeed = 0.15f;
        originalDiagonalSpeed = 0.1f;
        maximumSpeed = 0.15f;
        diagonalSpeed = 0.1f;
        health = 1000;
        type = "player";
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+6,y+21);
    }

    //TODO
    public void setMouseQuadrant()
    {
        double mouseX = LevelState.getMousePos().getX()/Game.SCALE;
        double mouseY = LevelState.getMousePos().getY()/Game.SCALE;

        double xPos = mouseX + offsetx - x - 14;
        double yPos = (LevelState.containerHeight/Game.SCALE - mouseY) + offsety - y - 24;

        double line1 = (0.5625)*xPos;//*Game.SCALE;
        double line2 = (-0.5625)*xPos+yPos/Game.SCALE;//+720;//*Game.SCALE + LevelState.containerHeight;

        if(yPos >= line1 && yPos > line2) {
            facing = Facing.DOWN;
        } else if(yPos <= line1 && yPos < line2) {
            facing = Facing.UP;
        } else if(yPos < line1 && yPos >= line2) {
            facing = Facing.RIGHT;
        } else {
            facing = Facing.LEFT;
        }
    }

    public void damage(int damage) {
        if (!invincible) {
            health -= damage;
        }
    }

    //this is used for new game
    public void reset() {
        invincible = false;
        originalMaxSpeed = 0.15f;
        originalDiagonalSpeed = 0.1f;
        maximumSpeed = 0.15f;
        diagonalSpeed = 0.1f;
        health = 1000;
    }

}