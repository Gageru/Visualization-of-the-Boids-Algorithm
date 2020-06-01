public class Vector implements Cloneable {

    protected double x;
    protected double y;

    //Create a vector null
    public Vector() {
        this.setX(0);
        this.setY(0);
    }

    public Vector(double x0, double y0) {
        this.setX(x0);
        this.setY(y0);
    }

    public boolean equal(Vector V) {
        if (this.x == V.x() && this.y == V.y())
            return true;
        else
            return false;
    }

    public static Vector average(Vector[] vectors) {
        Vector average = new Vector();
        double sumX = 0;
        double sumY = 0;
        for (Vector v : vectors) {
            sumX += v.x();
            sumY += v.y();
        }
        if (vectors.length != 0) {
            average.setX(sumX/vectors.length);
            average.setY(sumY/vectors.length);
        }
        return average;
    }

    public double norm() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector normalize() {
        if (this.norm() > 0)
            return (this.multiply(1/this.norm()));
        else
            return new Vector();
    }

    public Vector multiply(double c) {
        return (new Vector(this.x * c, this.y * c));
    }

    public Vector add(Vector V) {
        return (new Vector(this.x + V.x(), this.y + V.y()));
    }

    @Override
    public Vector clone() {
        Vector v = null;
        try {
            v = (Vector)super.clone();
        }
        catch (CloneNotSupportedException e) {
        }
        return v;
    }


    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }


    public void set(Vector V) {
        this.setX(V.x());
        this.setY(V.y());
    }

    public void setX(double x0) {
        this.x = x0;
    }

    public void setY(double y0) {
        this.y = y0;
    }

}