package Phys2d;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class World {
    static double gravity = 9.8;
    public static final double G = 6.67 * Math.pow(10.0, -11);

    private double width;
    private double height;

    public static final int DELAY = 20;
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
    // estimate for time between two frames in seconds
    public static final double DELTA_T = DELAY / 1000.0 / NUM_EULER_UPDATES_PER_SCREEN_REFRESH ;

    List<GameObject> gameObjects;
    List<AnchoredBarrier> barriers;

    public World(double width, double height) {
        gameObjects = new ArrayList<>(100);
        barriers = new ArrayList<>(10);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void addGameObject(GameObject obj) {
        obj.setWorld(this);
        gameObjects.add(obj);
    }

    public void addBarrier(AnchoredBarrier bar) {
        barriers.add(bar);
    }

    public Vector2D getGravitionalForce(GameObject obj) {
        Vector2D force = new Vector2D(0,0);
        for (GameObject o : gameObjects) {
            if (o != obj && obj.hasRigidBody()) {
                Vector2D position = new Vector2D(obj.getPosition());
                position.mult(-1);
                Vector2D objToOther = new Vector2D(o.getPosition());
                objToOther.add(position);

                Vector2D direction = new Vector2D(objToOther);
                direction.normalise();
                //gravitational (G*m1*m2)/d^2; m = mass, d = distance
                double forceMag = (World.G*o.mass*obj.mass)/(objToOther.mag()*objToOther.mag());
                direction.mult(forceMag);
                force.add(direction);
            }
        }
        return force;
    }


    public void update() {
        for (int i = 0; i < NUM_EULER_UPDATES_PER_SCREEN_REFRESH; i++) {
            for (GameObject obj : gameObjects) {
                obj.update();
                /*for (AnchoredBarrier b : barriers) {
                    if (obj.getShape() instanceof Circle) {
                        Circle objShape = (Circle)obj.getShape();
                        if (b.isCircleCollidingBarrier(obj.getPosition(), objShape.getRadius())) {
                            //b.onCollide();
                            Vector2D bouncedVel=b.calculateVelocityAfterACollision(obj.getPosition(), obj.getVelocity());
                            obj.setVelocity(bouncedVel);
                            //System.exit(1);
                        }
                    }


                }*/
            }

        }

        //check collisions

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<AnchoredBarrier> getBarriers() { return barriers; }
}
