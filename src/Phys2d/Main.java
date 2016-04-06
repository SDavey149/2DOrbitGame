package Phys2d;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World(100,100);
        GameObject obj = new GameObject(new Vector2D(100,100));
        RigidBodyImproved b = new RigidBodyImproved(obj);
        obj.addRigidBody(b);
        world.addGameObject(obj);
        for (int i = 0; i < 2f/World.DELTA_T; i++) {
            world.update(20/1000);
            System.out.println(obj.getPosition());
        }
    }
}
