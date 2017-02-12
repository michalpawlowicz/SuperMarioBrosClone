package Game.States;

import Engine.Entity.Background;
import Engine.GameState;
import Engine.TileMap.TileMap;
import Game.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 */
public class Level1State extends GameState {

    private TileMap tileMap;
    private TileMap backgroundMap;
    private Player player;
    private Background background;

    private QuestionMark[] questionMarks;

    public Level1State(){
        tileMap = new TileMap(16);
        try {
            tileMap.loadMap("/Maps/Level1Map.map");
            tileMap.loadTile("/MarioSheets/plansza.png",
                    new TileMap.TileType[]{
                            TileMap.TileType.BLOCKED,
                            TileMap.TileType.QUESTION_MARK,
                            TileMap.TileType.BLOCK});

        } catch (IOException e) {
            e.printStackTrace();
        }

        backgroundMap = new TileMap(16);
        try {
            backgroundMap.loadMap("/Maps/backGroundMap");
            backgroundMap.loadTile("/MarioSheets/backgroundEntity.png",
                    new TileMap.TileType[]{TileMap.TileType.NORMAL, TileMap.TileType.NORMAL});
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new Player(tileMap);
        player.setAction(Player.STANDING);

        try {
            background = new Background(ImageIO.read(getClass().getResourceAsStream("/background.png")),
                    new Point(0, 0),
                    GamePanel.WIDTH,
                    GamePanel.HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Loading question mark frames.
        BufferedImage[] questionMarkFrames = new BufferedImage[4];
        try {
            BufferedImage questionMarkSet = ImageIO.read(getClass().getResourceAsStream("/MarioSheets/blocks.png"));
            for(int i=0; i<4; i++){
                questionMarkFrames[i] = questionMarkSet.getSubimage((i+1)*16, 0, 16, 16);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Loading question marks map;
        InputStream in = getClass().getResourceAsStream("/Maps/questionMarkBlock.map");
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(in));
        try {
            questionMarks = new QuestionMark[Integer.parseInt(bufferedInputStream.readLine())];
            String line;
            for(int i=0; i<questionMarks.length; i++){
                line = bufferedInputStream.readLine();
                String[] tokens = line.split("\\s+");
                questionMarks[i] = new QuestionMark(
                        tileMap,
                        questionMarkFrames,
                        new Point(Integer.parseInt(tokens[0])*16, Integer.parseInt(tokens[1])*16));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init() {}

    @Override
    public void update() {
        player.update();
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getTmpPosition().getX(), 0);
        backgroundMap.setPosition(GamePanel.WIDTH / 2 - player.getTmpPosition().getX(), 0);
        for(QuestionMark x: questionMarks)
            x.update();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        background.draw(g);
        backgroundMap.draw(g);
        tileMap.draw(g);

        for(QuestionMark x: questionMarks){
            x.draw(g);
        }

        player.draw(g);
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.setLeft(false);
            player.setAction(Player.STANDING);
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.setRight(false);
            player.setAction(Player.STANDING);
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.setDown(false);
        } else if(e.getKeyCode() == KeyEvent.VK_Z){
            player.setJumping(false);
        }
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.setLeft(true);
            player.setAction(Player.WALKING);
            player.setFacingRight(false);
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.setRight(true);
            player.setAction(Player.WALKING);
            player.setFacingRight(true);
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.setDown(true);
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        } else if(e.getKeyCode() == KeyEvent.VK_Z){
            player.setAction(Player.JUMPING);
            player.setJumping(true);
        }
    }
    public void keyTyped(KeyEvent e) {}
}
