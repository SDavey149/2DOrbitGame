package Phys2d;

import utilities.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();
        GameObject obj = new GameObject(new Vector2D(100,100));
        RigidBody b = new RigidBody(obj, null);
        b.setMass(10);
        obj.addRigidBody(b);
        world.addGameObject(obj);
        for (int i = 0; i < 2f/World.DELTA_T; i++) {
            world.update();
            System.out.println(obj.getPosition());
        }
    }
}
