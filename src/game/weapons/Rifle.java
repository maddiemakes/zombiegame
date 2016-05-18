package game.weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Rifle extends Gun {
    public Rifle() {
        semi = 0;
        damage = 70;
        clipSize = 32;
        clipAmmo = clipSize;
        maxAmmo = clipSize*3;
        currentAmmo = maxAmmo;
        fireRate = 100;
        reloadSpeed = 4000;
        try {
            shootSound = new Sound("data/audio/sounds/guns/usp_fire.ogg");
            reloadSound = new Sound("data/audio/sounds/guns/AK47_Reload.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
