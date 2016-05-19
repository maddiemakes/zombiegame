package game.state;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

import java.awt.*;
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by Samuel on 5/19/2016.
 */
public class MenuState extends BasicGameState
{
    private Font font;
    private TrueTypeFont ttf;
    private Font smallFont;
    private TrueTypeFont smallTtf;
    private StateBasedGame game;
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        this.game = game;
        font = new Font("Verdana", Font.BOLD, 24);
        ttf = new TrueTypeFont(font, true);
        smallFont = new Font("Verdana", Font.BOLD, 18);
        smallTtf = new TrueTypeFont(smallFont, true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        g.setFont(ttf);
        g.drawString("ZombieGame", 550, 50);
        g.setFont(smallTtf);
        g.drawString("> Play", 500, 100);
        g.drawString("> Quit", 500, 150);

    }
    public static Point getMousePos() {
        return new Point(Mouse.getX(), Mouse.getY());
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        if(Mouse.isButtonDown(0))
        {
           if(Mouse.getX() < 565 && Mouse.getX() > 500 && Mouse.getY() > 600 && Mouse.getY() < 615)
           {

               game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
           }
            if(Mouse.getX() < 565 && Mouse.getX() > 500 && Mouse.getY() > 550 && Mouse.getY() < 565)
            {
                System.exit(0);
            }

               // System.out.println(getMousePos());
        }

    }

    @Override
    public int getID()
    {
        return 0;
    }
}
