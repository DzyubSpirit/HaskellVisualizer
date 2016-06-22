package HaskellASTTrees;

import HaskellASTTrees.Tools.Tool;
import HaskellASTTrees.Tools.ToolChangeObserver;
import HaskellASTTrees.Views.ConstructorSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by vlad on 24.05.16.
 */
public class MainFrame extends JFrame {
    public static final String title = "Tree constructor";
    public static final int width = 640;
    public static final int height = 480;
    public static final int titleHeight = 30;

    private ConstructorSpace constructorSpace;

    MainFrame() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.VERTICAL);
        JButton handButton = new JButton();
        try {
            ImageIcon ii = new ImageIcon("res/hand.png");
            handButton.setIcon(ii);
        } catch (Exception e) {
            handButton.setText("Hand button!");
        }
        handButton.addActionListener((ActionEvent e) -> {
            ToolChangeObserver.getInstance().toolChanged(Tool.HAND);
        });
        toolBar.add(handButton);
        JButton linkButton = new JButton();
        try {
            ImageIcon ii = new ImageIcon("res/arrow.png");
            linkButton.setIcon(ii);
        } catch (Exception e) {
            linkButton.setText("Link button!");
        }
        linkButton.addActionListener((ActionEvent e) -> {
            ToolChangeObserver.getInstance().toolChanged(Tool.LINK);
        });
        toolBar.add(linkButton);

        add(toolBar, BorderLayout.WEST);

        constructorSpace = new ConstructorSpace();
        constructorSpace.setBounds(50, 50, width-100, height - 100);
        add(constructorSpace);
        ToolChangeObserver.getInstance().toolChanged(Tool.HAND);
        this.pack();

        setTitle(title);
        setSize(width, height+titleHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}