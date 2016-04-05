import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;
import utilities.Vector2D;

import java.awt.*;

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
        worldWidth = 100*ratio;
        worldHeight = 100;
        World world = new World(worldWidth,100);
        View view = new View(world, maxWidth, maxHeight);
        JEasyFrame frame = new JEasyFrame(view, "Basic Physics Engine");
        frame.setSize(maxWidth, maxHeight);
        setup(world, view);
        runGame(world, view);

    }

    public static void setup(World world, View view) {
        GameObject obj = new GameObject(new Vector2D(50,80));
        obj.setShape(new Circle(obj, 1));
        obj.setVelocity(new Vector2D(-4,0));
        obj.mass = 10;
        RigidBodyImproved b = new RigidBodyImproved(obj);
        obj.addRigidBody(b);
        //world.addGameObject(obj);

        //blue ball
        GameObject ball1 = new GameObject(new Vector2D(50,60));
        ball1.setShape(new Circle(ball1, 1));
        ball1.setVelocity(new Vector2D(-7, 0));
        ball1.mass = 1;
        RigidBodyEuler b_ball = new RigidBodyEuler(ball1, 1);
        ball1.addRigidBody(b_ball);
        //world.addGameObject(ball1);

        GameObject obj2 = new GameObject(new Vector2D(50,50));
        obj2.setShape(new Circle(obj2, 2));
        obj2.mass = 10000000000000.0;
        RigidBodyImproved b2 = new RigidBodyImproved(obj2);
        obj2.addRigidBody(b2);
        world.addGameObject(obj2);

        GameObject obj99 = new GameObject(new Vector2D(50,30));
        obj99.setShape(new Circle(obj99, 1));
        obj99.setVelocity(new Vector2D(-5,0));
        obj99.mass = 10;
        RigidBodyImproved b99 = new RigidBodyImproved(obj99);
        obj99.addRigidBody(b99);
        //world.addGameObject(obj99);


        Ball ball = new Ball(obj);
        view.addObjectView(ball);

        Ball ball2 = new Ball(obj2);
        view.addObjectView(ball2);

        Ball ball99 = new Ball(obj99);
        view.addObjectView(ball99);

        Ball blueBall = new Ball(ball1);
        blueBall.setColor(Color.BLUE);
        view.addObjectView(blueBall);

        GameObject obj3 = new GameObject(new Vector2D(10,10));
        obj3.setShape(new Circle(obj3, 1));
        obj3.mass = 2;
        obj3.addRigidBody(new RigidBodyImproved(obj3));
        obj3.setVelocity(new Vector2D(5,0));
        Ship ship = new Ship(obj3);
        view.addObjectView(ship);
        world.addGameObject(obj3);


        //make da barriers - bottom
        AnchoredBarrier_StraightLine bar1 = new AnchoredBarrier_StraightLine(0,2,worldWidth, 2);
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
            for (ObjectView objectView : v.objectViews) {
                if (objectView instanceof GameObjectView) {
                    ((GameObjectView) objectView).notificationOfNewTimeStep(delta);
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
