package game.weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Pistol extends Gun {
    public Pistol () {
        semi = 1;
        damage = 20;
        clipSize = 12;
        clipAmmo = clipSize;
        maxAmmo = clipSize*6;
        currentAmmo = maxAmmo;
        fireRate = 200;
        reloadSpeed = 3000;
        try {
            shootSound = new Sound("data/audio/sounds/guns/usp_fire.ogg");
            reloadSound = new Sound("data/audio/sounds/guns/pistolReload.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
