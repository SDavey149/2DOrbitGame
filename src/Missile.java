import Phys2d.Circle;
import Phys2d.GameObject;
import Phys2d.RigidBodyImproved;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 06/04/2016.
 */
public class Missile extends GameObjectView {
    Circle circle;
    Color color;
    RigidBodyImproved rgb;

    public Missile(GameObject obj) {
        super(obj);
        circle = (Circle)obj.getShape();
        color = Color.RED;
        rgb = (RigidBodyImproved) obj.getBody();
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        //rgb.addForce(direction of missile);
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
