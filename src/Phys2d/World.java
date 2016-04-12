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
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
    // estimate for time between two frames in seconds
    public static final double DELTA_T = DELAY / 1000.0 / NUM_EULER_UPDATES_PER_SCREEN_REFRESH ;
    public static final double MIN_GRAVITATIONAL_MASS = 100000000.0;

    List<GameObject> gameObjects;
    List<GameObject> pending;
    List<GameObject> pendingRemoval;

    public World(double width, double height) {
        reset();
        this.width = width;
        this.height = height;
    }

    public void reset() {
        gameObjects = new ArrayList<>(100);
        pending = new ArrayList<>();
        pendingRemoval = new ArrayList<>();
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

    public Vector2D getGravitationalForce(GameObject obj, Vector2D pos, double mass) {
        Vector2D force = new Vector2D(0,0);
        if (obj.hasRigidBody()) {
            for (GameObject o : gameObjects) {
                if (o.mass > MIN_GRAVITATIONAL_MASS && o != obj) {
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
            }
        }

        //check collisions
        for (int n=0;n<gameObjects.size();n++) {
            for (int m=0;m<n;m++) {// avoids double check by requiring m<n
                GameObject g1 = gameObjects.get(n);
                GameObject g2 = gameObjects.get(m);
                Shape s1 = g1.getShape();
                Shape s2 = g2.getShape();
                if (s1.overlaps(s2)) {
                    g1.collided(g2.tag);
                    g2.collided(g1.tag);
                }
            }
        }

        //remove pending
        for (GameObject obj : pendingRemoval) {
            gameObjects.remove(obj);
        }

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void destroy(GameObject object) {
        pendingRemoval.add(object);
    }
}
