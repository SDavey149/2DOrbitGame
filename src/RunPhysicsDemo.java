import Phys2d.Circle;
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
        int maxWidth = JEasyFrame.SCREEN.width;
        int maxHeight = JEasyFrame.SCREEN.height;
        double ratio = maxWidth/maxHeight;
        World world = new World(100*ratio,100);
        View view = new View(world, maxWidth, maxHeight);
        JEasyFrame frame = new JEasyFrame(view, "Basic Physics Engine");
        frame.setSize(maxWidth, maxHeight);
        setup(world, view);
        runGame(world, view);
    }

    public static void setup(World world, View view) {
        GameObject obj = new GameObject(new Vector2D(5,10));
        obj.setVelocity(new Vector2D(0.4,0));
        obj.setShape(new Circle(obj, 20));
        RigidBody b = new RigidBody(obj, 10);
        obj.addRigidBody(b);
        world.addGameObject(obj);

        GameObject obj2 = new GameObject(new Vector2D(50,50));
        obj2.setShape(new Circle(obj, 30));
        RigidBody b2 = new RigidBody(obj, 1000000000.0);
        obj2.addRigidBody(b2);
        world.addGameObject(obj2);

        Ball ball = new Ball(obj);
        view.addObjectView(ball);

        Ball ball2 = new Ball(obj2);
        view.addObjectView(ball2);



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
