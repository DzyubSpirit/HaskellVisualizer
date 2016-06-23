package HaskellASTTrees.Views;

import HaskellASTTrees.MyGraphics.GeometricObject;
import HaskellASTTrees.MyGraphics.GraphicObject;
import HaskellASTTrees.Tools.Tool;
import HaskellASTTrees.Tools.ToolChangingManager;
import HaskellASTTrees.Trees.AbstractTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 08.06.16.
 */
public abstract class AbstractTreeView extends JComponent implements
        GraphicObject,
        GeometricObject {
    private ConstructorSpace constructorSpace;
    private AbstractTree tree;
    private List<TreeViewBoundsListener> boundsListeners = new ArrayList<>();
    {
        System.out.println("Before toolChangingManager");
        new ToolChangingManager(this)
            .addMouseAdapter(new HandMouseAdapter())
            .saveToolAdapters(Tool.HAND)
            .addMouseAdapter(new LinkMouseAdapter())
            .saveToolAdapters(Tool.LINK)
            .itIsAll();
        System.out.println("After toolChangingManager");
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void addBoundsListener(TreeViewBoundsListener tvbListener) {
        boundsListeners.add(tvbListener);
    }

    public AbstractTreeView(ConstructorSpace constructorSpace, AbstractTree tree) {
        this.constructorSpace = constructorSpace;
        this.tree = tree;
        tree.addObserver(constructorSpace);
    }

    public boolean isViewOf(AbstractTree tree) {
        return this.tree == tree;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.pink);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        boundsListeners.forEach((TreeViewBoundsListener listener) -> {
            listener.newBounds(this);
        });
    }


    public void moveXY(int deltaX, int deltaY) {
        setLocation(getX()+deltaX, getY()+deltaY);
    }

    public class ToTreeViewMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            AbstractTreeView.this.processMouseEvent(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            AbstractTreeView.this.processMouseMotionEvent(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) { AbstractTreeView.this.processMouseEvent(e);
        }
    }


    private class HandMouseAdapter extends MouseAdapter {
        private Point dragPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            dragPoint = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point curPoint = e.getPoint();
            setBounds(getX()+curPoint.x-dragPoint.x,
                    getY()+curPoint.y-dragPoint.y, getWidth(), getHeight());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

    }

    private class LinkMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point loc = getLocation();
            constructorSpace.modifyCurLink(loc.x+e.getX(), loc.y+e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            constructorSpace.addCurLink(AbstractTreeView.this);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            AbstractTreeView treeView = constructorSpace.findTreeView(
                    AbstractTreeView.this.getX() + e.getX(),
                    AbstractTreeView.this.getY() + e.getY());
            if (treeView != null) {
                AbstractTree parentTree = treeView.tree;
                AbstractTree childTree = AbstractTreeView.this.tree;
                AbstractTree oldParent = childTree.getParent();
                if (oldParent != null) {
                    oldParent.removeChild(childTree);
                }
                boolean isGoodLink = parentTree.isGoodParentFor(childTree);
                if (isGoodLink) {
                    parentTree.addChild(childTree);
                } else {
                    if (oldParent != null) {
                        oldParent.addChild(childTree);
                    }
                    System.out.println("Not a tree");
                }
            }
            constructorSpace.releaseCurLink();
        }
    }
}
