package game.state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static game.state.LevelState.menuChange;

public class MenuState extends BasicGameState {

    protected Font font;
    protected TrueTypeFont ttf;
    protected Font smallFont;
    protected TrueTypeFont smallTtf;
    protected StateBasedGame game;
    protected ArrayList<String> menuItemsText;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
//        try {
//            InputStream inputStream = ResourceLoader.getResourceAsStream("data/fonts/vtks-distress.ttf");
//            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
//            font = font.deriveFont(62f);
//            ttf = new TrueTypeFont(font, false);
//        } catch(Exception ex){}
//
//        smallFont = new Font("Verdana", Font.BOLD, 18);
//        smallTtf = new TrueTypeFont(smallFont, false);
//        this.game = game;
//
//        menuItemsText = new ArrayList<>();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }

    public int handleMenuItems(GameContainer container, ArrayList<String> menuItemsText, TrueTypeFont smallTtf) {

        int center = container.getWidth()/2;
        int menuY = 150;
        int mouseX = center-160;
        int mouseY = 545;
        boolean mouseHover = false;
        int menuItem = 0;

        for (String string: menuItemsText) {
            if (!mouseHover) {
                menuItem++;
            }
            if (Mouse.getX() >= mouseX && Mouse.getX() <= mouseX + smallTtf.getWidth(string) && Mouse.getY() >= mouseY && Mouse.getY() <= mouseY + smallTtf.getHeight(string)) {
                smallTtf.drawString(center-180, menuY, "> " + string, Color.green);
                mouseHover = true;
                if (Mouse.isButtonDown(0) && !menuChange) {
                    menuChange = true;
                    return menuItem;
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
        return 0;
    }

    @Override
    public int getID()
    {
        return 0;
    }
}