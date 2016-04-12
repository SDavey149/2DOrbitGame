import Phys2d.Vector2D;
import Phys2d.World;

/**
 * Created by scottdavey on 11/04/2016.
 */
public class EnemyShip extends Ship {
    public EnemyShip(Game game, Vector2D position, double radius) {
        super(game, position, radius);
        object.tag = "Enemy";
    }

    @Override
    public void controls(double delta) {
        //no controls for the enemy.. would put AI in here
    }

    @Override
    public void onCollide(String collidedTag) {
        isActive = false;
        game.getWorld().destroy(object);
        game.requestNextLevel();
    }
}
