package Phys2d;


import utilities.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class RigidBody extends Body {

    private double mass;
    private double rollingFriction;
    private Shape shape;

    public RigidBody(GameObject obj, Shape shape) {
        super(obj);
        mass = 0;
        rollingFriction = 0;
        this.shape = shape;
    }

    @Override
    public void update() {
        Vector2D forceToApply = new Vector2D();
        forceToApply.add(getParticleWeight());
        object.getAcceleration().set(0, 0);
        if (forceToApply.mag() > 0) {
            object.getAcceleration().addScaled(forceToApply, 1 / mass);
        }
        //basic Euler
        object.getPosition().addScaled(object.getVelocity(), World.DELTA_T);
        object.getVelocity().addScaled(object.getAcceleration(), World.DELTA_T);
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    private Vector2D getParticleWeight() {
        return new Vector2D(0, -World.gravity*mass);
    }

    public boolean collidesWith(GameObject o1, GameObject o2) {
        return false;
    }
}
