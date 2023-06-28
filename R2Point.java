import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class R2Point {
    private double x, y;

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
    public double getPrevX(ArrayList<R2Point> points) {
        // найти предыдущий элемент учитывая цикличность (как в питоне)
        int prevIndex = (points.indexOf(this) - 1 + points.size()) % points.size();
        return points.get(prevIndex).getX();
    }

    public double getPrevY(ArrayList<R2Point> points) {
        int prevIndex = (points.indexOf(this) - 1 + points.size()) % points.size();
        return points.get(prevIndex).getY();
    }

    public R2Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public R2Point() {
        Scanner in = new Scanner(System.in);
        System.out.print("x -> ");
        x = in.nextDouble();
        System.out.print("y -> ");
        y = in.nextDouble();
        try (FileWriter fileWriter = new FileWriter("points.txt", true)) {
            fileWriter.append(String.valueOf(x)).append(" ").append(String.valueOf(y)).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double dist(R2Point a, R2Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public static double area(R2Point a, R2Point b, R2Point c) {
        return 0.5 * ((a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        R2Point point = (R2Point) obj;
        return this.x == point.x && this.y == point.y;
    }

    public static boolean isTriangle(R2Point a, R2Point b, R2Point c) {
        return area(a, b, c) != 0.0;
    }

    public boolean inside(R2Point a, R2Point b) {
        return (a.x <= x && x <= b.x || a.x >= x && x >= b.x) && (a.y <= y && y <= b.y || a.y >= y && y >= b.y);
    }

    public boolean light(R2Point a, R2Point b) {
        double s = area(a, b, this);
        return s < 0.0 || (s == 0.0 && !inside(a, b));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}