import java.awt.*;

interface Figure{
    public double perimeter();
    public double area();
    public Figure addPoint(R2Point p);
    public void draw(Graphics graphics);

//    public double upperPlaneArea();
}