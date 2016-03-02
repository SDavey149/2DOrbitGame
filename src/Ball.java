/**
 * Created by scottdavey on 02/03/2016.
 */
public class Ball implements GameObjectView {


    @Override
    public void draw(double xScreenScale, double yScreenScale) {
        int x = BasicPhysicsEngine.convertWorldXtoScreenX(getPos().x);
        int y = BasicPhysicsEngine.convertWorldYtoScreenY(getPos().y);
        g.setColor(col);
        g.fillOval(x - SCREEN_RADIUS, y - SCREEN_RADIUS, 2 * SCREEN_RADIUS, 2 * SCREEN_RADIUS);
    }
}
