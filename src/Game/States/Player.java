package Game.States;

import Engine.Entity.Animation;
import Engine.Entity.MapObject;
import Engine.TileMap.TileMap;
import Game.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Michał Pawłowicz on 08.02.17.
 */

public class Player extends MapObject {


    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean down;

    //Movement attributes
    //Walking/running
    private double maxMovementSpeed;
    private double movementSpeed;
    private double stopSpeed;
    //Falling
    private double maxFallingSpeed;
    private double fallingSpeed;

    //Jumping
    private double jumpingSpeed;
    private double stopJumpSpeed;

    //Animations
    private ArrayList<BufferedImage[]> animations;


    //Actions
    public static final int WALKING = 0;
    public static final int STANDING = 1;
    public static final int JUMPING = 2;
    public static final int FALLING = 3;
    public int currentAction = WALKING;

    private final int[] frameNum = {
            2, 1, 2, 2
    };

    public Player(TileMap tileMap) {
        super(tileMap);

        maxMovementSpeed = 3;
        movementSpeed = 1;
        maxFallingSpeed = 5;
        fallingSpeed = 0.5;
        stopSpeed = 1.0;

        width = 12;
        height = 16;

        jumpingSpeed = -8.5;
        stopJumpSpeed = 0.1;

        position = new Point(0, GamePanel.HEIGHT - 50);

        //Importing sprites
        animations = new ArrayList<>();
        try {
            //Import walk sprites
            BufferedImage[] walkSprites = new BufferedImage[3];
            BufferedImage walkingSheet = ImageIO.read(getClass().getResourceAsStream("/MarioSheets/walkingMarioSmall.png"));

            walkSprites[0] = walkingSheet.getSubimage(0, 0, 12 ,15);
            walkSprites[1] = walkingSheet.getSubimage(12, 0, 12, 15);
            walkSprites[2] = walkingSheet.getSubimage(24, 0, 12, 15);
            animations.add(walkSprites);

            //Adding animation of standing
            BufferedImage[] standingSprites = new BufferedImage[1];
            standingSprites[0] = ImageIO.read(getClass().getResourceAsStream("/MarioSheets/standingSmallMario.png"));
            animations.add(standingSprites);

            //Adding animation of jumping
            BufferedImage[] jumpingSprites = new BufferedImage[1];
            jumpingSprites[0] = ImageIO.read(getClass().getResourceAsStream("/MarioSheets/SmallMarioJumping.png"));
            animations.add(jumpingSprites);

        } catch (IOException e) {
            e.printStackTrace();
        }

        animation = new Animation(animations.get(WALKING), 100);

    }

    public void update() {
        nextPosition();
        checkCollision();
        setPosition();
        animation.update();
    }

    public void nextPosition(){
        if(left){
            dx -= movementSpeed;
            if(dx < -maxMovementSpeed){
                dx = -maxMovementSpeed;
            }
        } else if(right){
            dx += movementSpeed;
            if(dx > maxMovementSpeed){
                dx = maxMovementSpeed;
            }
        } else {
            if(dx > 0){
                dx -= stopSpeed;
                if(dx < 0) dx = 0;
            } else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0) dx = 0;
            }
        }

        if(jumping && !falling){
            dy = jumpingSpeed;
            falling = true;
        }
        if(falling){
            dy += fallingSpeed;
            if(dy > 0){
                jumping = false;
            }
            if(dy < 0 && !jumping){
                dy += stopJumpSpeed;
            }
            if(dy > maxFallingSpeed){
                dy = maxFallingSpeed;
            }
        }

    }

    public void draw(Graphics2D g){
        if(currentAction == STANDING){
            animation.setFrames(animations.get(STANDING), 100);
        } else if(currentAction == WALKING){
            animation.setFrames(animations.get(WALKING), 100);
        } else if(currentAction == JUMPING){
            animation.setFrames(animations.get(JUMPING), 100);
        }
        if(facingRight)
            g.drawImage(
                    animation.getImage(),
                    (int)position.getX() + (int)tileMap.getPosition().getX(),
                    (int)position.getY(),
                    width, height, null);
        else g.drawImage(
                animation.getImage(),
                (int)position.getX() +(int)tileMap.getPosition().getX() + width,
                (int)position.getY(),
                -width, height, null);
    }

    public void setLeft(boolean b){
        this.left = b;
    }
    public void setRight(boolean b){
        this.right = b;
    }
    public void setJumping(boolean b){
        this.jumping = b;
    }
    public void setDown(boolean b){
        this.down = b;
    }
    public void setAction(int currentAction){ this.currentAction = currentAction; }

}
