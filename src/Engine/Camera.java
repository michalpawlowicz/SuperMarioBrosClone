package Engine;

import java.awt.*;

/**
 * Created by Michał Pawłowicz on 10.02.17.
 */
public class Camera {
    private Point position;
    private int height;
    private int width;
    private Point center;
    public Camera(Point position, int width, int height){
        this.position = position;
        this.width = width;
        this.height = height;
    }
}
