import Phys2d.GameObject;
import Phys2d.RigidBody;
import Phys2d.World;
import utilities.JEasyFrame;
import utilities.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class RunPhysicsDemo {
    public static void main(String[] args) {
        World world = new World(10,10*1.33333333333);
        View view = new View(world, 800, 600);
        JEasyFrame frame = new JEasyFrame(view, "Basic Physics Engine");
        frame.setSize(800, 600);
        setup(world, view);
        runGame(world, view);
    }

    public static void setup(World world, View view) {
        GameObject obj = new GameObject(new Vector2D(5,10));
        RigidBody b = new RigidBody(obj, null);
        b.setMass(10);
        obj.addRigidBody(b);
        world.addGameObject(obj);

        Ball ball = new Ball(obj);
        view.addObjectView(ball);

    }

    public static void runGame(World w, View v) {
        while (true) {
            w.update();
            v.repaint();
            try {
                Thread.sleep(World.DELAY);
            } catch (InterruptedException e) {
            }
        }
    }
}
