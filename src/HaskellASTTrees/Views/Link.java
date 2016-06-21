package HaskellASTTrees.Views;

import HaskellASTTrees.MyGraphics.GraphicObject;
import HaskellASTTrees.MyGraphics.Line;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Created by vlad on 01.06.16.
 */
public class Link implements TreeViewBoundsListener, GraphicObject {
    public static final int targetBallRadius = 7;

    private TreeView source;
    private TreeView target;
    private Line line = new Line();
    private JPanel parent;

    public Link(TreeView source, TreeView target, JPanel parent) {
        this.source = source;
        this.target = target;
        source.addBoundsListener(this);
        target.addBoundsListener(this);
        calcLine();
        this.parent = parent;
    }

    public Link(TreeView source, TreeView target) {
        this(source, target, null);
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

    public void newBounds(Object source) {
        boolean isSource = this.source == source;
        boolean isTarget = this.target == source;
        if (isSource) {
            calcSourcePoint();
        }
        if (isTarget) {
            calcTargetPoint();
        }
        if (parent != null && (isSource || isTarget)) {
            parent.repaint();
        }
    }

    public void draw(Graphics g) {
        line.draw(g);
        Rectangle tr = target.getBounds();
        boolean rightDirection = line.x1 > line.x2;
        boolean topDirection = line.y1 < line.y2;
        double dx = line.x1 - line.x2;
        double dy = line.y1 - line.y2;
        long nx;
        long ny;
        if (dx != 0) {
            if (rightDirection) {
                ny = line.y2 + Math.round(dy / dx * (tr.x + tr.width - line.x2));
                if (topDirection) {

                    if (ny < tr.y) {
                        ny = tr.y;
                        nx = line.x2 + Math.round(dx / dy * (tr.y - line.y2));
                    } else {
                        nx = tr.x + tr.width;
                    }
                } else {
                    if (ny > tr.y+tr.height) {
                        ny = tr.y + tr.height;
                        nx = line.x2 + Math.round(dx/dy*(tr.y+tr.height-line.y2));
                    } else {
                        nx = tr.x + tr.width;
                    }
                }
            } else {
                ny = line.y2 + Math.round(dy / dx * (tr.x - line.x2));
                if (topDirection) {
                    if (ny < tr.y) {
                        ny = tr.y;
                        nx = line.x2 + Math.round(dx / dy * (tr.y - line.y2));
                    } else {
                        nx = tr.x;
                    }
                } else {
                    if (ny > tr.y + tr.height) {
                        ny = tr.y + tr.height;
                        nx = line.x2 + Math.round(dx / dy * (tr.y + tr.height - line.y2));
                    } else {
                        nx = tr.x;
                    }
                }
            }

        } else {
            nx = line.x1;
            ny = line.y1 > line.y2 ? tr.y+tr.height : tr.y;
        }
        int lx = (int)nx - targetBallRadius;
        int bx = (int)ny - targetBallRadius;
        g.setColor(Color.blue);
        g.fillOval(lx, bx, 2*targetBallRadius, 2*targetBallRadius);
        g.setColor(Color.black);
    }

}
