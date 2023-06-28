import java.awt.*;

class Void implements Figure{
    public double perimeter(){
        return 0.0;
    }

    public double area(){
        return 0.0;
    }

    public Figure addPoint(R2Point p){
        return new Point(p);
    }

    @Override
    public void draw(Graphics graphics) {}
}
