package Phys2d;

/**
 * Created by scottdavey on 02/03/2016.
 */
public abstract class Shape {
    GameObject object;

    public Shape(GameObject obj) {
        object = obj;
    }

    public GameObject getObject() {
        return object;
    }

    public abstract boolean overlaps(Shape shape);
}
