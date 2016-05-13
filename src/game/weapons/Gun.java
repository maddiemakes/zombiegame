package game.weapons;

public abstract class Gun {

    protected int damage;
    protected int fireRate;
    protected int bulletSpeed;
    protected int maxAmmo;
    protected int clipSize;
    protected int currentAmmo;

    //number is how many shots are fired per mouse click, 0 is infinity
    protected int semi; //0 = fully automatic;  1 = semi automatic;  3 = 3 round burst

    public Gun () {
        //TODO
    }

}
