import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;
import Phys2d.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class RunPhysicsDemo {
    static int maxWidth;
    static int maxHeight;
    static double worldWidth;
    static int worldHeight;

    public static void main(String[] args) {
        maxWidth = JEasyFrame.SCREEN.width;
        maxHeight = JEasyFrame.SCREEN.height;
        double ratio = maxWidth/maxHeight;
        worldWidth = 500*ratio;
        worldHeight = 100;
        World world = new World(worldWidth,500);
        View view = new View(world, maxWidth, maxHeight);
        JEasyFrame frame = new JEasyFrame(view, "Basic Physics Engine");
        frame.setSize(maxWidth, maxHeight);
        setup(world, view);
        runGame(world, view);

    }

    public static void setup(World world, View view) {
        GameObject obj2 = new GameObject(new Vector2D(100,100));
        obj2.setShape(new Circle(obj2, 20));
        obj2.mass = 1000000000000000.0;
        //RigidBodyImproved b2 = new RigidBodyImproved(obj2);
        //obj2.addRigidBody(b2);
        world.addGameObject(obj2);

        GameObject obj4 = new GameObject(new Vector2D(300,300));
        obj4.setShape(new Circle(obj4, 20));
        obj4.mass = 5000000000000000.0;
        //RigidBodyImproved b4 = new RigidBodyImproved(obj4);
        //obj4.addRigidBody(b4);
        world.addGameObject(obj4);

        /*GameObject obj99 = new GameObject(new Vector2D(250,300));
        obj99.setShape(new Circle(obj99, 5));
        obj99.setVelocity(new Vector2D(-25, 0));
        obj99.mass = 10;
        RigidBodyImproved b99 = new RigidBodyImproved(obj99);
        obj99.addRigidBody(b99);
        world.addGameObject(obj99);*/

        Ball ball2 = new Ball(obj2);
        view.addObjectView(ball2);

        Ball ball4 = new Ball(obj4);
        view.addObjectView(ball4);

        //Ball ball99 = new Ball(obj99);
        //view.addObjectView(ball99);

        GameObject obj3 = new GameObject(new Vector2D(10,300));
        obj3.setShape(new Circle(obj3, 6));
        obj3.mass = 4500;
        RigidBodyImproved rgb3 = new RigidBodyImproved(obj3);
        obj3.addRigidBody(rgb3);
        Ship ship = new Ship(obj3, world, view, 100000);
        view.addObjectView(ship);
        world.addGameObject(obj3);


        //make da barriers - bottom
        /*AnchoredBarrier_StraightLine bar1 = new AnchoredBarrier_StraightLine(0,2,worldWidth, 2);
        BarrierView bar1View = new BarrierView(bar1);
        world.addBarrier(bar1);
        view.addObjectView(bar1View);

        //right
        AnchoredBarrier_StraightLine bar2 = new AnchoredBarrier_StraightLine(worldWidth,0,worldWidth, worldHeight);
        BarrierView bar2View = new BarrierView(bar2);
        world.addBarrier(bar2);
        view.addObjectView(bar2View);

        //top
        AnchoredBarrier_StraightLine bar3 = new AnchoredBarrier_StraightLine(0,worldHeight,worldWidth, worldHeight);
        BarrierView bar3View = new BarrierView(bar3);
        world.addBarrier(bar3);
        view.addObjectView(bar3View);

        //left
        AnchoredBarrier_StraightLine bar4 = new AnchoredBarrier_StraightLine(0,worldHeight,0, 0);
        BarrierView bar4View = new BarrierView(bar4);
        world.addBarrier(bar4);
        view.addObjectView(bar4View);

        //middle
        /*GameObject barrier = new GameObject(new Vector2D(worldWidth/2, worldHeight/2));
        barrier.setShape(new Line(barrier, 50, 1));
        barrier.setRotation(Math.PI/2);
        world.addGameObject(barrier);
        BarrierLine2 bar5View = new BarrierLine2(barrier);
        view.addObjectView(bar5View);*/

        view.addKeyListener(new BasicKeyListener());
        view.requestFocus();


    }


    public static void runGame(World w, View v) {
        long lastTime = 0;
        while (true) {
            long currentTime = System.currentTimeMillis();
            double delta = (currentTime - lastTime)/1000.0;
            if (lastTime == 0) {
                delta = World.DELTA_T;
            }
            lastTime = currentTime;
            delta = delta/World.NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
            synchronized (v.objectViews) {
                for (ObjectView objectView : v.objectViews) {
                    if (objectView instanceof GameObjectView) {
                        ((GameObjectView) objectView).notificationOfNewTimeStep(delta);
                    }
                }
            }

            w.update(delta);
            v.repaint();
            try {
                Thread.sleep(World.DELAY);
            } catch (InterruptedException e) {
            }
        }
    }
}
