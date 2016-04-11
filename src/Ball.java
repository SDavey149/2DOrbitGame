import Phys2d.*;
import utilities.JEasyFrame;

import java.awt.*;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Ball extends GameObjectView implements CollideCallback {

    Circle circle;
    Color color;


    public Ball(World world, double mass, Vector2D pos, double radius) {
        GameObject obj2 = new GameObject(pos, this);
        obj2.setShape(new Circle(obj2, radius));
        obj2.mass = mass;
        //RigidBodyImproved b2 = new RigidBodyImproved(obj2);
        //obj2.addRigidBody(b2);
        object = obj2;
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
        int y = (int) (JEasyFrame.SCREEN.height-object.getPosition().y*yScreenScale);
        g.setColor(color);
        double radius = circle.getRadius()*yScreenScale;
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void onCollide() {
        System.out.println("planet collided");
    }
}
