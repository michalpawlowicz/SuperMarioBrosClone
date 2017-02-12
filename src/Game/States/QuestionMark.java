package Game.States;

import Engine.Entity.Animation;
import Engine.Entity.MapObject;
import Engine.TileMap.Tile;
import Engine.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Michał Pawłowicz on 11.02.17.
 */
public class QuestionMark extends MapObject {

    private Point actualPosition;
    private boolean wasHit;
    private BufferedImage[] imageAfterHit;

    public QuestionMark(TileMap tileMap, BufferedImage[] frames, Point position) {
        super(tileMap);

        animation = new Animation(frames, 100);

        this.position = position;

        height = 16;
        width = 16;

        actualPosition = new Point((int)tileMap.getPosition().getX() + (int)position.getX(),
                (int)tileMap.getPosition().getY() + (int)position.getY());

        wasHit = false;

        try {
            imageAfterHit = new BufferedImage[]{ImageIO.read(
                    getClass().getResourceAsStream("/MarioSheets/blocks.png")).getSubimage(4*16, 0, 16, 16)};
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(){
        if(isOnScreen()) {
            animation.update();
            actualPosition.setLocation(tileMap.getPosition().getX() + position.getX(),
                    tileMap.getPosition().getY() + position.getY());
        }
    }

    public void draw(Graphics2D g){
        if(isOnScreen())
            g.drawImage(animation.getImage(), (int)actualPosition.getX(), (int)actualPosition.getY(), width, height, null);
    }

    public boolean isOnScreen(){return true;}
    public void hit(){
        this.wasHit = true;
        animation.setFrames(imageAfterHit, 100);
    }
    public void setHitAnimation(){
        //animation.setFrames();
    }

}
