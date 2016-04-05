import Phys2d.GameObject;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by scottdavey on 08/03/2016.
 */
public class Ship extends GameObjectView {

    public Ship(GameObject obj) {
        super(obj);
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        System.out.println(object.getPosition());
        System.out.println("A: " + object.getAcceleration());
        System.out.println("V: " + object.getVelocity());
        if (BasicKeyListener.isMoveUpPressed()) {
            object.setVelocity(new Vector2D(0, 20));
        }
        if (BasicKeyListener.isMoveDownPressed()) {
            object.setVelocity(new Vector2D(0, -20));
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
        g.rotate(Math.PI/2);
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
