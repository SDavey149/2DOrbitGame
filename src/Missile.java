import Phys2d.*;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 06/04/2016.
 */
public class Missile extends GameObjectView implements CollideCallback {
    Circle circle;
    Color color;
    RigidBodyImproved rgb;
    private double fuel;
    private double missileMass;
    private static int MISSILE_THRUST = 20000;
    private static int FUEL_USE_PER_SECOND = 1;

    public Missile(World world, Vector2D pos, Vector2D initialDirection, Vector2D initialVelocity, int initialFuelSize) {
        GameObject bullet = new GameObject(pos, this);
        bullet.setShape(new Circle(bullet, 0.4));
        initialDirection.mult(MISSILE_THRUST);
        bullet.mass = 1;
        rgb = new RigidBodyImproved(bullet);
        bullet.addRigidBody(rgb);
        rgb.addForce(initialDirection);
        object = bullet;
        object.setVelocity(initialVelocity);
        world.addGameObject(object);

        circle = (Circle)object.getShape();
        color = Color.RED;
        missileMass = object.mass;
        fuel = initialFuelSize;
        System.out.println("missle spawned at: " + pos);
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        object.mass = missileMass + fuel;
        if (fuel > 0) {
            Vector2D direction = new Vector2D(object.getVelocity());
            direction.normalise();
            direction.mult(MISSILE_THRUST*delta);
            rgb.addForce(direction);
            fuel -= FUEL_USE_PER_SECOND*delta;
        }

    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {

        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (JEasyFrame.SCREEN.height-object.getPosition().y*yScreenScale);
        g.setColor(color);
        double radius = circle.getRadius()*xScreenScale;
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void onCollide() {

    }
}
