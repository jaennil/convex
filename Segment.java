import java.awt.*;

class Segment implements Figure{
    public R2Point p, q;

    public Segment(R2Point p, R2Point q){
        this.p = p;
        this.q = q;
    }

    public double perimeter(){
        return 2.0 * R2Point.dist(p, q);
    }

    public double area(){
        return 0.0;
    }

    public Figure addPoint(R2Point r){
        if(R2Point.isTriangle(p, q, r))
            return new Polygon(p, q, r);

        if(q.inside(p, r))
            q = r;

        if(p.inside(r, q))
            p = r;

        return this;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawLine((int)p.getX(), (int)p.getY(), (int)q.getX(), (int)q.getY());
    }

    @Override
    public double upperPlaneArea() {
        return 0;
    }
}
