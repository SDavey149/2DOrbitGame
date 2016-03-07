import Phys2d.AnchoredBarrier_StraightLine;
import utilities.JEasyFrame;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by scottdavey on 07/03/2016.
 */
public class BarrierView implements ObjectView {
    AnchoredBarrier_StraightLine barrier;

    public BarrierView(AnchoredBarrier_StraightLine ab) {
        barrier = ab;
    }

    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        Vector2D start = barrier.getStartPos();
        Vector2D end = barrier.getEndPos();

        int x1 = (int) (start.x*xScreenScale);
        int y1 = (int) (JEasyFrame.SCREEN.height-start.y*yScreenScale);
        int x2 = (int) (end.x*xScreenScale);
        int y2 = (int) (JEasyFrame.SCREEN.height-end.y*yScreenScale);
        g.setColor(Color.WHITE);
        g.drawLine(x1, y1, x2, y2);
    }
}
