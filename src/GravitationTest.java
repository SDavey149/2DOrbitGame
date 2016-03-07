import Phys2d.RigidBody;
import Phys2d.World;
import utilities.Vector2D;

/**
 * Created by scottdavey on 07/03/2016.
 */
public class GravitationTest {
    public static void main(String[] args) {
        //a - 1kg, b - 1000000000kg, d = 14.14213562
        //6.67*10^-11 * 1 * 1000000000 / 0.05^2
        System.out.println("" + run(new Vector2D(10,10), new Vector2D(20,20)));
    }

    public static double run(Vector2D a, Vector2D b) {
        Vector2D position = new Vector2D(a);
        position.mult(-1);
        Vector2D objToOther = new Vector2D(b);
        objToOther.add(position);
        Vector2D direction = new Vector2D(objToOther);
        direction.normalise();
        //gravitational (G*m1*m2)/d^2; m = mass, d = distance
        System.out.println(objToOther.mag());
        System.out.println((objToOther.mag()*objToOther.mag()));
        double bottom = (objToOther.mag()*objToOther.mag());
        System.out.println(World.G);

        double top = World.G*1*1000000000.0;
        System.out.println("top: " + top);
        System.out.println("botom" + bottom);
        double forceMag = top/bottom;
        if (forceMag == Double.POSITIVE_INFINITY) {
            forceMag = -Double.MAX_VALUE;
        }
        System.out.println("force: " + forceMag);
        return forceMag;
    }
}
