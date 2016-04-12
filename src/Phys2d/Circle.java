package Phys2d;

/**
 * Created by scottdavey on 06/03/2016.
 */
public class Circle extends Shape {
    private double radius;

    public Circle(GameObject obj, double radius) {
        super(obj);
        this.radius = radius;
    }

    @Override
    public boolean overlaps(Shape shape) {
        GameObject otherObj = shape.getObject();

        //collision with another ball
        if (shape instanceof Circle) {
            Circle otherShape = (Circle) shape;
            Vector2D vecFrom1to2 = Vector2D.minus(otherObj.getPosition(), object.getPosition());
            boolean movingTowardsEachOther = Vector2D.minus(otherObj.getVelocity(),
                    object.getVelocity()).scalarProduct(vecFrom1to2)<0;

            return vecFrom1to2.mag()<getRadius()+otherShape.getRadius() && movingTowardsEachOther;
        }


        return false;

    }

    public double getRadius() {
        return radius;
    }
}
