import Phys2d.CollideCallback;
import Phys2d.GameObject;
import Phys2d.Line;
import Phys2d.Vector2D;

import java.awt.*;

/**
 * Created by scottdavey on 12/04/2016.
 */
public class Barrier extends GameObjectView implements CollideCallback {
    Game game;
    public Barrier(Game g, Vector2D position, double length, double rotation) {
        game = g;
        object = new GameObject(position, this);
        object.tag = "Barrier";
        object.rotate(rotation);
        Line line = new Line(object, length, 1);
        object.setShape(line);
        game.getWorld().addGameObject(object);
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {

    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        Line line = (Line)object.getShape();
        Vector2D start = line.startOfLine();
        Vector2D end = line.endOfLine();

        int xStart = (int) (start.x*xScreenScale);
        int yStart = (int) (Game.maxHeight-start.y*yScreenScale);
        int xEnd = (int) (end.x*xScreenScale);
        int yEnd = (int) (Game.maxHeight-end.y*yScreenScale);
        g.setColor(Color.BLACK);
        g.drawLine(xStart, yStart, xEnd, yEnd);
    }

    @Override
    public void onCollide(String collidedTag) {

    }
}
