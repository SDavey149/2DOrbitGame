import Phys2d.Vector2D;
import Phys2d.World;

/**
 * Created by scottdavey on 11/04/2016.
 */
public class EnemyShip extends Ship {
    public EnemyShip(World world, View view, Vector2D position, double radius) {
        super(world, view, position, radius);
    }

    @Override
    public void controls(double delta) {
        //no controls for the enemy.. would put AI in here
    }
}
