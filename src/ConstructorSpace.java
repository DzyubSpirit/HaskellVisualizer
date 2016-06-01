import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 25.05.16.
 */
public class ConstructorSpace extends JPanel {
    private List<TreeView> children = new ArrayList<>();
    private final ConstructorSpace self;
    {
        self = this;
    }


    public ConstructorSpace() {
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreeView newElem = new TreeView(new Tree("max1"), e.getX(), e.getY());
                children.add(newElem);
                add(newElem);
                repaint();

                System.out.println("Mouse clicked: "+e.getX()+" "+e.getY());
            }
        });
    }


}
