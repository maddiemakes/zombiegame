package game.character;


import game.Game;
import game.character.Player;
import game.settings.SettingsGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class HealthBar {

    private float maxWidth;
    private Rectangle healthBaseRect;
    private Rectangle healthBar;
    private float x;
    private float y;

    public HealthBar(float x, float y) {
        this.x = x;
        this.y = y;
        maxWidth = (Game.WINDOW_WIDTH/Game.SCALE)/5;
//        healthBaseRect = new Rectangle(20, ((Game.WINDOW_HEIGTH / Game.SCALE) - 12), maxWidth,(((Game.WINDOW_HEIGTH)/Game.SCALE) / 20) - 7);
        healthBaseRect = new Rectangle(x, y, maxWidth,(((Game.WINDOW_HEIGTH)/Game.SCALE) / 20) - 7);
        healthBar = new Rectangle(healthBaseRect.getX() + 1.1f, healthBaseRect.getY() + 1.5f, healthBaseRect.getWidth() - 2.5f, healthBaseRect.getHeight() - 3);

    }

    public void setHealthBar(Player player)
    {
        if(player.getHealth() >= 0) {
            healthBar.setWidth(maxWidth * (player.getHealth()/player.getMaxHealth()));
        }
        else
            healthBar.setWidth(0);
    }

    public void reset() {
        healthBar.setWidth(maxWidth);
    }

    public void draw(Graphics g) {
//        if(player.getHealth() <= player.getMaxHealth() * 0.33)
//            g.setColor(Color.red);
//        if(player.getHealth() <= player.getMaxHealth() * 0.67)
//            g.setColor(Color.yellow);
//        else
        g.setColor(SettingsGame.healthBarColor);
        g.drawString("HP", x - 19, y - 4);
        g.draw(healthBaseRect);
        g.fill(healthBar);
        g.setColor(Color.white);
    }


}
