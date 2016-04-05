package Phys2d;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Body {
    protected GameObject object;

    public Body(GameObject obj) {
        object = obj;
    }

    public void update(double delta) {
        object.getPosition().addScaled(object.getVelocity(), delta);
        object.getVelocity().addScaled(object.getAcceleration(), delta);
    }

    public boolean collidesWith(GameObject o1, GameObject o2) {
        return false;
    }
}

