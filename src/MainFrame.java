import javax.swing.*;

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
        setLayout(null);

        constructorSpace = new ConstructorSpace();
        constructorSpace.setBounds(50, 50, width-100, height - 100);
        add(constructorSpace);
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