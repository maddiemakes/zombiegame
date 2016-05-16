package game.weapons;

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
        Bullet bullet = null;
        try {
            bullet = new Bullet(player.getX(), player.getY());
        } catch (SlickException e) {
            e.printStackTrace();
        }
        bullet.setYVelocity(-bulletSpeed*delta);
        LevelState.bullets.add(bullet);
        currentAmmo--;

        //TODO all this below
//        double mouseX = LevelState.getMousePos().getX();
//        double mouseY = LevelState.getMousePos().getY();
//
//        double angle = Math.toDegrees(Math.atan2(mouseX - player.getX(), mouseY - player.getY()));
////        double xVelocity = (bulletSpeed) * Math.cos(angle);
////        double yVelocity = (bulletSpeed) * Math.sin(angle);
//        bullet.setXVelocity((float) ((bulletSpeed*delta*01) * (Math.cos(angle))));
//        bullet.setYVelocity((float) ((bulletSpeed*delta*01) * (Math.sin(angle))));
    }

    public int getDamage() {
        return damage;
    }

    public int getSemi() {
        return semi;
    }

}
