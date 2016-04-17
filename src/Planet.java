import Phys2d.*;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Planet extends GameObjectView implements CollideCallback {

    Circle circle;
    Color color;


    public Planet(World world, double mass, Vector2D pos, double radius) {
        object = new GameObject(pos, this);
        object.setShape(new Circle(object, radius));
        object.mass = mass;
        //RigidBody b2 = new RigidBody(obj2);
        //obj2.addRigidBody(b2);
        world.addGameObject(object);

        circle = (Circle)object.getShape();
        color = Color.RED;
    }

    @Override
    public void notificationOfNewTimeStep(double delta) {

    }

    @Override
    public void draw(Graphics2D g, double xScreenScale, double yScreenScale) {
        int x = (int) (object.getPosition().x*xScreenScale);
        int y = (int) (Game.maxHeight-object.getPosition().y*yScreenScale);
        g.setColor(color);
        double radius = circle.getRadius()*Math.min(yScreenScale, xScreenScale);
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void onCollide(String collidedTag) {

    }

    public Circle getShape() {
        return (Circle)object.getShape();
    }
}
