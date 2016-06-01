import javax.swing.*;
import java.awt.*;

/**
 * Created by vlad on 26.05.16.
 */
public interface GraphicObject {
    void draw(Graphics g);
    void repaint(JPanel panel);
    void moveXY(int deltaX, int deltaY);
}
