package Phys2d;

/**
 * Created by scottdavey on 06/03/2016.
 */
public class Circle extends Shape {
    int radius;

    public Circle(GameObject obj, int radius) {
        super(obj);
        this.radius = radius;
    }

    @Override
    public boolean overlaps(Shape shape) {
        return false;
    }
}
