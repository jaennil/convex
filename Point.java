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

    public Figure add(R2Point point){
        if(!R2Point.equal(this.point, point))
            return new Segment(this.point, point);
        else
            return this;
    }
}