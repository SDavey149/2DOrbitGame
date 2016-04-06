import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by scottdavey on 08/03/2016.
 */
public class Ship extends GameObjectView {

    private static int THRUST_FORCE = 44000;

    private RigidBodyImproved rgb;
    private World world;
    private View view;

    public Ship(GameObject obj, World world, View view) {
        super(obj);
        //check this
        rgb = (RigidBodyImproved) obj.getBody();
        this.world = world;
        this.view = view;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        if (BasicKeyListener.isMoveUpPressed()) {
            rgb.addForce(new Vector2D(0, 1 * THRUST_FORCE));
        } else if (BasicKeyListener.isMoveDownPressed()) {
            rgb.addForce(new Vector2D(0, -1*THRUST_FORCE));
        }
        if (BasicKeyListener.isRotateLeftKeyPressed()) {
            object.setRotation(object.getRotation() - Math.PI * delta);
        }
        else if (BasicKeyListener.isRotateRightKeyPressed()) {
            object.setRotation(object.getRotation() + Math.PI * delta);
        }
        if (BasicKeyListener.isFireButtonPressed()) {
            GameObject bullet = new GameObject(new Vector2D(object.getPosition()));
            bullet.setShape(new Circle(bullet, 0.1));
            Vector2D bulletVelocity = object.getDirection();
            bulletVelocity.mult(5);
            bullet.setVelocity(bulletVelocity);
            bullet.mass = 0.1;
            bullet.addRigidBody(new RigidBodyImproved(bullet));
            world.addGameObject(bullet);

            Ball ball = new Ball(bullet);
            ball.setColor(Color.GREEN);
            view.addObjectView(ball);


        }
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
        g.translate(x,y);
        g.rotate(Math.PI/2 - object.getRotation());
        g.scale(SCALE, SCALE);
        g.setColor(Color.GREEN);
        g.fillPolygon(XP, YP, XP.length);
        /*if (utilities.BasicKeyListener.isThrustKeyPressed()) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }*/
        g.setTransform(at);
    }

}
