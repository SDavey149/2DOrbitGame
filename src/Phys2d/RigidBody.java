package Phys2d;


/**
 * Created by scottdavey on 02/03/2016.
 */
public class RigidBody extends Body {

    private Vector2D forceToApply;
    private double mass;
    private boolean useGravity;
    private Vector2D frictionForce;

    public RigidBody(GameObject obj) {
        super(obj);
        this.mass = obj.mass;
        forceToApply = new Vector2D();
        frictionForce = new Vector2D();
        useGravity = true;
    }

    @Override
    public void update(double delta) {
        //a copy of forces to apply for improved euler, incase custom forces used
        Vector2D forceToApply2 = new Vector2D(forceToApply);

        if (useGravity)
            forceToApply.add(object.getGravitationalForce());
        object.getAcceleration().set(0, 0);
        if (forceToApply.mag() > 0) {
            object.getAcceleration().addScaled(forceToApply, 1/mass);
        }

        Vector2D vel2=new Vector2D(object.getVelocity());
        Vector2D pos2=new Vector2D(object.getPosition());

        //1 step ahead
        pos2.addScaled(object.getVelocity(), delta);
        vel2.addScaled(object.getAcceleration(), delta);
        Vector2D acc2=new Vector2D();
        if (useGravity)
            forceToApply2.add(object.getGravitationalForceStepAhead(pos2, mass));
        if (forceToApply2.mag() > 0) {
            acc2.addScaled(forceToApply2, 1/mass);
        }
        vel2.add(object.getVelocity());
        vel2.mult(0.5);
        acc2.add(object.getAcceleration());
        acc2.mult(0.5);

        object.getPosition().addScaled(vel2, delta);
        object.getVelocity().addScaled(acc2, delta);

        forceToApply = new Vector2D();
    }


    private Vector2D getParticleWeight() {
        return new Vector2D(0, -World.gravity*mass);
    }

    public boolean collidesWith(GameObject o1, GameObject o2) {
        return false;
    }

    public void addForce(Vector2D force) {
        forceToApply.add(force);
    }

    public void useGravity(boolean gravity) {
        useGravity = gravity;
    }

    public Vector2D getFrictionForce() {
        return frictionForce;
    }
}
