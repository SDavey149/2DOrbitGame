package tests;

import Phys2d.Circle;
import Phys2d.GameObject;
import junit.framework.TestCase;
import utilities.Vector2D;

/**
 * Created by scottdavey on 02/04/2016.
 */
public class CircleTest extends TestCase {

    private GameObject object1;
    private GameObject object2;

    private GameObject object3;
    private GameObject object4;

    public void setUp() throws Exception {
        super.setUp();
        object1 = new GameObject(new Vector2D(15,20));
        object2 = new GameObject(new Vector2D(16,19));
        object3 = new GameObject(new Vector2D(0,0));
        object4 = new GameObject(new Vector2D(2,1));
        object1.setVelocity(new Vector2D(0.5, -0.5)); //moving towards each other

        object1.setShape(new Circle(object1, 5));
        object2.setShape(new Circle(object2, 2));
        object3.setShape(new Circle(object3, 1));

    }

    public void tearDown() throws Exception {

    }

    public void testOverlaps() throws Exception {
        assertEquals(true, object2.getShape().overlaps(object1.getShape()));
        assertEquals(true, object1.getShape().overlaps(object2.getShape()));
        assertEquals(false, object2.getShape().overlaps(object3.getShape()));
    }

    public void testGetRadius() throws Exception {
        assertEquals(true, true);
    }
}