import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.PackedColorModel;

/**
 * Created by scottdavey on 08/03/2016.
 */
public class Ship extends GameObjectView {

    private static int THRUST_FORCE = 440000;
    private static int FUEL_RATE_PER_SECOND = 100000;
    private static int MAX_STABILITY_FORCE = 400000;

    private RigidBodyImproved rgb;
    private World world;
    private View view;
    private int initialFuel;
    private double fuel;
    private double stabilityPower;

    public Ship(GameObject obj, World world, View view, int initialFuel) {
        super(obj);
        //check this
        GameObject obj3 = new GameObject(new Vector2D(10,300));
        obj3.setShape(new Circle(obj3, 6));
        obj3.mass = 4500;
        RigidBodyImproved rgb3 = new RigidBodyImproved(obj3);
        obj3.addRigidBody(rgb3);
        rgb = (RigidBodyImproved) obj.getBody();
        this.world = world;
        this.view = view;
        this.initialFuel = initialFuel;
        fuel = initialFuel;
        stabilityPower = 0;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        if (BasicKeyListener.isMoveUpPressed() && fuel > 0) {
            rgb.addForce(new Vector2D(0, 1 * THRUST_FORCE));
            fuel -= FUEL_RATE_PER_SECOND * delta;
        } else if (BasicKeyListener.isMoveDownPressed() && fuel > 0) {
            rgb.addForce(new Vector2D(0, -1*THRUST_FORCE));
            fuel -= FUEL_RATE_PER_SECOND * delta;
        }
        if (BasicKeyListener.isRotateLeftKeyPressed()) {
            object.rotate(-Math.PI / 6 * delta);
            Vector2D rotation = object.getRotation();
            System.out.println("rotation vec: " + rotation);
            System.out.println("angle is: " + Math.atan2(rotation.y, rotation.x));
        }
        else if (BasicKeyListener.isRotateRightKeyPressed()) {
            object.rotate(Math.PI/6 * delta);
        }
        if (BasicKeyListener.isFireButtonPressed()) {
            GameObject bullet = new GameObject(new Vector2D(object.getPosition()));
            bullet.setShape(new Circle(bullet, 0.5));
            Vector2D bulletForce = new Vector2D(object.getRotation());
            bulletForce.mult(10);
            bullet.mass = 1;
            RigidBodyImproved rgb = new RigidBodyImproved(bullet);
            bullet.addRigidBody(rgb);
            //rgb.addForce(bulletForce);

            world.addGameObject(bullet);

            Missile ball = new Missile(bullet, 2);
            ball.setColor(Color.GREEN);
            view.addObjectView(ball);

        }
        if (BasicKeyListener.isStabiliserEnabled()) {
            Vector2D stabiliserForce = getStabiliserForce(delta);
            stabilityPower += 500000 * delta;
            double magNeeded = stabiliserForce.mag();
            if (stabilityPower > magNeeded) {
                stabilityPower = magNeeded;
            }
            if (stabilityPower > MAX_STABILITY_FORCE) {
                stabilityPower = MAX_STABILITY_FORCE;
            }
            Vector2D stabilityDir = new Vector2D(stabiliserForce);
            stabilityDir.normalise();
            stabilityDir.mult(stabilityPower);
            rgb.addForce(stabilityDir);
        } else {
            stabilityPower = 0;
        }


    }

    public Vector2D getStabiliserForce(double delta) {
        /**
         * Force required to keep the ship stable against gravity
         * Uses the equation (mv-mu)/t = F
         */
        Vector2D finalVel = new Vector2D();
        Vector2D initialVel = new Vector2D(object.getVelocity());

        finalVel.mult(object.mass);
        initialVel.mult(-object.mass); //negative ready for subtraction
        finalVel.add(initialVel); //mv-mu
        finalVel.divide(delta); //divide by time
        return finalVel; //final force

    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (JEasyFrame.SCREEN.height-object.getPosition().y*yScreenScale);
        g.setColor(Color.GREEN);
        final int[] XP = { -2, 0, 2, 0 };
        final int[] YP = { 2, -2, 2, 0 };
        final int[] XPTHRUST = { -2, 0, 2, 0 };
        final int[] YPTHRUST = { 2, 3, 2, 0 };
        final double SCALE = 10; //radius

        AffineTransform at = g.getTransform();
        g.translate(x, y);
        Vector2D rotation = new Vector2D(object.getRotation());
        g.rotate(Math.PI / 2 - Math.atan2(rotation.y, rotation.x));
        g.scale(SCALE, SCALE);
        g.setColor(Color.GREEN);
        g.fillPolygon(XP, YP, XP.length);
        /*if (utilities.BasicKeyListener.isThrustKeyPressed()) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }*/
        g.setTransform(at);

        Circle circle = (Circle) object.getShape();
        g.setColor(Color.RED);
        double radius = circle.getRadius()*xScreenScale;
        g.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    public double getPercentageFuelRemaining() {
        return fuel/initialFuel * 100;
    }

}
