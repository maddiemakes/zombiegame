package game.state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

import java.awt.*;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

public class MenuState extends BasicGameState {

    private Font font;
    private TrueTypeFont ttf;
    private Font smallFont;
    private TrueTypeFont smallTtf;
    private StateBasedGame game;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("data/fonts/vtks-distress.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(62f);
            ttf = new TrueTypeFont(font, false);
        } catch(Exception ex){}

        smallFont = new Font("Verdana", Font.BOLD, 18);
        smallTtf = new TrueTypeFont(smallFont, false);
        this.game = game;

        game.getState(1).init(container, game);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        //g.setFont(ttf);
//        ttf.drawString(450, 50, "ZombieGame");
        int center = container.getWidth()/2;

        ttf.drawString(center - 190, 50, "ZombieGame");
        smallTtf.drawString(container.getWidth()-215, container.getHeight()-30, "Music by Dubwolfer", Color.white);
        //g.setFont(smallTtf);
        if(Mouse.getX() <= center-115 && Mouse.getX() >= center-160 && Mouse.getY() >= 545 && Mouse.getY() <= 565) {
            smallTtf.drawString(center-180, 150, "> Play", Color.green);
        }else{
//            smallTtf.drawString(center-180, 150, "> Play", Color.white);
            smallTtf.drawString(center-160, 150, "Play", Color.white);
        }
        if(Mouse.getX() <= center-75 && Mouse.getX() >= center-160 && Mouse.getY() >= 500 && Mouse.getY() <= 515) {
            g.setColor(Color.green);
            smallTtf.drawString(center-180, 200, "> Settings", Color.green);
        }else {
            smallTtf.drawString(center-160, 200, "Settings", Color.white);
        }

        if(Mouse.getX() <= center-125 && Mouse.getX() >= center-160 && Mouse.getY() >= 455 && Mouse.getY() <= 475) {
            g.setColor(Color.green);
            smallTtf.drawString(center-180, 250, "> Exit", Color.green);
        }else {
            smallTtf.drawString(center-160, 250, "Exit", Color.white);
        }
    }

    public static Point getMousePos() {
        return new Point(Mouse.getX(), Mouse.getY());
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        int center = container.getWidth()/2;

        if(Mouse.isButtonDown(0))
        {
            //play
            if(Mouse.getX() <= center-115 && Mouse.getX() >= center-160 && Mouse.getY() >= 545 && Mouse.getY() <= 565)
            {
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                LevelState.paused = false;
            }

            //settings
            if(Mouse.getX() <= center-75 && Mouse.getX() >= center-160 && Mouse.getY() >= 500 && Mouse.getY() <= 515) {
                game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

            //exit
            if(Mouse.getX() <= center-125 && Mouse.getX() >= center-160 && Mouse.getY() >= 455 && Mouse.getY() <= 475) {
                System.exit(0);
            }
        }

    }

    @Override
    public int getID()
    {
        return 0;
    }
}