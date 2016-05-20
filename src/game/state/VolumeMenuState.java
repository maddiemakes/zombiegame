package game.state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import javax.swing.*;
import java.awt.Font;
import java.awt.*;
import java.io.InputStream;

public class VolumeMenuState extends BasicGameState
{
    private Font font;
    private TrueTypeFont ttf;
    private Font smallFont;
    private TrueTypeFont smallTtf;
    private StateBasedGame game;
    /*
    -volume
    ***new levelState
    -controls
    ***new levelState
    -back
    ***return to previous levelState
     */

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        this.game = game;
        try
        {
            InputStream inputStream = ResourceLoader.getResourceAsStream("data/fonts/vtks-distress.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(62f);
            ttf = new TrueTypeFont(font, false);
        }catch(Exception ex){}
        smallFont = new Font("Verdana", Font.BOLD, 18);
        smallTtf = new TrueTypeFont(smallFont, false);
        LevelState.paused = true;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

//        g.setFont(ttf);
//        ttf.drawstring(450, 50, "Volume");
        int center = container.getWidth()/2;


        ttf.drawString(center - 190, 50, "ZombieGame");
        //g.setFont(smallTtf);
        if(Mouse.getX() <= center-60 && Mouse.getX() >= center-160 && Mouse.getY() >= 545 && Mouse.getY() <= 565) {
            g.setColor(Color.green);

            smallTtf.drawString(center-180, 150, "> Master Volume:", Color.green);
        }else{
//            smallTtf.drawString(center-180, 150, "> Play", Color.white);
            smallTtf.drawString(center-160, 150, "Master Volume:", Color.white);
        }
        JSlider mv = new JSlider(0, 100, 100);

        if(Mouse.getX() <= center-60 && Mouse.getX() >= center-160 && Mouse.getY() >= 500 && Mouse.getY() <= 515) {
            g.setColor(Color.green);
            smallTtf.drawString(center-180, 200, "> Music Volume:", Color.green);
        }else {
            smallTtf.drawString(center-160, 200, "Music Volume:", Color.white);
        }

        if(Mouse.getX() <= center-115 && Mouse.getX() >= center-160 && Mouse.getY() >= 455 && Mouse.getY() <= 475) {
            g.setColor(Color.green);
            smallTtf.drawString(center-180, 250, "> Back", Color.green);
        }else {
            smallTtf.drawString(center-160, 250, "Back", Color.white);
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
            if(Mouse.getX() <= center-60 && Mouse.getX() >= center-160 && Mouse.getY() >= 545 && Mouse.getY() <= 565) {
//                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

            if(Mouse.getX() <= center-60 && Mouse.getX() >= center-160 && Mouse.getY() >= 500 && Mouse.getY() <= 515) {
//                game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(Mouse.getX() <= center-115 && Mouse.getX() >= center-160 && Mouse.getY() >= 455 && Mouse.getY() <= 475) {
                game.enterState(3);

            }

            // System.out.println(getMousePos());
        }

    }

    @Override
    public int getID()
    {
        return 4;
    }
}
