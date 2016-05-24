package game.state;

import game.level.Level;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

import java.awt.*;
import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import static game.state.LevelState.lastMenu;
import static game.state.LevelState.menuChange;

public class PauseMenuState extends BasicGameState
{
    private Font font;
    private TrueTypeFont ttf;
    private Font smallFont;
    private TrueTypeFont smallTtf;
    private StateBasedGame game;
    private ArrayList<String> menuItemsText;

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

        menuItemsText = new ArrayList<>();
        menuItemsText.add("Play");
        menuItemsText.add("Settings");
        menuItemsText.add("Quit");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int center = container.getWidth()/2;

        ttf.drawString(center - 190, 50, "ZombieGame");
        handleMenuItems(container, game, g);
    }

    public static Point getMousePos() {
        return new Point(Mouse.getX(), Mouse.getY());
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }

    private void handleMenuItems(GameContainer container, StateBasedGame game, Graphics g) {

        int center = container.getWidth()/2;
        int menuY = 150;
        int mouseX = center-160;
        int mouseY = 545;
        boolean mouseHover = false;
        int menuItem = 0;
        g.setFont(smallTtf);

        for (String string: menuItemsText) {
            if (!mouseHover) {
                menuItem++;
            }
            if (Mouse.getX() >= mouseX && Mouse.getX() <= mouseX + smallTtf.getWidth(string) && Mouse.getY() >= mouseY && Mouse.getY() <= mouseY + smallTtf.getHeight(string)) {
                smallTtf.drawString(center-180, menuY, "> " + string, Color.green);
                mouseHover = true;
                if (Mouse.isButtonDown(0) && !menuChange) {
                    menuChange = true;
                    switch (menuItem) {
                        case 1:
                            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                            LevelState.paused = false;
                            lastMenu = getID();
                            break;
                        case 2:
                            game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                            lastMenu = getID();
                            break;
                        case 3:
                            game.enterState(0, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                            LevelState.restart();
                            break;
                    }
                } else if (!Mouse.isButtonDown(0) && menuChange) {
                    menuChange = false;
                }
            }
            else {
                smallTtf.drawString(center-160, menuY, string, Color.white);
            }
            menuY += 50;
            mouseY -= 50;
        }
    }

    @Override
    public int getID()
    {
        return 2;
    }
}
