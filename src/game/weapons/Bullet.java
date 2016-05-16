package game.weapons;

import game.level.LevelObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends LevelObject {

    protected int damage;
    protected Image sprite;
    protected int health; //this is for collaterals
    public static int bbb;

    public Bullet(float x, float y) throws SlickException {
        super(x, y);

        sprite = new Image("data/images/bullet.png");
        damage = 1;
        bbb = 0;
    }

    public void render(float offset_x, float offset_y) {
        sprite.draw(x - offset_x, y - offset_y);
        setX(x + x_velocity);
        setY(y + y_velocity);
//        setX(x + 1);
//        setY(y + 1);
    }
}
