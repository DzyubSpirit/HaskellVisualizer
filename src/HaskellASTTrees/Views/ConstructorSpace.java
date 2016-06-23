package HaskellASTTrees.Views;

import HaskellASTTrees.DataTypes.HString;
import HaskellASTTrees.MyGraphics.GeometricObject;
import HaskellASTTrees.Tools.Tool;
import HaskellASTTrees.Tools.ToolChangingManager;
import HaskellASTTrees.Trees.AbstractTree;
import HaskellASTTrees.Trees.AbstractTreeObserver;
import HaskellASTTrees.Trees.ConstantValueTree;
import HaskellASTTrees.MyGraphics.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vlad on 25.05.16.
 */
public class ConstructorSpace extends JPanel implements AbstractTreeObserver {
    private List<AbstractTreeView> treeViews = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    private Line curLink = null;
    {
        MouseAdapter handMouseAdapter = new HandMouseAdapter();
        new ToolChangingManager(this)
                .addMouseAdapter(handMouseAdapter)
                .saveToolAdapters(Tool.HAND)
                .addMouseAdapter(handMouseAdapter)
                .saveToolAdapters(Tool.LINK)
                .itIsAll();
   }


    public ConstructorSpace() {
        setLayout(null);

        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
    }

    public void addCurLink(AbstractTreeView treeView) {
        int x = treeView.getX() + treeView.getWidth() / 2;
        int y = treeView.getY() + treeView.getHeight() / 2;
        curLink = new Line(x, y, x, y);
    }

    public void modifyCurLink(int x, int y) {
        AbstractTreeView treeView = findTreeView(x, y);

        if (treeView != null) {
            curLink.x2 = treeView.getX() + treeView.getWidth() / 2;
            curLink.y2 = treeView.getY() + treeView.getHeight() / 2;
        } else {
            curLink.x2 = x;
            curLink.y2 = y;
        }
        repaint();
    }

    public void releaseCurLink() {
        curLink = null;
        repaint();
    }

    public AbstractTreeView findTreeView(double x, double y) {
        AbstractTreeView selected = null;
        for (AbstractTreeView treeView : treeViews) {
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
    public void childAdded(AbstractTree parent, AbstractTree newChild) {

    }

    @Override
    public void childRemoved(AbstractTree parent, AbstractTree oldChild) {

    }

    @Override
    public void parentChanged(AbstractTree child, AbstractTree oldParent) {
        List<AbstractTreeView> childViews = child.getTreeViews();
        links = links.stream()
                     .filter((Link link) -> !link.getSource().isViewOf(child)
                                         || !link.getTarget().isViewOf(oldParent))
                     .collect(Collectors.toList());
        AbstractTree newParent = child.getParent();
        if (newParent != null) {
            List<AbstractTreeView> parentViews = newParent.getTreeViews();
            for (AbstractTreeView childView : childViews) {
                for (AbstractTreeView parentView : parentViews) {
                    Link link = new Link(childView, parentView, this);
                    links.add(link);
                }
            }
        }
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
            ConstantValueTree tree = new ConstantValueTree<>(new HString("123"));
            AbstractTreeView newElem =
                    new ConstantValueView(ConstructorSpace.this, tree);

            newElem.setBounds(e.getX() - 50, e.getY() - 55, 100, 110);

            //TreeView newElem = new TreeView(ConstructorSpace.this,
            //        new ConstantValueTree(new HInt(42)), e.getX(), e.getY());
            treeViews.add(newElem);
            tree.addTreeView(newElem);
            add(newElem);
            repaint();
        }
    }
}
