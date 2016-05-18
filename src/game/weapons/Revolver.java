package game.weapons;

public class Revolver extends Gun {
    public Revolver() {
        semi = 1;
        damage = 60;
        clipSize = 6;
        clipAmmo = clipSize;
        maxAmmo = clipSize*6;
        currentAmmo = maxAmmo;
        fireRate = 20;
        reloadSpeed = 1000;
    }
}
