package Game.Main;

import Engine.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import Game.States.Level1State;
import Game.States.MenuState;

/**
 * Created by michal on 03.02.17.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

    private Graphics2D g;
    private BufferedImage image;


    //dimentions
    public static final int HEIGHT = 200;
    public static final int WIDTH = 300;
    private static final double SCALE = 3;

    //Game thread
    private Thread thread;
    private boolean running;

    //Timers
    private int fps = 60;
    private long targetTime = 1000 / fps;

    private GameStateManager<String> gameStateManager;

    public GamePanel(){
        super();
        setPreferredSize(new Dimension((int)SCALE*WIDTH, (int)SCALE*HEIGHT));
        setFocusable(true);
    }

    private void init(){
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;

        //State Manager
        gameStateManager = new GameStateManager();

        //Adding states
        gameStateManager.addState(new MenuState(gameStateManager), "MenuState");
        gameStateManager.addState(new Level1State(), "Level1");
        gameStateManager.setCurrentState("MenuState");
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            requestFocus();
            thread.start();
        }
    }

    public void run() {
        long elapsed;
        long wait;
        long start;
        init();
        while(running){
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = Math.max(0, targetTime - elapsed / (long)1e6);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void update(){
        gameStateManager.update();
    }
    private void draw(){
        gameStateManager.draw(g);
    }
    private void drawToScreen(){
        Graphics2D g2 = (Graphics2D) getGraphics();
        g2.drawImage(image, 0, 0, WIDTH*(int)SCALE, HEIGHT*(int)SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent e) {
        gameStateManager.keyTyped(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        gameStateManager.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        gameStateManager.keyReleased(e);
    }
}
