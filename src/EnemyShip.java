import Phys2d.Circle;
import Phys2d.Vector2D;
import Phys2d.World;

import java.awt.*;
import java.util.Random;

/**
 * Created by scottdavey on 11/04/2016.
 */
public class EnemyShip extends Ship {
    int shootTimeout = 0;
    Random random;
    private boolean rotateLeft = false;

    public EnemyShip(Game game, Vector2D position, double radius) {
        super(game, position, radius);
        object.tag = "Enemy";
        random = new Random();

    }


    @Override
    public void controls(double delta) {
        //no controls for the enemy.. would put AI in here
        shootTimeout++;
        if (shootTimeout > 150) {
            shootTimeout = 0;
            object.rotate(random.nextInt(4)-2);
            fire();
        }
    }

    @Override
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

    @Override
    public void onCollide(String collidedTag) {
        isActive = false;
        game.getWorld().destroy(object);
        game.requestNextLevel();
    }
}
