package game.character;

import game.enums.Facing;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public abstract class Character {

    protected float x;
    protected float y;
    protected Facing facing;
    protected HashMap<Facing,Image> sprites;

    public Character(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;
        //in case we forget to set the image, we don't want the game to crash, but it still has to be obvious that something was forgotten
        facing = Facing.RIGHT;
        setSprite(new Image("images/placeholder_sprite.png"));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void render() {
        sprites.get(facing).draw(x-2, y-2);
    }

    protected void setSprite(Image i){
        sprites = new HashMap<Facing,Image>();
        sprites.put(Facing.RIGHT, i);
        sprites.put(Facing.LEFT , i.getFlippedCopy(true, false));
    }

}