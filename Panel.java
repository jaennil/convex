import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel implements Runnable {

    private final Convex convex;
    private final int WIDTH;
    private final int HEIGHT;

    public Panel(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        convex = new Convex();
        setSize(width, height);
        setBackground(Color.BLACK);
        Thread thread = new Thread(this, "scanner thread");
        thread.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.clearRect(0,0,WIDTH,HEIGHT);
        graphics.translate(WIDTH/2, HEIGHT/2);
        graphics.drawLine(0,0, 10000, 0);
        graphics.drawLine(0,0, 0, 10000);
        graphics.drawLine(0,0, -10000, 0);
        graphics.drawLine(0,0, 0, -10000);
        convex.draw(graphics);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            convex.add(new R2Point());
            System.out.println("S = " + convex.area() + " P = " + convex.perimeter() + " площадь фигуры в верхней полуплоскости: " + convex.upperPlaneArea());
            repaint();
        }
    }
}
