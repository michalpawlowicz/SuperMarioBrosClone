package Engine.Entity;

import Game.Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Michał Pawłowicz on 09.02.17.
 */
public class Background {

    private BufferedImage image;

    //Position and destination
    private Point position;
    private int dx, dy;

    //dimensions
    private int height;
    private int widht;

    public Background(BufferedImage image, Point position, int width, int height){
        this.image = image;
        this.widht = width;
        this.height = height;
        this.position = position;
    }

    public void update(){
        position.setLocation(position.getX()+dx, position.getY()+dy);
    }

    public void draw(Graphics2D g){
        g.drawImage(image, (int)position.getX(), (int)position.getY(), widht, height, null);
        if(position.getX() < 0){
            g.drawImage(image, (int)position.getX() + GamePanel.WIDTH, (int)position.getY(), widht, height, null);
        } else if(position.getX() > 0){
            g.drawImage(image, (int)position.getX() - GamePanel.WIDTH, (int)position.getY(), widht, height, null);
        }
    }

    public void setPosition(Point position){ this.position = position; }
    public void setDestination(int dx, int dy){ this.dx = dx; this.dy = dy; }

}