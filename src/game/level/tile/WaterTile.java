package game.level.tile;

import game.physics.AABoundingRect;

public class WaterTile extends Tile {

    public WaterTile(int x, int y) {
        super(x, y);
        boundingShape = new AABoundingRect(x*16,y*16,16,16);
    }

}