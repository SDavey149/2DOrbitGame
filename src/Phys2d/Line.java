package Phys2d;

/**
 * Created by scottdavey on 06/03/2016.
 */
public class Line extends Shape {

    private double length;
    private int thickness;

    public Line(GameObject obj, double length, int thickness) {
        super(obj);
        this.length = length;
        this.thickness = thickness;
    }

    public static Line getLineObject(Vector2D start, Vector2D end) {
        Vector2D midpoint = new Vector2D(start);
        start.mult(-1);
        end.add(start);
        Vector2D line = end; //rename to line

        Vector2D half = new Vector2D(line);
        half.mult(0.5);
        midpoint.add(half);

        //get angle to xaxis
        double rotation = Math.atan2(line.y, line.x);
        GameObject obj = new GameObject(midpoint, null);
        obj.rotate(rotation);
        return new Line(obj, line.mag(), 1);

    }

    @Override
    public boolean overlaps(Shape shape) {
        if (shape instanceof Circle) {
            return overlapWith((Circle)shape);
        }
        return false;
    }

    private boolean overlapWith(Circle circle) {
        Vector2D start = startOfLine();
        Vector2D tangent = Vector2D.minus(endOfLine(), start);
        double length = tangent.mag();
        tangent.normalise();
        Vector2D normal = tangent.rotate90degreesAnticlockwise();

        Vector2D lineToC = Vector2D.minus(circle.object.getPosition(), start);
        double distNormal = Math.abs(lineToC.scalarProduct(normal));
        double distLine = lineToC.scalarProduct(tangent);
        return distNormal <= circle.getRadius() && distLine >= 0 && distLine <= length;
    }


    public Vector2D startOfLine() {
        double startX = object.getPosition().x - length/2;
        double startY = object.getPosition().y;
        Vector2D start = new Vector2D(startX, startY);
        Vector2D rotationVec = object.getRotation();
        double rotation = Math.atan2(rotationVec.y, rotationVec.x);
        if (rotation == 0) {
            return start;
        }
        Vector2D pos = new Vector2D(object.getPosition());
        pos.mult(-1);
        start.add(pos);
        start.rotate(rotation);
        pos.mult(-1);
        start.add(pos);
        return start;
    }

    public Vector2D endOfLine() {
        double endX = object.getPosition().x + length/2;
        double endY = object.getPosition().y;
        Vector2D end = new Vector2D(endX, endY);
        Vector2D rotationVec = object.getRotation();
        double rotation = Math.atan2(rotationVec.y, rotationVec.x);
        if (rotation == 0) {
            return end;
        }
        Vector2D pos = new Vector2D(object.getPosition());
        pos.mult(-1);
        end.add(pos);
        end.rotate(rotation);
        pos.mult(-1);
        end.add(pos);
        return end;
    }
}
