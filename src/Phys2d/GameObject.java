package Phys2d;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class GameObject {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Vector2D rotation;
    private Shape shape;
    public double mass;
    private Body body;
    public World world;
    private CollideCallback collider;
    public String tag = "Unknown";

    public GameObject(Vector2D pos, CollideCallback collider) {
        position = pos;
        this.body = new Body(this);
        velocity = new Vector2D();
        acceleration = new Vector2D();
        rotation = new Vector2D(1,0);
        this.collider = collider;
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

    public Vector2D getRotation() {
        return rotation;
    }

    public void rotate(double theta) {
        rotation.rotate(theta);
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
        return body instanceof RigidBody;
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

    public void collided(String tag) {
        collider.onCollide(tag);
    }


}
