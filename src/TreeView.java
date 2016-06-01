import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vlad on 26.05.16.
 */
public class TreeView extends JComponent implements ToolChangeObservable,
                                                    GraphicObject,
                                                    GeometricObject {
    private ConstructorSpace constructorSpace;
    private Tree tree;
    private List<TreeViewBoundsListener> boundsListeners = new ArrayList<>();
    private HashMap<Tool, List<MouseAdapter>> toolMouseAdapters = new HashMap<>();
    {
        List<MouseAdapter> mouseAdapters = new ArrayList<>();
        mouseAdapters.add(new HandMouseAdapter());
        toolMouseAdapters.put(Tool.HAND, mouseAdapters);
        mouseAdapters = new ArrayList<>();
        mouseAdapters.add(new LinkMouseAdapter());
        toolMouseAdapters.put(Tool.LINK, mouseAdapters);

        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public TreeView(ConstructorSpace constructorSpace, Tree tree, int mouseX, int mouseY) {
        this.constructorSpace = constructorSpace;
        this.tree = tree;
        setBounds(mouseX-25, mouseY-25, 50, 50);
        setLayout(null);
        JLabel name = new JLabel(tree.getName());
//        name.setBorder(BorderFactory.create());
        name.setBounds(2, 2, 46, 20);
        add(name);
    }

    public void draw(Graphics g) { }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.pink);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void moveXY(int deltaX, int deltaY) {
        setLocation(getX()+deltaX, getY()+deltaY);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        boundsListeners.forEach((TreeViewBoundsListener listener) -> {
            listener.newBounds(this);
        });
    }

    public void addBoundsListener(TreeViewBoundsListener listener) {
        boundsListeners.add(listener);
    }

    private class EditOnClickLabel extends JTextField {

    }

    @Override
    public void toolChanged(Tool tool) {
        List<MouseAdapter> mouseAdapters = toolMouseAdapters.get(tool);
        if (mouseAdapters != null) {
            ToolChangeObserver.setNewMouseAdapters(this, mouseAdapters);
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
//            System.out.println("Mouse dragged: "+e.getX()+" "+e.getY());
            Point curPoint = e.getPoint();
            setBounds(getX()+curPoint.x-dragPoint.x,
                    getY()+curPoint.y-dragPoint.y, getWidth(), getHeight());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.println("TreeView clicked");
        }

    }

    private class LinkMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point loc = getLocation();
            constructorSpace.modifyCurLink(loc.x+e.getX(), loc.y+e.getY());
//            System.out.println("Drag");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            constructorSpace.addCurLink(TreeView.this);
//            System.out.println("TreeView pressed");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            TreeView treeView = constructorSpace.findTreeView(
                    TreeView.this.getX() + e.getX(),
                    TreeView.this.getY() + e.getY());
            boolean isGoodLink = treeView != null;
            if (isGoodLink) {
                TreeView.this.tree.addChild(treeView.tree);
            }
            constructorSpace.releaseCurLink(TreeView.this, treeView, isGoodLink);
//            System.out.println("TreeView mouse released");
        }
    }
}
