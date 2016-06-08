package Views;

import Views.GeometricObject;
import Views.GraphicObject;

import java.awt.*;

/**
 * Created by vlad on 25.05.16.
 */
public class Line implements GraphicObject, GeometricObject {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line() {
        this(0, 0, 0, 0);
    }

    public void draw(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }

    public void moveXY(int deltaX, int deltaY) {
        x1 += deltaX;
        x2 += deltaX;
        y1 += deltaY;
        y2 += deltaY;
    }
}
