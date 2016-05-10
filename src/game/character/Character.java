package game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Character {

    protected float x;
    protected float y;
    protected Image sprite;

    public Character(float x, float y) throws SlickException{
        this.x = x;
        this.y = y;
        //in case we forget to set the image, we don't want the game to crash, but it still has to be obvious that something was forgotten
        sprite = new Image("images/placeholder_sprite.png");
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void render(){
        sprite.draw(x-2,y-2);
    }

}