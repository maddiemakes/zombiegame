package game.settings;

import org.newdawn.slick.Input;

public class SettingsControls {

    public static int walkLeft1 = Input.KEY_A;
    public static int walkLeft2 = Input.KEY_LEFT;
    public static int walkRight1 = Input.KEY_D;
    public static int walkRight2 = Input.KEY_RIGHT;
    public static int walkUp1 = Input.KEY_W;
    public static int walkUp2 = Input.KEY_UP;
    public static int walkDown1 = Input.KEY_S;
    public static int walkDown2 = Input.KEY_DOWN;

    public static int reload = Input.KEY_R;
    public static int shoot = Input.MOUSE_LEFT_BUTTON;
    public static int weaponSwap = Input.KEY_Q;

    public static int mute = Input.KEY_M;
    public static int invincibility = Input.KEY_I; //toggles player invincibility
    public static int restart = Input.KEY_G; //starts a new game
    public static int spawnZombie = Input.KEY_Z; //spawns one zombie
    public static int apocalypse = Input.KEY_P; //toggles super zombie spawning
    public static int invisibility = Input.KEY_O; //toggles zombies attacking you
    public static int devSettings = Input.KEY_SLASH; //toggles dev settings

}
