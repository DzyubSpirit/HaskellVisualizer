package HaskellASTTrees.Views;

import HaskellASTTrees.Trees.AbstractTree;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by vlad on 23.06.16.
 */
public class LabelField extends JPanel {
    JTextField field;
    JLabel label;

    public LabelField(AbstractTreeView tree, String text) {
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
        AbstractTreeView.ToTreeViewMouseAdapter toTreeViewMouseAdapter =
                tree.new ToTreeViewMouseAdapter();
        label.addMouseListener(toTreeViewMouseAdapter);
        label.addMouseMotionListener(toTreeViewMouseAdapter);

        add(field);
        add(label);
        calcInners();
    }

    public LabelField(AbstractTreeView treeView) {
        this(treeView, "undefined");
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
        label.setLocation(0, 0);
        label.setSize(getSize());
        field.setLocation(0, 0);
        field.setSize(getSize());
    }

}

