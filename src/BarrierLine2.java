import Phys2d.GameObject;
import Phys2d.Line;
import utilities.JEasyFrame;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by scottdavey on 27/03/2016.
 */
public class BarrierLine2 implements ObjectView {

    GameObject object;

    public BarrierLine2(GameObject object) {
        this.object = object;
    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        Line line = (Line)object.getShape();
        Vector2D start = line.startOfLine();
        Vector2D end = line.endOfLine();

        int x1 = (int) (start.x*xScreenScale);
        int y1 = (int) (JEasyFrame.SCREEN.height-start.y*yScreenScale);
        int x2 = (int) (end.x*xScreenScale);
        int y2 = (int) (JEasyFrame.SCREEN.height-end.y*yScreenScale);
        g.setColor(Color.RED);
        g.drawLine(x1, y1, x2, y2);
    }
}
