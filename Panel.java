import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel implements Runnable {

    private final Convex convex;
    private final static int width = 400;
    private final static int height = 400;

    public Panel(int width, int height) {
        convex = new Convex();
        setSize(width, height);
        setBackground(Color.BLACK);
        Thread thread = new Thread(this, "scanner thread");
        thread.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.clearRect(0,0,width,height);
        convex.draw(graphics);
    }

    @Override
    public void run() {
        while (true) {
            convex.add(new R2Point());
            System.out.println("S = " + convex.area() + " P = " + convex.perimeter());
            repaint();
        }
    }
}
