package game.weapons;

public class Rifle extends Gun {
    public Rifle() {
        semi = 0;
        damage = 70;
        clipSize = 32;
        clipAmmo = clipSize;
        maxAmmo = clipSize*3;
        currentAmmo = maxAmmo;
        fireRate = 100;
        reloadSpeed = 2000;
    }
}
