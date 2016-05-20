package game.weapons;


import game.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.Stack;

import static game.state.LevelState.playerGun;

public class AmmoBar {

    private float maxWidth;
    private Image bulletImage;
    private Stack<Rectangle> bullets;
    private float x;
    private float y;
    private int maxBulletMarks = 24;
    /*
    Use lines/image to define bullet are
     */
    public AmmoBar(float x_, float y_) throws SlickException {
        this.x = x_;
        this.y = y_;
        maxWidth = (Game.WINDOW_WIDTH/Game.SCALE)/5;
        bulletImage = new Image("data/images/bullet.png");
        bulletImage = bulletImage.getScaledCopy(1.4f);
        bullets = new Stack<>();
        setAmmoBar();
    }

    public void setAmmoBar()
    {
        x+= bullets.size()*3;
        bullets.clear();
        if (playerGun.getClip() > 0) {
            for (int i = 0; i < (playerGun.getClip()*maxBulletMarks/playerGun.getClipSize()); i++) {
                bullets.add(new Rectangle(x, y, 2, 5));
                x -= 3;
            }
        }

    }

//    public void bulletShot() {
//        //fjdskf
//        if(bullets.size() > 0) {
//            for (int i = 0; i < ((int)maxBulletMarks)/((int)playerGun.clipSize); i++) {
//                //dsjfodk
//                bullets.pop();
//                x += 3;a
//            }
//        }
//    }

    public void draw(Graphics g) {

//        g.drawString("Ammo", x - 19, y - 5);
        g.drawImage(bulletImage, Game.WINDOW_WIDTH/Game.SCALE - 36, y - 18);
        g.drawImage(bulletImage, Game.WINDOW_WIDTH/Game.SCALE - 32, y - 18);
        g.drawImage(bulletImage, Game.WINDOW_WIDTH/Game.SCALE - 28, y - 18);
        g.setColor(Color.yellow);
        for(Rectangle r: bullets) {
            g.fill(r);
        }
        g.setColor(Color.white);
    }


}
