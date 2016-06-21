package HaskellASTTrees.Views;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by vlad on 08.06.16.
 */
public abstract class AbstractTreeView extends JComponent {
    protected class LabelField extends JPanel {
        private JTextField field;
        private JLabel label;

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

        private class ToTreeViewMouseAdapter extends MouseAdapter {
            @Override
            public void mousePressed(MouseEvent e) {
                AbstractTreeView.this.processMouseEvent(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                AbstractTreeView.this.processMouseMotionEvent(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                AbstractTreeView.this.processMouseEvent(e);
            }
        }
    }}
