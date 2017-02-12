package Game.States;

import Engine.Entity.Animation;
import Engine.Entity.MapObject;
import Engine.TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Michał Pawłowicz on 11.02.17.
 */
public class BrickBlock extends MapObject{

    private Point actualPosition;


    public BrickBlock(TileMap tileMap, BufferedImage[] frames, Point position) {
        super(tileMap);
        this.position = position;
        animation = new Animation(frames, 100);

        height = 16;
        width = 16;

        actualPosition = new Point((int)tileMap.getPosition().getX() + (int)position.getX(),
                (int)tileMap.getPosition().getY() + (int)position.getY());
    }

    public void update(){
        if (isOnScreen()){
            actualPosition.setLocation(tileMap.getPosition().getX() + position.getX(),
                    tileMap.getPosition().getY() + position.getY());
        }
    }

    public void draw(Graphics2D g){
        if(isOnScreen()){
            g.drawImage(animation.getImage(), (int)actualPosition.getX(), (int)actualPosition.getY(), width, height, null);
        }
    }

    public boolean isOnScreen(){ return true; }

}
