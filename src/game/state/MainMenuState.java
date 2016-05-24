package game.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import static game.state.LevelState.lastMenu;

public class MainMenuState extends MenuState {

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
        menuItemsText.add("Play");
        menuItemsText.add("Settings");
        menuItemsText.add("Exit");
//        menuItemsText.add("Look at this test menu");


//        game.getState(1).init(container, game);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        LevelState.levelSelected = false;
        int center = container.getWidth()/2;
        ttf.drawString(center - 190, 50, "ZombieGame");
        smallTtf.drawString(container.getWidth()-215, container.getHeight()-30, "Music by Dubwolfer", Color.white);
        switch (handleMenuItems(container,menuItemsText,smallTtf)) {
            case 1:
                game.enterState(7, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            case 2:
                game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                lastMenu = getID();
                break;
            case 3:
                System.exit(0);
                break;
            case 4:
                game.enterState(6);
                break;
            default:
                break;
        }
    }

    @Override
    public int getID()
    {
        return 0;
    }
}