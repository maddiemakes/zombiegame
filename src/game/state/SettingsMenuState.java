package game.state;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import static game.state.LevelState.lastMenu;

public class SettingsMenuState extends MenuState {

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
        menuItemsText.add("Volume");
        menuItemsText.add("Controls");
        menuItemsText.add("Back");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int center = container.getWidth()/2;

        ttf.drawString(center - 190, 50, "ZombieGame");
        switch (handleMenuItems(container,menuItemsText, smallTtf)) {
            case 1:
                game.enterState(4);
                break;
            case 2:
                break;
            case 3:
                game.enterState(lastMenu);
                break;
        }
    }

    @Override
    public int getID()
    {
        return 3;
    }
}
