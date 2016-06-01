import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vlad on 25.05.16.
 */
public class ConstructorSpace extends JPanel implements ToolChangeObservable {
    private List<TreeView> treeViews = new ArrayList<>();
    private List<Line> links = new ArrayList<>();
    private HashMap<Tool, List<MouseAdapter>> toolMouseAdapters = new HashMap<>();
    private Line curLink = null;
    {
        List<MouseAdapter> handMouseAdapters = new ArrayList<>();
        handMouseAdapters.add(new HandMouseAdapter());
        toolMouseAdapters.put(Tool.HAND, handMouseAdapters);
        toolMouseAdapters.put(Tool.LINK, handMouseAdapters);
    }


    public ConstructorSpace() {
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
    }


    public void toolChanged(Tool tool) {
        List<MouseAdapter> mouseAdapters = toolMouseAdapters.get(tool);
        if (mouseAdapters != null) {
            ToolChangeObserver.setNewMouseAdapters(this, mouseAdapters);
        }
    }

    public void addCurLink(TreeView treeView) {
        int x = treeView.getX() + treeView.getWidth() / 2;
        int y = treeView.getY() + treeView.getHeight() / 2;
        curLink = new Line(x, y, x, y);
        links.add(curLink);
    }

    public void modifyCurLink(int x, int y) {
        int oldX = curLink.x2;
        int oldY = curLink.y2;
        TreeView treeView = findTreeView(x, y);

        if (treeView != null) {
            curLink.x2 = treeView.getX() + treeView.getWidth() / 2;
            curLink.y2 = treeView.getY() + treeView.getHeight() / 2;
        } else {
            curLink.x2 = x;
            curLink.y2 = y;
        }

        int minX = Math.min(Math.min(curLink.x1, curLink.x2), oldX);
        int maxX = Math.max(Math.max(curLink.x1, curLink.x2), oldX);
        int minY = Math.min(Math.min(curLink.y1, curLink.y2), oldY);
        int maxY = Math.max(Math.max(curLink.y1, curLink.y2), oldY);
        repaint(minX, minY, maxX-minX+1, maxY-minY+1);
    }

    public void removeCurLink() {
        links.remove(curLink);
        curLink.repaint(this);
    }

    public TreeView findTreeView(double x, double y) {
        TreeView selected = null;
        for (TreeView treeView : treeViews) {
            int treeX = treeView.getX();
            int treeY = treeView.getY();
            int treeWidth = treeView.getWidth();
            int treeHeight = treeView.getHeight();

            if (x >= treeX && x <= treeX+treeWidth &&
                    y >= treeY && y <= treeY+treeHeight) {
                selected = treeView;
            }
        }
        return selected;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Line obj : links) {
            obj.draw(g);
        }

        for (TreeView treeView : treeViews) {
            treeView.draw(g);
        }
    }

    private class HandMouseAdapter extends MouseAdapter {
        Point dragPoint;

        @Override
        public void mouseDragged(MouseEvent e) {
            Point curPoint = e.getPoint();
            int dx = curPoint.x - dragPoint.x;
            int dy = curPoint.y - dragPoint.y;
            for (GraphicObject child : treeViews) {
                child.moveXY(dx, dy);
            }
            for (GraphicObject child : links) {
                child.moveXY(dx, dy);
            }
            dragPoint = curPoint;
            System.out.println("ConstructorSpace dragged");

        }

        @Override
        public void mousePressed(MouseEvent e) {
            dragPoint = e.getPoint();
            System.out.println("ConstructorSpace pressed");

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            TreeView newElem = new TreeView(ConstructorSpace.this, new Tree("max1"), e.getX(), e.getY());
            treeViews.add(newElem);
            add(newElem);
            ToolChangeObserver.getInstance().add(newElem);
            newElem.repaint();
            System.out.println("ConstructorSpace Clicked");
        }
    }
}
