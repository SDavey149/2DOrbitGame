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

    @Override
    public boolean overlaps(Shape shape) {
        if (shape instanceof Circle) {

        }
        return false;
    }

    private boolean overlapWith(Circle circle) {
        /*Vector2D ap=Vector2D.minus(circleCentre, startPos);
		double distOnCorrectSideOfBarrierToCentre=ap.scalarProduct(getUnitNormal());
		double distAlongBarrier=ap.scalarProduct(getUnitTangent());
		// Note barrierDepth is type Double declared in constructor.
		// barrierDepth null indicates infinite barrierDepth
		// barrierLength is ||AB||, declared in constructor.
		return distOnCorrectSideOfBarrierToCentre<=circleRadius && (barrierDepth==null || distOnCorrectSideOfBarrierToCentre>=-(barrierDepth+circleRadius))
				&& distAlongBarrier>=0 && distAlongBarrier<=barrierLength;*/
        Vector2D start = startOfLine();
        Vector2D tangent = Vector2D.minus(endOfLine(), start);
        double length = tangent.mag();
        tangent.normalise();
        Vector2D normal = tangent.rotate90degreesAnticlockwise();

        Vector2D lineToC = Vector2D.minus(circle.object.getPosition(), start);
        double distNormal = lineToC.scalarProduct(normal);
        double distLine = lineToC.scalarProduct(tangent);
        return distNormal <= circle.getRadius() && distLine >= 0 && distLine <= length;
    }


    public Vector2D startOfLine() {
        double startX = object.getPosition().x - length/2;
        double startY = object.getPosition().y;
        Vector2D start = new Vector2D(startX, startY);
        double rotation = object.getRotation();
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
        double rotation = object.getRotation();
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
