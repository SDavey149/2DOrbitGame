package Phys2d;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NOTATIONDatatypeValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utilities.Vector2D;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class GameObject {
    private Vector2D position;
    private Body body;

    public GameObject(Vector2D pos) {
        position = pos;
        this.body = new Body(this);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void addRigidBody(RigidBody body) {
        this.body = body;
    }

    public void update() {
        body.update();
    }

    public void applyForce() {
        throw new NotImplementedException();
    }

}
