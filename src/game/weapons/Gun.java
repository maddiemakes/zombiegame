package game.weapons;

import game.Game;
import game.level.Level;
import game.state.LevelState;
import org.newdawn.slick.SlickException;

import static game.state.LevelState.player;

public abstract class Gun {

    protected int damage;
    protected int fireRate;
    protected float bulletSpeed;
    protected int maxAmmo;
    protected int clipSize;
    protected int currentAmmo;
    protected int reloadSpeed;

    //number is how many shots are fired per mouse click, 0 is infinity
    protected int semi; //0 = fully automatic;  1 = semi automatic;  3 = 3 round burst

    public Gun () {
        //TODO
        fireRate = 1;
        damage = 1;
        bulletSpeed = 0.4f;
        maxAmmo = 10;
        clipSize = 5;
        reloadSpeed = 1;
    }

    public void shoot(int delta) {
        double mouseX = LevelState.getMousePos().getX()/Game.SCALE;
        double mouseY = LevelState.getMousePos().getY()/Game.SCALE;

        double offsetX = player.offsetx;
        double offsetY = player.offsety;

        player.rotate = (Math.atan2((LevelState.containerHeight/Game.SCALE - mouseY) + offsetY - 8 - player.getY() - 16, mouseX + offsetX + 2 - player.getX() - 16));

        Bullet bullet = null;
        try {
            bullet = new Bullet(player.getX(), player.getY());
            bullet.sprite.rotate((float) Math.toDegrees(player.rotate) - 90);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        LevelState.bullets.add(bullet);
        currentAmmo--;

        //TODO all this below

        bullet.setXVelocity((float) ((bulletSpeed*delta) * (Math.cos(player.rotate))));
        bullet.setYVelocity((float) ((bulletSpeed*delta) * (Math.sin(player.rotate))));
    }

    public int getDamage() {
        return damage;
    }

    public int getSemi() {
        return semi;
    }

}
