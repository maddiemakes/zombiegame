package game.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

public class ExampleMenuState extends MenuState {

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
        switch (handleMenuItems(container, menuItemsText, smallTtf)) {
            case 6:
                game.enterState(0);
                break;
            default:
                break;
        }
    }

    @Override
    public int getID()
    {
        return 6;
    }
}