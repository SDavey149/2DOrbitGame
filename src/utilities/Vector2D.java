package utilities;

// mutable 2D vectors
public final class Vector2D {

    // fields
    public final double x, y;

    // construct a null vector
    public Vector2D() {
        x = 0;
        y = 0;
    }

    // construct a vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // construct a vector that is a copy of the argument
    public Vector2D(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    // compare for equality (needs to allow for Object type argument...)
    public boolean equals(Object o) {
        Vector2D v = (Vector2D) o;
        return x == v.x && y == v.y;
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    // angle between vector and horizontal axis in radians
    public double theta() {
        return Math.atan2(y, x);
    }

    // String for displaying vector as text
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    // add coordinate values
    public Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    // weighted add - frequently useful
    public Vector2D add(Vector2D v, double fac) {
        return new Vector2D(this.x + v.x * fac, this.x + v.y * fac);
    }

    // multiply with factor
    public Vector2D mult(double fac) {
        return new Vector2D(x * fac, y * fac);
    }

    // rotate by angle given in radians
    public Vector2D rotate(double theta) {
        double xn = x * Math.cos(theta) - y * Math.sin(theta);
        double yn = x * Math.sin(theta) + y * Math.cos(theta);
        return new Vector2D(xn, yn);
    }

    public Vector2D rotate90Anti() {
        return new Vector2D(-y, x);
    }

    // scalar product with argument vector
    public double scalarProduct(Vector2D v) {
        return (x * v.x) + (y * v.y);
    }

    public double dotProduct(Vector2D v) {
        //Just an alias of scalarProduct
        return scalarProduct(v);
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double xn = x - v.x;
        double yn = y - v.y;
        return Math.sqrt(xn * xn + yn * yn);

    }

    // normalise vector so that mag becomes 1
    // direction is unchanged
    public Vector2D normalise() {
        return new Vector2D(x / mag(), y / mag());
    }
}
