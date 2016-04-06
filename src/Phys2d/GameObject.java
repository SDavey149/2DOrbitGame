package Phys2d;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class GameObject {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Shape shape;
    private double rotation;
    public double mass;
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

    public void addRigidBody(Body body) {
        this.body = body;
    }

    public void update(double delta) {
        body.update(delta);
    }

    public boolean hasRigidBody() {
        return body instanceof RigidBodyImproved
                || body instanceof RigidBodyEuler;
    }

    public Body getBody() {
        return body;
    }

    public Vector2D getGravitationalForce() {
        if (world != null) {
            return world.getGravitationalForce(this, this.getPosition(), this.mass);
        }
        return new Vector2D(0,0);
    }

    public Vector2D getGravitationalForceStepAhead(Vector2D position, double mass) {
        if (world != null) {
            return world.getGravitationalForce(this, position, mass);
        }
        return new Vector2D(0,0);
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

    public void setAcceleration(Vector2D acc) {
        acceleration = acc;
    }

    public Vector2D getDirection() {
        return new Vector2D(Math.cos(rotation), Math.sin(rotation));
    }

}
