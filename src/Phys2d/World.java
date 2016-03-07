package Phys2d;

import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class World {
    static double gravity = 9.8;
    public static final double G = Math.pow(6.673, -11);

    private double width;
    private double height;

    // sleep time between two drawn frames in milliseconds - NEEDS TO BE CHANGED
    public static final int DELAY = 10;
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
    // estimate for time between two frames in seconds
    public static final double DELTA_T = DELAY / 1000.0 / NUM_EULER_UPDATES_PER_SCREEN_REFRESH ;

    List<GameObject> gameObjects;

    public World(double width, double height) {
        gameObjects = new ArrayList<>(100);
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

    public Vector2D getGravitionalForce(GameObject obj) {
        Vector2D force = new Vector2D(0,0);
        for (GameObject o : gameObjects) {
            if (o != obj && obj.hasRigidBody()) {
                RigidBody b = (RigidBody)obj.getBody();
                force.add(getGravitionalForce(obj.getPosition(), b.getMass()));
            }
        }
        return force;
    }

    public Vector2D getGravitionalForce(Vector2D pos, double mass) {
        Vector2D force = new Vector2D(0,0);
        for (GameObject o : gameObjects) {
            if (o.getPosition() != pos && o.hasRigidBody()) {
                Vector2D oPos = new Vector2D(pos);
                oPos.mult(-1);
                Vector2D objToOther = new Vector2D(o.getPosition());
                objToOther.add(oPos);

                Vector2D direction = new Vector2D(objToOther);
                direction.normalise();
                //gravitational (G*m1*m2)/d^2; m = mass, d = distance
                RigidBody b = (RigidBody)o.getBody();
                double forceMag = (World.G*mass*b.getMass())/objToOther.mag();
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
            }
        }

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
