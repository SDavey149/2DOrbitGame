package Phys2d;

/**
 * Created by scottdavey on 10/04/2016.
 */
public class Box extends Shape {

    public Box(GameObject obj) {
        super(obj);
    }

    @Override
    public boolean overlaps(Shape shape) {
        return false;
    }
}
