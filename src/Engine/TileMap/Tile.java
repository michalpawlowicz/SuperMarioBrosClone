package Engine.TileMap;

import java.awt.*;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 */
public class Tile {
    private Image image;
    private TileMap.TileType type;
    public Tile(Image image, TileMap.TileType type){
        this.type = type;
        this.image = image;
    }
    public Image getImage(){ return image; }
    public TileMap.TileType getType(){ return type; }
    public void setType(TileMap.TileType type){ this.type = type; }
}
