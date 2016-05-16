package game.weapons;

import game.level.LevelObject;
import game.physics.AABoundingRect;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends LevelObject {

    protected int damage;
    protected Image sprite;
    protected int health; //this is for collaterals
    public static int bbb;

    public Bullet(float x, float y) throws SlickException {
        super(x, y);

        health = 1;

//        boundingShape = new AABoundingRect(x+15, y+10, 3, 8);
        boundingShape = new AABoundingRect(x+7, y+4, 3, 8);

        sprite = new Image("data/images/bullet.png");
        damage = 1;
        bbb = 0;
    }

    public void render(float offset_x, float offset_y) {
        if (health > 0) {
            sprite.draw(x - offset_x, y - offset_y);
            setX(x + x_velocity);
            setY(y + y_velocity);
//        } else {
//            setXVelocity(0f);
//            setYVelocity(0f);
//            setX(x);
//            setY(y);
//            sprite.destroy();
        }
    }

    public void damage(int thickness) {
        //TODO make it so bullets can go through some things but not others
        health -= thickness;
    }

    public int getHealth() {
        return health;
    }

    public void kill() throws SlickException {
        setXVelocity(0);
        setYVelocity(0);
        sprite.destroy();
    }
}
