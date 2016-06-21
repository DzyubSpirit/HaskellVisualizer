package HaskellASTTrees.Views;

import HaskellASTTrees.DataTypes.HInt;
import HaskellASTTrees.MyGraphics.GeometricObject;
import HaskellASTTrees.Tools.Tool;
import HaskellASTTrees.Tools.ToolChangeObservable;
import HaskellASTTrees.Tools.ToolChangeObserver;
import HaskellASTTrees.Trees.ConstantValueTree;
import HaskellASTTrees.MyGraphics.Line;

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
    private TreeView uppest;
    private List<Link> links = new ArrayList<>();
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
    }

    public void modifyCurLink(int x, int y) {
        TreeView treeView = findTreeView(x, y);

        if (treeView != null) {
            curLink.x2 = treeView.getX() + treeView.getWidth() / 2;
            curLink.y2 = treeView.getY() + treeView.getHeight() / 2;
        } else {
            curLink.x2 = x;
            curLink.y2 = y;
        }
        repaint();
    }

    public void releaseCurLink(TreeView source, TreeView target, boolean addAsLink) {
        if (addAsLink) {
            links.add(new Link(source, target, this));
        }
        curLink = null;
        repaint();
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

        for (Link obj : links) {
            obj.draw(g);
        }

        if (curLink != null) {
            curLink.draw(g);
        }
    }

    private class HandMouseAdapter extends MouseAdapter {
        Point dragPoint;

        @Override
        public void mouseDragged(MouseEvent e) {
            Point curPoint = e.getPoint();
            int dx = curPoint.x - dragPoint.x;
            int dy = curPoint.y - dragPoint.y;
            for (GeometricObject child : treeViews) {
                child.moveXY(dx, dy);
            }
            dragPoint = curPoint;
//            System.out.println("ConstructorSpace dragged");

        }

        @Override
        public void mousePressed(MouseEvent e) {
            dragPoint = e.getPoint();
//            System.out.println("ConstructorSpace pressed");

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            TreeView newElem = new TreeView(ConstructorSpace.this,
                    new ConstantValueTree(new HInt(42)), e.getX(), e.getY());
            treeViews.add(newElem);
            add(newElem);
            repaint();
//            System.out.println("ConstructorSpace Clicked");
        }
    }
}
