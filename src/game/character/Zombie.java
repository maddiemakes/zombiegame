package game.character;

import game.level.object.AmmoPickup;
import game.level.object.HealthPickup;
import game.physics.AABoundingRect;
import game.settings.SettingsGame;
import game.state.LevelState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Random;

public class Zombie extends Character {

    protected int attack;

    public Zombie(float x, float y) throws SlickException {
        super(x, y);
        setSprite("data/images/zombie/zombie");
        setMovingAnimation("data/images/zombie/zombie", 3, 100);

        //TODO move these around to fix the box to fit to the character sprite
        boundingShape = new AABoundingRect(x+6, y+6, 20, 26);

        originalMaxSpeed = 0.06f;
        originalDiagonalSpeed = 0.04f;
        maximumSpeed = 0.06f;
        diagonalSpeed = 0.04f;
        maxHealth = 100;
        health = maxHealth;
        attack = 5;

//        LevelObject[] drops = new LevelObject[]{new HealthPickup(), new AmmoPickup};
    }

    public int getAttack() {
        return attack;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+6,y+6);
    }

    @Override
    public int getTimer() {
        return 0;
    }

    @Override
    public void setTimer(int delta) { }

    public void damage(int damage) {
        playSound(LevelState.zombieHurt);
        health -= damage;
    }

    public void playSound(Sound[] soundThing) {
        Random rand = new Random();
        boolean isPlaying = false;
        for (Sound sound: soundThing) {
            if (sound.playing()) {
                isPlaying = true;
            }
        }
        if (!isPlaying) {
            if (rand.nextInt(100) < 8) {
                soundThing[rand.nextInt(soundThing.length)].play(1, SettingsGame.zombieVolume);
            }
        }
    }

    public void drop() throws SlickException {
        Random rand = new Random();
            if (rand.nextInt(100) < 3 && LevelState.dropTimer <= 0) {
                switch (rand.nextInt(LevelState.numberOfDrops)) {
                    case 0:
                        LevelState.level.addLevelObject(new HealthPickup(x,y));
                        break;
                    case 1:
                        LevelState.level.addLevelObject(new AmmoPickup(x,y));
                        break;
                    default:
                        break;
                }
                LevelState.dropTimer = SettingsGame.dropTimer;
            }
//        }
    }

}