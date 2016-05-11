package game.level;

import game.character.Character;

import game.level.tile.AirTile;
import game.level.tile.SolidTile;
import game.level.tile.Tile;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

public class Level {

    private TiledMap map;
    private Tile[][] tiles;

    //a list of all characters present somewhere on this map
    private ArrayList<Character> characters;

    public Level(String level) throws SlickException {
        map = new TiledMap("data/levels/" + level + ".tmx", "data/levels/");
        characters = new ArrayList<>();

        loadTileMap();
    }

    private void loadTileMap(){
        //create an array to hold all the tiles in the map
        tiles = new Tile[map.getWidth()][map.getHeight()];

        int layerIndex = map.getLayerIndex("CollisionLayer");

        if(layerIndex == -1){
            //TODO we can clean this up later with an exception if we want, but because we make the maps ourselfs this will suffice for now
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }

        //loop through the whole map
        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y < map.getHeight(); y++){

                //get the tile
                int tileID = map.getTileId(x, y, layerIndex);

                Tile tile = null;

                //and check what kind of tile it is (
                switch(map.getTileProperty(tileID, "tileType", "solid")){
                    case "air":
                        tile = new AirTile(x,y);
                        break;
                    default:
                        tile = new SolidTile(x,y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public ArrayList<Character> getCharacters(){
        return characters;
    }

    public void render() {
        //render the map first
        map.render(0, 0, 39, 35, 32, 18);

        //and then render the characters on top of the map
        for (Character c : characters) {
            c.render();
        }
    }

    public Tile[][] getTiles(){
        return tiles;
    }
}
