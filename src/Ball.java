import Phys2d.Circle;
import Phys2d.GameObject;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Ball extends GameObjectView {

    Circle circle;
    public Ball(GameObject obj) {
        super(obj);
        circle = (Circle)obj.getShape();
    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {

        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (JEasyFrame.SCREEN.height-object.getPosition().y*yScreenScale);
        g.setColor(Color.RED);
        double radius = circle.getRadius()*xScreenScale;
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }
}
