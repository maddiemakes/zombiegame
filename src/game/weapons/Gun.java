package game.weapons;

import game.Game;
import game.character.Player;
import game.settings.SettingsGame;
import game.state.LevelState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Set;

import static game.state.LevelState.gunShootTime;
import static game.state.LevelState.player;

public abstract class Gun {

    protected int damage;
    protected int fireRate;
    protected float bulletSpeed;
    protected int maxAmmo;
    protected int clipSize;
    protected int clipAmmo;
    protected int currentAmmo;
    protected int reloadSpeed;
    protected int swapSpeed;

    protected Sound shootSound;
    protected Sound reloadSound;
    public Sound swapSound;

    //number is how many shots are fired per mouse click, 0 is infinity
    protected int semi; //0 = fully automatic;  1 = semi automatic;  3 = 3 round burst

    public Gun () {
        fireRate = 1;
        damage = 1;
        bulletSpeed = 0.4f;
        maxAmmo = 10;
        clipSize = 5;
        reloadSpeed = 1;
        swapSpeed = 350;
        try {
            shootSound = new Sound("data/audio/sounds/guns/Gatling_1.ogg");
            reloadSound = new Sound("data/audio/sounds/guns/pistolReload.ogg");
            swapSound = new Sound("data/audio/sounds/guns/weaponSwap2.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void shoot(int delta) {
        //doesn't shoot if firerate
        if (LevelState.gunShootTime <= 0) {
            double mouseX = LevelState.getMousePos().getX() / Game.SCALE;
            double mouseY = LevelState.getMousePos().getY() / Game.SCALE;

            double offsetX = player.offsetx;
            double offsetY = player.offsety;

            //rotates bullet picture
            player.rotate = (Math.atan2((LevelState.containerHeight / Game.SCALE - mouseY) + offsetY - 8 - player.getY() - 16, mouseX + offsetX + 2 - player.getX() - 16));

            //creates the bullet and plays the sound
            Bullet bullet = null;
            try {
                shootSound.play(1, SettingsGame.gunShootVolume);
//                shootSound.play();
                bullet = new Bullet(player.getX(), player.getY());
                bullet.sprite.rotate((float) Math.toDegrees(player.rotate) - 90);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            LevelState.bullets.add(bullet);
            clipAmmo--;

            //shoots the bullet
            bullet.setXVelocity((float) ((bulletSpeed * delta) * (Math.cos(player.rotate))));
            bullet.setYVelocity((float) ((bulletSpeed * delta) * (Math.sin(player.rotate))));
            LevelState.gunShootTime = this.fireRate;

//            if(clipAmmo > 1) {
//                Player.ammoBar.bulletShot();
            Player.ammoBar.setAmmoBar();
//            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getSemi() {
        return semi;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public int getClip() {
        return clipAmmo;
    }

    public int getClipSize() {
        return clipSize;
    }

    public void reload() {
        if (LevelState.gunShootTime <= 0 && currentAmmo > 0 && clipAmmo < clipSize) {
            reloadSound.play(1, SettingsGame.gunReloadVolume);
            currentAmmo -= (clipSize - clipAmmo);
            if ((currentAmmo + (clipSize - clipAmmo)) + clipAmmo >= clipSize) {
                clipAmmo = clipSize;
            } else {
                clipAmmo += (currentAmmo + (clipSize - clipAmmo)) % clipSize;
            }
            if (currentAmmo < 0) {
                currentAmmo = 0;
            }
            Player.ammoBar.setAmmoBar();
            gunShootTime = this.reloadSpeed;
        }
        if (currentAmmo <= 0 && clipAmmo <= 0) {
            swapGuns();
        }
    }

    public void swapGuns() {
        swapSound.play(1,SettingsGame.gunSwapVolume);
        if (LevelState.playerGun == LevelState.playerGuns[0]) {
            LevelState.playerGun = LevelState.playerGuns[1];
        } else {
            LevelState.playerGun = LevelState.playerGuns[0];
        }
        if (gunShootTime < swapSpeed) {
            gunShootTime = swapSpeed;
        }
        Player.ammoBar.setAmmoBar();
    }

    public void maxAmmo() {
        currentAmmo = maxAmmo;
    }

}