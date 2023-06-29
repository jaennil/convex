import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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

//        graphics.drawPolygon(xList, yList, this.size());
        graphics.fillPolygon(xList, yList, this.size());
    }

    @Override
    public double upperPlaneArea() {
        ArrayList<R2Point> points = new ArrayList<>(this);
        ArrayList<R2Point> intersectionPoints = findIntersectionPoints(points);
        System.out.println(intersectionPoints);
        ArrayList<R2Point> areaPoints = new ArrayList<>();
        for (R2Point point : this) {
            if (point.getY() <= 0) {
                areaPoints.add(point);
            }
        }
        areaPoints.addAll(intersectionPoints);
        sortVerticesClockwise(areaPoints);
        System.out.println(areaPoints);

        // формула гаусса
        double sum = 0;
        for (int i = 0; i < areaPoints.size() - 1; i++) {
            sum += areaPoints.get(i).getX() * areaPoints.get(i + 1).getY();
        }
        sum += areaPoints.get(areaPoints.size() - 1).getX() * areaPoints.get(0).getY();
        double difference = 0;
        for (int i = 0; i < areaPoints.size() - 1; i++) {
            difference += areaPoints.get(i).getY() * areaPoints.get(i + 1).getX();
        }
        difference += areaPoints.get(areaPoints.size() - 1).getY() * areaPoints.get(0).getX();
        return Math.abs(0.5 * (sum - difference));
    }

    public static void sortVerticesClockwise(ArrayList<R2Point> vertices) {
        // среднее арифметическое
        double centerX = 0;
        double centerY = 0;
        int n = vertices.size();
        for (R2Point vertex : vertices) {
            centerX += vertex.getX();
            centerY += vertex.getY();
        }
        centerX /= n;
        centerY /= n;

        double finalCenterX = centerX;
        double finalCenterY = centerY;
        Comparator<R2Point> polarAngleComparator = (v1, v2) -> {
            // atan2 возвращает угол между осью икс и вектором из центра оси координат в точку(икс, игрик)
            double angle1 = Math.atan2(v1.getY() - finalCenterY, v1.getX() - finalCenterX);
            double angle2 = Math.atan2(v2.getY() - finalCenterY, v2.getX() - finalCenterX);
            return Double.compare(angle1, angle2);
        };
        vertices.sort(polarAngleComparator);
    }

    public static ArrayList<R2Point> findIntersectionPoints(ArrayList<R2Point> points) {
        ArrayList<R2Point> intersectionPoints = new ArrayList<>();

        for (R2Point point : points) {
            if (point.getY() == 0) {
                intersectionPoints.add(point);
            } else {
                if ((point.getY() > 0 && point.getPrevY(points) < 0) || (point.getY() < 0 && point.getPrevY(points) > 0)) {
                    double intersectionX = point.getX() - (point.getY() * (point.getPrevX(points) - point.getX())) / (point.getPrevY(points) - point.getY());
                    intersectionPoints.add(new R2Point(intersectionX, 0));
                }
            }
        }

        return intersectionPoints;
    }
}