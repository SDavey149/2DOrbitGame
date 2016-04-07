package Phys2d;

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
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=1;
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

    public Vector2D getGravitationalForce(GameObject obj, Vector2D pos, double mass) {
        Vector2D force = new Vector2D(0,0);
        if (obj.hasRigidBody()) {
            for (GameObject o : gameObjects) {
                if (o != obj) {
                    //make a copy
                    Vector2D position = new Vector2D(pos);
                    position.mult(-1);
                    Vector2D objToOther = new Vector2D(o.getPosition());
                    objToOther.add(position);
                    Vector2D direction = new Vector2D(objToOther);
                    direction.normalise();
                    //gravitational (G*m1*m2)/d^2; m = mass, d = distance
                    if (objToOther.mag() > 0) {
                        double forceMag = (World.G*o.mass*mass)/(objToOther.mag()*objToOther.mag());
                        direction.mult(forceMag);
                        force.add(direction);
                    }
                }
            }
        }
        return force;
    }


    public void update(double delta) {
        for (int i = 0; i < NUM_EULER_UPDATES_PER_SCREEN_REFRESH; i++) {
            for (GameObject obj : gameObjects) {
                obj.update(delta);
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
        for (int n=0;n<gameObjects.size();n++) {
            for (int m=0;m<n;m++) {// avoids double check by requiring m<n
                Shape s1 = gameObjects.get(n).getShape();
                Shape s2 = gameObjects.get(m).getShape();
                if (s1.overlaps(s2)) {
                    System.out.println("COLLIDED MUTHAFUGGA");
                }
            }
        }

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<AnchoredBarrier> getBarriers() { return barriers; }
}
