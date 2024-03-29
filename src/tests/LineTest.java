package tests;

import Phys2d.GameObject;
import Phys2d.Line;
import junit.framework.TestCase;
import Phys2d.Vector2D;

/**
 * Created by scottdavey on 06/03/2016.
 */
public class LineTest extends TestCase {


    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testOverlaps() throws Exception {

    }

    public void testLine() throws Exception {
        GameObject object = new GameObject(new Vector2D(15,20), null);
        Line line1 = new Line(object, 10, 1);
        assertEquals(new Vector2D(10.0,20.0), line1.startOfLine());
        assertEquals(new Vector2D(20.0, 20.0), line1.endOfLine());

        object.rotate(Math.PI / 2);
        assertEquals(new Vector2D(15.0, 15.0), line1.startOfLine());
        assertEquals(new Vector2D(15.0, 25.0), line1.endOfLine());

        Line line = Line.getLineObject(new Vector2D(5,5), new Vector2D(10,10));
        assertEquals(new Vector2D(5,5), line.startOfLine());
        assertEquals(new Vector2D(10,10), line.endOfLine());

    }

}