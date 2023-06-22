import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable{

    private final Convex convex;
    public Panel() {
        convex = new Convex();
        setSize(400, 400);
        setBackground(Color.BLACK);
        Thread thread = new Thread(this, "scanner thread");
        thread.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        R2Point lastPoint = convex.lastPoint;
        if (lastPoint == null) return;
        graphics2D.fillOval((int) lastPoint.getX(), (int) lastPoint.getY(), 5, 5);
        graphics.drawString(lastPoint.getX() + " " + lastPoint.getY(), (int) lastPoint.getX(), (int) lastPoint.getY());
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
