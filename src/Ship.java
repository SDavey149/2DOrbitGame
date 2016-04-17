import Phys2d.*;
import utilities.BasicKeyListener;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by scottdavey on 08/03/2016.
 */
public class Ship extends GameObjectView implements CollideCallback{

    private final static int THRUST_FORCE = 500;
    private final static int FIRE_TIMEOUT = 50;

    protected RigidBody rgb;
    protected Game game;
    final int[] XP = { -2, 0, 2, 0 };
    final int[] YP = { 2, -2, 2, 0 };
    int lastFired;

    public Ship(Game game, Vector2D position, double radius) {
        GameObject obj3 = new GameObject(position, this);
        obj3.tag = "Ship";
        obj3.setShape(new Circle(obj3, radius));
        obj3.mass = 1;
        rgb = new RigidBody(obj3);
        rgb.useGravity(false);
        obj3.addRigidBody(rgb);
        object = obj3;
        this.game = game;
        this.game.getWorld().addGameObject(object);
        lastFired = 0;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {
        controls(delta);
    }

    public void controls(double delta) {
        if (BasicKeyListener.isMoveUpPressed()) {
            moveUp();
        } else if (BasicKeyListener.isMoveDownPressed()) {
            moveDown();
        } else {
            object.getVelocity().mult(0.95);
        }
        if (BasicKeyListener.isRotateLeftKeyPressed()) {
            rotateLeft(delta);
        }
        else if (BasicKeyListener.isRotateRightKeyPressed()) {
            rotateRight(delta);
        }
        if (BasicKeyListener.isFireButtonPressed() && lastFired <= 0) {
            fire();
            lastFired = FIRE_TIMEOUT;
        }
        lastFired--;

    }

    public void moveUp() {
        rgb.addForce(new Vector2D(0, THRUST_FORCE));
    }

    public void moveDown() {
        rgb.addForce(new Vector2D(0, -THRUST_FORCE));
    }

    public void rest() {
        object.getVelocity().set(0,0);
    }

    public void rotateLeft(double delta) {
        object.rotate(-2 * Math.PI * delta);
    }

    public void rotateRight(double delta) {
        object.rotate(2 * Math.PI * delta);
    }

    public void fire() {
        Vector2D missileDirection = object.getRotation();
        Vector2D position = new Vector2D(object.getPosition());
        double radius = ((Circle)(object.getShape())).getRadius();
        //spawn it outside of ship, not sure why the radius must be /2, mismatch somewhere..
        position.addScaled(missileDirection, (radius / 2) + 1);
        Missile ball = new Missile(game.getWorld(), position, new Vector2D(missileDirection), 5);
        ball.setColor(Color.GREEN);
        game.getView().addObjectView(ball);
    }

    public void rotate(double angle) {
        object.rotate(angle);
    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (Game.maxHeight-object.getPosition().y*yScreenScale);
        g.setColor(Color.GREEN);
        Circle circle = (Circle) object.getShape();
        double radius = circle.getRadius()*Math.min(yScreenScale, xScreenScale);
        AffineTransform at = g.getTransform();
        g.translate(x, y);
        Vector2D rotation = new Vector2D(object.getRotation());
        g.rotate(Math.PI / 2 - Math.atan2(rotation.y, rotation.x));
        g.scale(radius/2, radius/2);
        g.setColor(Color.GREEN);
        g.fillPolygon(XP, YP, XP.length);
        g.setTransform(at);

        g.setColor(Color.RED);
        g.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    @Override
    public void onCollide(String collidedTag) {
        isActive = false;
        game.getWorld().destroy(object);
        game.requestGameOver();
    }

    public Circle getShape() {
        return (Circle)object.getShape();
    }
}
