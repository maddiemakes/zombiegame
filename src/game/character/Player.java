package game.character;

import game.enums.Facing;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Character {

    public Player(float x, float y) throws SlickException{
        super(x,y);
        setSprite(new Image("images/player.png"));
    }

    public void moveLeft(int delta){
        facing = Facing.LEFT;
        x = x - (0.15f*delta);
    }

    public void moveRight(int delta){
        facing = Facing.RIGHT;
        x = x + (0.15f*delta);
    }

    public void moveUp(int delta) {
        y = y - (0.15f*delta);
    }

    public void moveDown(int delta) {
        y = y + (0.15f*delta);
    }

    public void moveUpLeft(int delta) {
        x = x - (0.15f*delta);
        y = y - (0.15f*delta);
    }

    public void moveUpRight(int delta) {
        x = x + (0.15f*delta);
        y = y - (0.15f*delta);
    }

    public void moveDownLeft(int delta) {
        x = x - (0.15f*delta);
        y = y + (0.15f*delta);
    }

    public void moveDownRight(int delta) {
        x = x + (0.15f*delta);
        y = y + (0.15f*delta);
    }
}