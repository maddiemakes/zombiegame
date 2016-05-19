package game.character;


import game.character.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class HealthBar extends Rectangle {

    private float maxWidth;

    public HealthBar(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.maxWidth = w;
    }
    public void setHealthBar(Player player)
    {
        if(player.getHealth() >= 0) {
            setWidth(maxWidth * (player.getHealth()/player.getMaxHealth()));
        }
        else
            setWidth(0);
    }
    public void reset() {
        setWidth(maxWidth);
    }
    public void draw(Graphics g, Player player) {
//        if(player.getHealth() <= player.getMaxHealth() * 0.33)
//            g.setColor(Color.red);
//        if(player.getHealth() <= player.getMaxHealth() * 0.67)
//            g.setColor(Color.yellow);
//        else
        g.drawString("HP", x - 19, y - 5);
        g.fill(this);
        g.setColor(Color.white);
    }


}
