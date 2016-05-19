package game.level.tile;

import game.physics.AABoundingRect;

public class WasteTile extends Tile {

    public WasteTile(int x, int y) {
        super(x, y);
        boundingShape = new AABoundingRect(x*16,y*16,16,16);
    }

}