import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.PackedColorModel;

/**
 * Created by scottdavey on 08/03/2016.
 */
public class Ship extends GameObjectView implements CollideCallback{

    private static int THRUST_FORCE = 600000;

    private RigidBodyImproved rgb;
    private World world;
    private View view;
    final int[] XP = { -2, 0, 2, 0 };
    final int[] YP = { 2, -2, 2, 0 };
    final double SCALE = 10; //radius

    public Ship(World world, View view) {
        GameObject obj3 = new GameObject(new Vector2D(10,300), this);
        obj3.setShape(new Circle(obj3, 6));
        obj3.mass = 4500;
        rgb = new RigidBodyImproved(obj3);
        rgb.useGravity(false);
        obj3.addRigidBody(rgb);
        object = obj3;
        this.world = world;
        this.world.addGameObject(object);
        this.view = view;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        if (BasicKeyListener.isMoveUpPressed()) {
            //rgb.addForce(new Vector2D(0, THRUST_FORCE));
            object.getVelocity().set(0,100);
        } else if (BasicKeyListener.isMoveDownPressed()) {
            //rgb.addForce(new Vector2D(0, -THRUST_FORCE));
            object.getVelocity().set(0,-100);
        } else {
            object.getVelocity().set(0,0);
        }
        if (BasicKeyListener.isRotateLeftKeyPressed()) {
            object.rotate(-2 * Math.PI * delta);
        }
        else if (BasicKeyListener.isRotateRightKeyPressed()) {
            object.rotate(2 * Math.PI * delta);
        }
        if (BasicKeyListener.isFireButtonPressed()) {
            Vector2D missileDirection = object.getRotation();
            Missile ball = new Missile(world, new Vector2D(object.getPosition()), new Vector2D(missileDirection),
                    new Vector2D(object.getVelocity()), 5);
            ball.setColor(Color.GREEN);
            view.addObjectView(ball);

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

        AffineTransform at = g.getTransform();
        g.translate(x, y);
        Vector2D rotation = new Vector2D(object.getRotation());
        g.rotate(Math.PI / 2 - Math.atan2(rotation.y, rotation.x));
        g.scale(SCALE, SCALE);
        g.setColor(Color.GREEN);
        g.fillPolygon(XP, YP, XP.length);
        g.setTransform(at);

        Circle circle = (Circle) object.getShape();
        g.setColor(Color.RED);
        double radius = circle.getRadius()*xScreenScale;
        g.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    @Override
    public void onCollide() {
        System.out.println("Ship collided");
    }
}
