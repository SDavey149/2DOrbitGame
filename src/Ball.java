import Phys2d.GameObject;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Ball extends GameObjectView {

    public Ball(GameObject obj) {
        super(obj);
    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {

        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (600-object.getPosition().y*yScreenScale);
        g.setColor(Color.RED);
        g.fillOval(x - 50, y - 50, 2 * 50, 2 * 50);
    }
}
