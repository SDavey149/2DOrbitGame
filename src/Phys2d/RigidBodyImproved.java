package Phys2d;


import utilities.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class RigidBodyImproved extends Body {

    private double rollingFriction;
    private Vector2D forceToApply;
    private double mass;

    public RigidBodyImproved(GameObject obj) {
        super(obj);
        this.mass = obj.mass;
        rollingFriction = 0;
        forceToApply = new Vector2D();
    }

    @Override
    public void update(double delta) {
        //forceToApply.add(getParticleWeight());
        forceToApply.add(object.getGravitationalForce());
        object.getAcceleration().set(0, 0);
        if (forceToApply.mag() > 0) {
            object.getAcceleration().addScaled(forceToApply, 1/mass);
        }
        //basic Euler
        //object.getPosition().addScaled(object.getVelocity(), World.DELTA_T);
        //object.getVelocity().addScaled(object.getAcceleration(), World.DELTA_T);

        Vector2D vel2=new Vector2D(object.getVelocity());
        Vector2D pos2=new Vector2D(object.getPosition());
        //1 step ahead
        pos2.addScaled(object.getVelocity(), delta);
        vel2.addScaled(object.getAcceleration(), delta);

        Vector2D acc2=new Vector2D();
        Vector2D forceToApply2 = new Vector2D();
        forceToApply2.add(object.getGravitationalForceStepAhead(pos2, mass));
        if (forceToApply2.mag() > 0) {
            acc2.addScaled(forceToApply2, 1/mass);
        }
        //assuming acceleration is constant
        // Note acceleration is NOT CONSTANT for distance dependent forces such as
        // Hooke's law or newton's law of gravity, so this is BUG
        // in this Improved Euler implementation.
        // The whole program structure needs changing to fix this problem properly!
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
}
