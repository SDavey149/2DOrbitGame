import Phys2d.GameObject;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public abstract class GameObjectView implements ObjectView {
    GameObject object;

    public GameObjectView(GameObject object) {
        this.object = object;
    }

}
