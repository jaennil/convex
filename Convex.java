class Convex{
    private Figure figure;
    public R2Point lastPoint;

    public Convex(){
        figure = new Void();
    }

    public void add(R2Point point){
        figure = figure.add(point);
        this.lastPoint = point;
    }

    public double area(){
        return figure.area();
    }

    public double perimeter(){
        return figure.perimeter();
    }
}