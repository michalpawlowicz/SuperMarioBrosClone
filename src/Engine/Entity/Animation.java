package Engine.Entity;

import java.awt.image.BufferedImage;

/**
 * Created by Michał Pawłowicz on 04.02.17.
 */
public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private long delay;
    private long startTime;

    public Animation(BufferedImage[] frames, long delay){
        this.frames = frames;
        this.delay = delay;
        currentFrame = 0;
    }

    public void update(){
        if((System.nanoTime() - startTime)/1e6 > delay){
            startTime = System.nanoTime();
            currentFrame++;
        }
    }

    public BufferedImage getImage(){
        if(currentFrame >= frames.length) currentFrame = 0;
        return frames[currentFrame];
    }

    public void setFrames(BufferedImage[] frames, long delay){
        this.frames = frames;
        this.delay = delay;
    }
}
