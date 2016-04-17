import Phys2d.*;
import utilities.JEasyFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by scottdavey on 06/04/2016.
 */
public class Missile extends GameObjectView implements CollideCallback {
    Circle circle;
    Color color;
    RigidBody rgb;
    private double fuel;
    private double missileMass;
    private static int MISSILE_THRUST = 20000;
    public static final int ORBIT_STEP_INTERVAL = 2;
    public static final int MAX_STEPS_RECORDED = 200;
    public static final int TIME_TO_LIVE = 300;
    private List<Vector2D> orbitTrace;
    private int orbitSteps;
    private int ttl;
    private World world;

    public Missile(World world, Vector2D pos, Vector2D initialForce, int initialFuelSize) {
        GameObject bullet = new GameObject(pos, this);
        bullet.tag = "Missile";
        bullet.setShape(new Circle(bullet, 0.4));
        //initialForce.mult(MISSILE_THRUST);
        bullet.mass = 1;
        rgb = new RigidBody(bullet);
        bullet.addRigidBody(rgb);
        rgb.addForce(initialForce);
        object = bullet;
        this.world = world;
        this.world.addGameObject(object);
        orbitSteps = ORBIT_STEP_INTERVAL;
        circle = (Circle)object.getShape();
        color = Color.RED;
        missileMass = object.mass;
        fuel = initialFuelSize;
        orbitTrace = new LinkedList<>();
        ttl = 0;
        isActive = true;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        object.mass = missileMass + fuel;
        if (fuel > 0) {
            Vector2D direction = new Vector2D(object.getVelocity());
            direction.normalise();
            direction.mult(MISSILE_THRUST*delta);
            rgb.addForce(direction);
            int FUEL_USE_PER_SECOND = 1;
            fuel -= FUEL_USE_PER_SECOND *delta;
        }

    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {

        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (Game.maxHeight-object.getPosition().y*yScreenScale);
        g.setColor(color);
        double radius = circle.getRadius()*xScreenScale;
        if (x >= 0 && x <= JEasyFrame.SCREEN.width
                && y >= 0 && y <= JEasyFrame.SCREEN.height) {
            g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));

            if (orbitSteps > ORBIT_STEP_INTERVAL) {
                orbitTrace.add(new Vector2D(x, y));
                orbitSteps = 0;
            }
            orbitSteps++;
            ttl = 0;
        } else {
            //outside of screen, give it until TIME_TO_LIVE to get back in the screen otherwise destroy it
            if (ttl++ > TIME_TO_LIVE) {
                world.destroy(object);
                isActive = false;
            }
        }

        for (Vector2D orbitPos : orbitTrace) {
            g.setColor(Color.GRAY);
            g.fillOval((int)(orbitPos.x - 1), (int)(orbitPos.y - 1),
                    2, 2);
        }
        if (orbitTrace.size() > MAX_STEPS_RECORDED) {
            for (int i = 0; i < orbitTrace.size()-MAX_STEPS_RECORDED; i++) {
                orbitTrace.remove(0);
            }

        }
    }

    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void onCollide(String collidedTag) {
        if (!collidedTag.equals("Barrier")) {
            isActive = false;
            world.destroy(object);
            System.out.println("destroyed");
        }

    }

}
