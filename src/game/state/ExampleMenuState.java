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

import java.awt.Font;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

import static game.state.LevelState.lastMenu;
import static game.state.LevelState.menuChange;

public class ExampleMenuState extends BasicGameState {

    private Font font;
    private TrueTypeFont ttf;
    private Font smallFont;
    private TrueTypeFont smallTtf;
    private StateBasedGame game;
    private ArrayList<String> menuItemsText;

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

        menuItemsText = new ArrayList<>();
        menuItemsText.add("This");
        menuItemsText.add("Is");
        menuItemsText.add("Just");
        menuItemsText.add("A");
        menuItemsText.add("Test");
        menuItemsText.add("Back to Main Menu");


        game.getState(1).init(container, game);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        int center = container.getWidth()/2;

        ttf.drawString(center - 190, 50, "ZombieGame");
        smallTtf.drawString(container.getWidth()-215, container.getHeight()-30, "Music by Dubwolfer", Color.white);
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
                        case 6:
                            game.enterState(0);
                            break;
                        default:
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
        return 6;
    }
}