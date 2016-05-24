package game.state;

import game.level.Level;
import game.settings.SettingsGame;
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
import static game.state.LevelState.player;

public class LevelSelectMenuState extends MenuState {

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
        menuItemsText.add("Tester");
        menuItemsText.add("NCSSM");
        menuItemsText.add("Back");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int center = container.getWidth()/2;

        ttf.drawString(center - 190, 50, "ZombieGame");
//        handleMenuItems(container, game, g);
        switch (handleMenuItems(container,menuItemsText, smallTtf)) {
            case 1:
                LevelState.startinglevel = "level_0";
                LevelState.levelSelected = true;
                SettingsGame.playerX = 541;
                SettingsGame.playerY = 667;
                player.setX(SettingsGame.playerX);
                player.setY(SettingsGame.playerY);
                LevelState.startLevel("level_0");
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                LevelState.paused = false;
                break;
            case 2:
                LevelState.startinglevel = "ncssm";
                LevelState.levelSelected = true;
                SettingsGame.playerX = 791;
                SettingsGame.playerY = 1050;
                player.setX(SettingsGame.playerX);
                player.setY(SettingsGame.playerY);
                LevelState.startLevel("ncssm");
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                LevelState.paused = false;
                break;
            case 3:
                game.enterState(0, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                LevelState.restart();
                break;
        }
    }

    @Override
    public int getID()
    {
        return 7;
    }
}
