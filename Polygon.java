import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

class Polygon extends ArrayDeque<R2Point> implements Figure {
    private double s, p;

    public Polygon(R2Point a, R2Point b, R2Point c) {
        push(b);

        if (b.light(a, c)) {
            push(a);
            addLast(c);
        } else {
            push(c);
            addLast(a);
        }

        p = R2Point.dist(a, b) + R2Point.dist(b, c) + R2Point.dist(c, a);
        s = Math.abs(R2Point.area(a, b, c));
    }

    public double perimeter() {
        return p;
    }

    public double area() {
        return s;
    }

    private void grow(R2Point a, R2Point b, R2Point t) {
        p -= R2Point.dist(a, b);
        s += Math.abs(R2Point.area(a, b, t));
    }

    public Figure addPoint(R2Point t) {
        int i;
        //Ищем освещенные ребра, просматривая их одно за другим.
        for (i = size(); i > 0 && !t.light(getLast(), getFirst()); i--)
            addLast(pop());

        //УТВЕРЖДЕНИЕ:
        //либо ребро [back(), front()] освещено из t,
        //либо освещенных ребер нет совсем.
        if (i > 0) {
            R2Point x;
            grow(getLast(), getFirst(), t);

            //Удаляем все освещенные ребра из начала дека.
            for (x = pop(); t.light(x, getFirst()); x = pop())
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
            xList[i] = (int) point.getX();
            yList[i] = (int) point.getY();
            i++;
        }

        graphics.drawPolygon(xList, yList, this.size());
    }

    @Override
    public double upperPlaneArea() {
        ArrayList<R2Point> points = new ArrayList<>();
        for (R2Point point : this) {
            points.add(point);
        }
        points.addAll(findIntersectionPoints(points));

        // формула гаусса
        double sum = 0;
        for (int i = 0; i < points.size()-1; i++) {
            sum += points.get(i).getX()*points.get(i+1).getY();
        }
        sum += points.get(points.size()-1).getX()*points.get(0).getY();
        double difference = 0;
        for (int i = 0; i < points.size()-1; i++) {
            difference -= points.get(i).getY()*points.get(i+1).getX();
        }
        difference -= points.get(points.size()-1).getY()*points.get(0).getX();
        return 0.5*(sum+difference);
    }

    public static ArrayList<R2Point> findIntersectionPoints(ArrayList<R2Point> points) {
        ArrayList<R2Point> intersectionPoints = new ArrayList<>();

        // Находим крайнюю левую и крайнюю правую точки
        R2Point leftmost = points.get(0);
        R2Point rightmost = points.get(0);
        for (R2Point point : points) {
            if (point.getX() < leftmost.getX()) {
                leftmost = point;
            }
            if (point.getX() > rightmost.getX()) {
                rightmost = point;
            }
        }

        // Находим первое пересечение с осью X
        double intersectionX = ((0 - leftmost.getY()) * (rightmost.getX() - leftmost.getX()) / (rightmost.getY() - leftmost.getY())) + leftmost.getX();
        intersectionPoints.add(new R2Point(intersectionX, 0));

        // Ищем пересечения с осью X для соседних точек
        for (int i = 0; i < points.size(); i++) {
            R2Point current = points.get(i);
            R2Point next = points.get((i + 1) % points.size()); // Циклический доступ к следующей точке

            // Проверяем, что линия проходит через ось X
            if ((current.getY() > 0 && next.getY() < 0) || (current.getY() < 0 && next.getY() > 0)) {
                // Находим пересечение с осью X
                double intersectionX2 = ((0 - current.getY()) * (next.getX() - current.getX()) / (next.getY() - current.getY())) + current.getX();
                intersectionPoints.add(new R2Point(intersectionX2, 0));
            }
        }

        return intersectionPoints;
    }
}