import Phys2d.Circle;
import Phys2d.GameObject;
import Phys2d.RigidBodyImproved;
import Phys2d.Vector2D;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 06/04/2016.
 */
public class Missile extends GameObjectView {
    Circle circle;
    Color color;
    RigidBodyImproved rgb;
    private double fuel;
    private double missileMass;
    private static int MISSILE_THRUST = 10;
    private static int FUEL_USE_PER_SECOND = 1;

    public Missile(GameObject obj, int initialFuelSize) {
        //super(obj);
        circle = (Circle)obj.getShape();
        color = Color.RED;
        missileMass = obj.mass;
        rgb = (RigidBodyImproved) obj.getBody();
        fuel = initialFuelSize;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        object.mass = missileMass + fuel;
        if (fuel > 0) {
            Vector2D direction = new Vector2D(object.getVelocity());
            direction.normalise();
            direction.mult(MISSILE_THRUST);
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
}
