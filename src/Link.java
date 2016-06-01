import java.awt.*;
import javax.swing.JPanel;

/**
 * Created by vlad on 01.06.16.
 */
public class Link implements TreeViewBoundsListener,GraphicObject {
    private TreeView source;
    private TreeView target;
    private Line line = new Line();

    public Link(TreeView source, TreeView target) {
        this.source = source;
        this.target = target;
        source.addBoundsListener(this);
        target.addBoundsListener(this);
        calcLine();
    }

    private void calcLine() {
        calcSourcePoint();
        calcTargetPoint();
    }

    private void calcSourcePoint() {
        line.x1 = source.getX() + source.getWidth()/2;
        line.y1 = source.getY() + source.getHeight()/2;
    }

    private void calcTargetPoint() {
        line.x2 = target.getX() + target.getWidth()/2;
        line.y2 = target.getY() + target.getHeight()/2;
    }

    public void newBounds(Object source, Rectangle r) {
        if (this.source == source) {
            line.x1 = r.x + r.width/2;
            line.y1 = r.y + r.height/2;
        }
        if (source == target) {
            line.x2 = r.x + r.width/2;
            line.y2 = r.y + r.height/2;
        }
    }

    public void draw(Graphics g) {
        line.draw(g);
    }

}
