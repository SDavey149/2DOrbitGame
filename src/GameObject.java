import utilities.Vector2D;

import java.util.Vector;

/**
 * Created by scottdavey on 08/02/2016.
 */
public abstract class GameObject {
    Vector2D position;
    Vector2D velocity;
    Vector2D acceleration;
    Renderer renderer;

    public GameObject(Vector2D pos, Vector2D vel, Vector2D acc) {
        position = pos;
        velocity = vel;
        acceleration = acc;
    }

    public abstract void update();

    public abstract void applyForce();

    public void render() {
        renderer.draw();
    }
}
