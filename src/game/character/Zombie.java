package game.character;

import game.physics.AABoundingRect;
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
        health = 100;
        type = "zombie";
        attack = 5;
    }

    public int getAttack() {
        return attack;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+6,y+6);
    }

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
                soundThing[rand.nextInt(soundThing.length)].play(1, .5f);
            }
        }
    }

}