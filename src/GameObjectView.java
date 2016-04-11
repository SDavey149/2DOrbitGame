import Phys2d.GameObject;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public abstract class GameObjectView {
    GameObject object;
    boolean isActive = true;

    public abstract void notificationOfNewTimeStep(double delta);

    public boolean isActive() {
        return isActive;
    }

    public abstract void draw(Graphics2D g, double xScreenScale, double yScreenScale);

}
