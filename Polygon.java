import java.awt.*;
import java.util.ArrayDeque;

class Polygon extends ArrayDeque<R2Point> implements Figure{
    private double s, p;

    public Polygon(R2Point a, R2Point b, R2Point c){
        push(b);

        if (b.light(a, c)){
            push(a);
            addLast(c);
        }
        else{
            push(c);
            addLast(a);
        }

        p = R2Point.dist(a, b) + R2Point.dist(b, c)+ R2Point.dist(c, a);
        s = Math.abs(R2Point.area(a, b, c));
    }

    public double perimeter(){
        return p;
    }

    public double area(){
        return s;
    }

    private void grow(R2Point a, R2Point b, R2Point t){
        p -= R2Point.dist(a, b);
        s += Math.abs(R2Point.area(a, b, t));
    }

    public Figure addPoint(R2Point t){
        int i;
        //Ищем освещенные ребра, просматривая их одно за другим.
        for(i = size(); i>0 && !t.light(getLast(), getFirst()); i--)
            addLast(pop());

        //УТВЕРЖДЕНИЕ:
        //либо ребро [back(), front()] освещено из t,
        //либо освещенных ребер нет совсем.
        if (i>0){
            R2Point x;
            grow(getLast(), getFirst(), t);

            //Удаляем все освещенные ребра из начала дека.
            for(x = pop(); t.light(x, getFirst()); x = pop())
                grow(x, getFirst(), t);
            push(x);

            //Удаляем все освещенные ребра из конца дека.
            for (x = removeLast(); t.light(getLast(), x); x = removeLast())
                grow(getLast(), x, t);
            addLast(x);

            //Завершаем обработку добавляемой точки.
            p += R2Point.dist(getLast(), t) + R2Point.dist(t, getFirst());
            push(t);
        }
        return this;
    }

    @Override
    public void draw(Graphics graphics) {
        int[] xList = new int[this.size()];
        int[] yList = new int[this.size()];
        int i = 0;

        for (R2Point point : this) {
            xList[i] = (int)point.getX();
            yList[i] = (int)point.getY();
            i++;
        }

        graphics.drawPolygon(xList, yList, this.size());
    }
}