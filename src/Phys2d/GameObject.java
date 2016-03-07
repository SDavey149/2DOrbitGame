package Phys2d;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NOTATIONDatatypeValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utilities.Vector2D;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class GameObject {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Shape shape;
    private double rotation;
    private Body body;
    public World world;

    public GameObject(Vector2D pos) {
        position = pos;
        this.body = new Body(this);
        velocity = new Vector2D(0,0);
        acceleration = new Vector2D(0,0);
        this.rotation = 0;
    }

    public void setWorld(World w) {
        world = w;
    }

    public void setShape(Shape sh) {
        shape = sh;
    }

    public Shape getShape() {
        return shape;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double theta) {
        rotation = theta;
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

    public boolean hasRigidBody() {
        return body instanceof RigidBody;
    }

    public Body getBody() {
        return body;
    }

    public Vector2D getGravitationalForce() {
        if (world != null) {
            return world.getGravitionalForce(this);
        }
        return new Vector2D(0,0);
    }

    public void applyForce() {
        throw new NotImplementedException();
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }
}
