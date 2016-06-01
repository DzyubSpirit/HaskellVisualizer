import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by vlad on 01.06.16.
 */
public class TreeView extends JComponent {
    private Tree tree;
    {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.blue);
    }

    public TreeView(Tree tree, int mouseX, int mouseY) {
        this.tree = tree;
        setBounds(mouseX-25, mouseY-25, 50, 50);
        setLayout(null);
        JLabel name = new JLabel(tree.getName());
//        name.setBorder(BorderFactory.create());
        name.setBounds(2, 2, 46, 20);
        add(name);

        MouseAdapter mouseAdapter = new OwnMouseAdapter();

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private class NameTextField extends JTextField {

    }

    private class OwnMouseAdapter extends MouseAdapter {
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
}
