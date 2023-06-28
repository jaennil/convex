import javax.swing.*;

public class Window extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public Window() {
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        Panel panel = new Panel(WIDTH, HEIGHT);
        add(panel);
    }
}
