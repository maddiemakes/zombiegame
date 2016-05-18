package game.weapons;

public class Pistol extends Gun {
    public Pistol () {
        semi = 1;
        damage = 20;
        clipSize = 7;
        clipAmmo = clipSize;
        maxAmmo = clipSize*6;
        currentAmmo = maxAmmo;
        fireRate = 200;
        reloadSpeed = 1000;
    }
}
