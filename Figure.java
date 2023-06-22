import java.util.ArrayList;

//Интерфейс, задающий новый тип - фигуру.
interface Figure{
    public double perimeter();
    public double area();
    public Figure add(R2Point p);
}