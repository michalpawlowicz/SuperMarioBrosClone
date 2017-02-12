package Game.Main;

import javax.swing.*;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 */
public class Game {
    public static void main(String[] args){
        JFrame frame = new JFrame("Square Game");
        GamePanel gamePanel = new GamePanel();
        frame.setContentPane(gamePanel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.pack();
        frame.setVisible(true);
    }
}
