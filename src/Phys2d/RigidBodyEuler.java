package Phys2d;

/**
 * Created by scottdavey on 27/03/2016.
 */
public class RigidBodyEuler extends Body {
    private double rollingFriction;
    Vector2D forceToApply;
    double mass;

    public RigidBodyEuler(GameObject obj, double mass) {
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
        object.getPosition().addScaled(object.getVelocity(), delta);
        object.getVelocity().addScaled(object.getAcceleration(), delta);

        forceToApply = new Vector2D();
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
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
