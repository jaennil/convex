import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Panel panel = new Panel();
        add(panel);
    }

}
