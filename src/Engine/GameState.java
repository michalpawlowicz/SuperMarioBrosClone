package Engine;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by michal on 03.02.17.
 */
public abstract class GameState {
    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void keyReleased(KeyEvent e);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyTyped(KeyEvent e);
}
