package Game.States;

import Engine.GameState;
import Engine.GameStateManager;
import Game.Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 */
public class MenuState extends GameState {

    private GameStateManager<String> gameStateManager;

    //Options
    private String[] options = {
            "Start",
            "Help",
            "Quit"
    };
    private int option = 0;
    private Color currentOptionColor;
    private Color optionsColor;
    private Font font;

    //Title
    private String title = "Square Game";
    private Font titleFont;
    private Color titleColor;

    public MenuState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        titleFont = new Font(
                "Century Gothic",
                Font.PLAIN,
                16);

        font = new Font("Arial", Font.PLAIN, 12);

        currentOptionColor = Color.RED;
        optionsColor = Color.BLACK;
        titleColor = Color.orange;
    }

    @Override
    public void init() {
        System.out.println("Menu state inicialization");
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        //Clear window
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        //Drawing title
        g.setColor(titleColor);
        g.drawString(title, 120, 70);

        //Draw options
        for(int i=0; i<options.length; i++){
            if(i == option){
                g.setColor(currentOptionColor);
            } else {
                g.setColor(optionsColor);
            }
            g.drawString(options[i], 140, 100 + i*15);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            option--;
            if(option < 0){
                option = options.length - 1;
            }
        } else if (code == KeyEvent.VK_DOWN){
            option++;
            if(option >= options.length){
                option = 0;
            }
        } else if(code == KeyEvent.VK_ENTER){
            if(option == 2){
                System.exit(0);
            } else if(option == 0){
                gameStateManager.setCurrentState("Level1");
                System.out.println(gameStateManager.getCurrentStateKey());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
