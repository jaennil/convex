import java.awt.*;

class Convex{
    private Figure figure;

    public Convex(){
        figure = new Void();
    }

    public void add(R2Point point){
        figure = figure.addPoint(point);
    }

    public double area(){
        return figure.area();
    }

    public double perimeter(){
        return figure.perimeter();
    }

    public double upperPlaneArea(){
        return figure.upperPlaneArea();
    }

    public void draw(Graphics graphics) {
        figure.draw(graphics);
    }
}