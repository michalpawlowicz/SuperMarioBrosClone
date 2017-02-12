package Engine.Entity;

import Engine.TileMap.TileMap;
import Game.Main.GamePanel;

import java.awt.*;

/**
 * Created by Michał Pawłowicz on 04.02.17.
 */
public abstract class MapObject {

    protected TileMap tileMap;

    protected Point position;
    protected Point tmpPosition;
    protected Point centerPosition;
    //dx and dy together creates destination
    protected double dx, dy;

    protected boolean falling;

    protected int height;
    protected int width;

    protected Animation animation;

    protected boolean facingRight = true;

    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        tmpPosition = new Point();
        centerPosition = new Point();
    }

    public void checkCollisionWith(MapObject o){
        if(dy < 0){

        }
    }

    public void checkCollision(){
        tmpPosition.setLocation(position.getX(), position.getY());
        int row, col;
        if(dx > 0){
            col = (int)(position.getX() + dx + width) / tileMap.getTileSize();
            row = (int)(position.getY() + height / 2) / tileMap.getTileSize();
            if(tileMap.getType(row, col) == TileMap.TileType.BLOCKED){
                dx = 0;
                System.out.println(col + " " + row);
                tmpPosition.setLocation(
                        col*tileMap.getTileSize() - width,
                        tmpPosition.getY());
            } else {
                tmpPosition.setLocation(tmpPosition.getX()+dx, tmpPosition.getY());
            }
        } else if (dx < 0){
            col = (int)(position.getX() + dx) / tileMap.getTileSize();
            row = (int)(position.getY() + height / 2) / tileMap.getTileSize();
            if(tileMap.getType(row, col) == TileMap.TileType.BLOCKED){
                dx = 0;
                tmpPosition.setLocation(
                        col*tileMap.getTileSize()+tileMap.getTileSize(),
                        tmpPosition.getY());
            } else {
                tmpPosition.setLocation(tmpPosition.getX()+dx, tmpPosition.getY());
            }
        }

        if(dy > 0){
            col = (int)(position.getX() + width / 2) / tileMap.getTileSize();
            row = (int)(position.getY() + dy + height) / tileMap.getTileSize();
            if(tileMap.getType(row, col) == TileMap.TileType.BLOCKED) {
                dy = 0;
                tmpPosition.setLocation(tmpPosition.getX(), row*tileMap.getTileSize() - height);
                falling = false;
            } else {
                tmpPosition.setLocation(tmpPosition.getX(), tmpPosition.getY()+dy);
            }
        } else if(dy < 0){
            col = (int)(position.getX()) / tileMap.getTileSize();
            row = (int)(position.getY() + dy) / tileMap.getTileSize();
            if(tileMap.getType(row, col) == TileMap.TileType.BLOCKED) {
                dy = 0;
                tmpPosition.setLocation(tmpPosition.getX(), tileMap.getTileSize() + row*tileMap.getTileSize() - 1);
            } else {
                tmpPosition.setLocation(tmpPosition.getX(), tmpPosition.getY()+dy);
            }
        }

        if(!falling) {
            col = (int) (position.getX()) / tileMap.getTileSize();
            row = (int) (position.getY() + dy + 1 + height) / tileMap.getTileSize();
            if (tileMap.getType(row, col) == TileMap.TileType.NORMAL) {
                falling = true;
            }
        }
    }

    public Rectangle getRectangle(){
        return new Rectangle((int)position.getX(), (int)position.getY(), width, height);
    }

    public void setPosition(){
        position.setLocation(tmpPosition);
        centerPosition.setLocation(position.getX() + width / 2, position.getY() + height / 2);
    }

    public boolean isOnScreen(){
        return false;
    }
    public void setFacingRight(boolean b){ this.facingRight = b; }
    public Point getTmpPosition(){ return tmpPosition; }
    public Point getPosition(){ return position; }
    public Point getCenterPosition(){ return centerPosition; }
    public int getHeight(){ return height; }
    public int getWidth(){ return width; }
}