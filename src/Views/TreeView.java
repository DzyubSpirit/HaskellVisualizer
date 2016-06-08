package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
//    private String name;
//    private
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
        setBounds(mouseX - 50, mouseY - 25, 100, 50);
        setLayout(null);
        LabelField labelField = new LabelField();
        labelField.setBounds(2, 2, 96, 20);
        add(labelField);
    }

    private class LabelField extends JPanel {
        JTextField field;
        JLabel label;

        public LabelField(String text) {
            setLayout(null);

            field = new JTextField(text);
            field.setVisible(false);
            field.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    textEnterEnd();
                }
            });
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                        textEnterEnd();
                    }
                }
            });

            label = new JLabel(text);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    label.setVisible(false);
                    field.setVisible(true);
                    field.requestFocus();
                }

            });
            ToTreeViewMouseAdapter toTreeViewMouseAdapter = new ToTreeViewMouseAdapter();
            label.addMouseListener(toTreeViewMouseAdapter);
            label.addMouseMotionListener(toTreeViewMouseAdapter);

            field.setBounds(getBounds());
            label.setBounds(getBounds());
            add(field);
            add(label);
        }

        public LabelField() {
            this("undefined");
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            calcInners();
        }

        private void textEnterEnd() {
            label.setText(field.getText());
            field.setVisible(false);
            label.setVisible(true);
        }

        private void calcInners() {
            field.setBounds(getBounds());
            label.setBounds(getBounds());
        }

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

    private class ToTreeViewMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            TreeView.this.processMouseEvent(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            TreeView.this.processMouseMotionEvent(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            TreeView.this.processMouseEvent(e);
        }
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
            constructorSpace.addCurLink(TreeView.this);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            TreeView treeView = constructorSpace.findTreeView(
                    TreeView.this.getX() + e.getX(),
                    TreeView.this.getY() + e.getY());
            boolean isGoodLink = treeView != null && treeView.tree != tree;
            if (isGoodLink) {
                TreeView.this.tree.addChild(treeView.tree);
            }
            constructorSpace.releaseCurLink(TreeView.this, treeView, isGoodLink);
        }
    }
}
