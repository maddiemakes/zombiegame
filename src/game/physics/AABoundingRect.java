package game.physics;

import game.level.tile.Tile;

import java.util.ArrayList;

public class AABoundingRect extends BoundingShape {

    public float x;
    public float y;
    public float width;
    public float height;

    public AABoundingRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void movePosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public boolean checkCollision(AABoundingRect rect) {
        return !(rect.x > this.x+width || rect.x+rect.width < this.x || rect.y > this.y+height || rect.y+rect.height < this.y);
    }

    public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
        ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();

        //we go from the left of the rect towards to right of the rect, making sure we round upwards to a multiple of 32 or we might miss a few tiles
        for(int i = (int) x; i <= x+width+(16-width%16); i+=16){
            for(int j = (int) y; j <= y+height+(16-height%16); j+=16){
                if (i/16 > 100) {
                    i = 16*100;
                }
                if (j/16 > 100) {
                    j = 16*100;
                }
                occupiedTiles.add(tiles[i/16][j/16]);
            }
        }
        return occupiedTiles;
    }
}