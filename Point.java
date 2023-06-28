import java.awt.*;

class Point implements Figure{
    public R2Point point;

    public Point(R2Point point){
        this.point = point;
    }

    public double perimeter(){
        return 0.0;
    }

    public double area(){
        return 0.0;
    }

    public Figure addPoint(R2Point point){
        if(!point.equals(this.point))
            return new Segment(this.point, point);
        else
            return this;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawOval((int)point.getX(), (int)point.getY(), 1,1);
    }
}