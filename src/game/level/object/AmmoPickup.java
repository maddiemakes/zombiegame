package game.level.object;

import game.level.LevelObject;
import game.state.LevelState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AmmoPickup extends LevelObject {

    protected Animation animation;
    protected int timer = 30000;

    public AmmoPickup(float x, float y) throws SlickException {
        super(x, y);

        //add the right animation for this objective
        animation = new Animation(new Image[]{new Image("data/images/ammo/ammo1.png"),
                                              new Image("data/images/ammo/ammo2.png"),
                                              new Image("data/images/ammo/ammo3.png"),
                                              new Image("data/images/ammo/ammo4.png")},
                                  new int[]{200, 125, 125, 200});

        animation.setPingPong(true);

        //we will just keep the default boundingrect of 32x32 for the objective
    }

    public void render(float offset_x, float offset_y) {
        animation.draw(x - 2 - offset_x, y - 2 - offset_y);
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int delta) {
        timer -= delta;
    }
}
