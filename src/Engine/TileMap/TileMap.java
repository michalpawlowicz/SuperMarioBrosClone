package Engine.TileMap;

import Game.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 */
public class TileMap {

    public enum TileType {
        BLOCKED,
        NORMAL,
        BLOCK,
        QUESTION_MARK
    }

    //Position
    private Point position;
    private Point maxPosition;
    private Point minPosition;

    //Integer map
    private int numCollumns;
    private int numRows;
    private int map[][];

    //Tile
    private int tileSize;
    private BufferedImage tileset;
    private int numOfTiles;
    private int numOfRowsWithTiles;
    private Tile[][] tiles;


    //Drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColToDraw;

    //Dimensions
    private double height;
    private double width;

    public TileMap(int tileSize){
        this.tileSize = tileSize;
        position = new Point(0,0);
        minPosition = new Point(GamePanel.WIDTH, GamePanel.HEIGHT);
        maxPosition = new Point(0, 0);
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColToDraw = GamePanel.WIDTH / tileSize + 2;
        rowOffset = 0;
        colOffset = 0;

    }

    public void loadTile(String s, TileType[] tileTypes) throws IOException {
        tileset = ImageIO.read( getClass().getResourceAsStream(s));
        height = tileset.getHeight();
        width = tileset.getWidth(null);
        numOfTiles = tileset.getWidth() / tileSize;
        numOfRowsWithTiles = tileset.getHeight() / tileSize;

        tiles = new Tile[numOfRowsWithTiles][numOfTiles];

        System.out.println(numOfRowsWithTiles + " " + numOfTiles);

        BufferedImage subimage;
        for(int k=0; k<numOfRowsWithTiles; k++) {
            for (int i = 0; i < numOfTiles; i++) {
                subimage = tileset.getSubimage(i * tileSize, k * tileSize, tileSize, tileSize);
                tiles[k][i] = new Tile(subimage, tileTypes[k]);
            }
        }
    }
    public void loadMap(String s) throws IOException {
        InputStream in = getClass().getResourceAsStream(s);
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(in));
        numRows = Integer.parseInt(bufferedInputStream.readLine());
        numCollumns = Integer.parseInt(bufferedInputStream.readLine());
        map = new int[numRows][numCollumns];

        for(int row = 0; row < numRows; row++){
            String line = bufferedInputStream.readLine();
            String[] tokens = line.split("\\s+");
            for(int col = 0; col < numCollumns; col++){
                map[row][col] = Integer.parseInt(tokens[col]);
            }
        }
    }

    public void draw(Graphics2D g){
        for(int row = rowOffset; row < rowOffset + numRowsToDraw  && row < numRows; row++){
            for(int col = colOffset; col < colOffset + numColToDraw  && col < numCollumns; col++){
                if(map[row][col] != -1) {
                    int rc = map[row][col];
                    int r = rc / numOfTiles;
                    int c = rc % numOfTiles;
                    g.drawImage(tiles[r][c].getImage(),
                            (int)position.getX() + col*tileSize,
                            (int)position.getY() + row*tileSize,
                            null
                    );
                    /*//debug
                    if(this.getType(row, col) == TileType.BLOCKED){
                        g.setColor(Color.BLACK);
                        g.drawString("B (" + col + "," + row + ")" , (int)position.getX() + col*tileSize,
                                (int)position.getY() + row*tileSize);
                    }*/
                }
            }
        }
    }

    public int getTileSize(){ return tileSize; }
    public Point getPosition(){ return position; }

    public TileMap.TileType getType(int row, int col){
        if(col >= numCollumns) {
            return TileType.BLOCKED;
        }
        if(col < 0) return TileType.BLOCKED;
        if(row >= numRowsToDraw) return TileType.BLOCKED;
        if(row < 0) return TileType.BLOCKED;
        if(map[row][col] == -1) return TileType.NORMAL;
        int rc = map[row][col];
        int r = rc / numOfTiles;
        int c = rc % numOfTiles;
        return tiles[r][c].getType();
    }
    public void setPosition(double x, double y){

        addToPosition((x - position.getX())*1, 0);
        /*if(position.getX() < minPosition.getX()){
            position.setLocation(minPosition.getX(), position.getY());
        }*/
        /*if(position.getY() < minPosition.getY()){
            position.setLocation(position.getY(), minPosition.getY());
        }*/
        if(position.getX() > maxPosition.getX()){
            position.setLocation(maxPosition.getX(), position.getY());
        }
        /*if(position.getY() > maxPosition.getY()){
            position.setLocation(position.getX(), maxPosition.getY());
        }*/

        colOffset = Math.abs((int)position.getX()) / tileSize;

    }

    private void addToPosition(double x, double y){
        position.setLocation(position.getX() + x, position.getY() + y);
    }

    public double getWidth(){ return width; }
    public int getColOffset(){ return colOffset; }
    public int getNumCollumns(){ return numCollumns; }
    public void setMap(int row, int col, int val){
        map[row][col] = val;
    }
    public Tile getTile(int row, int col){ return tiles[row / numOfTiles][col % numOfTiles]; }

}
