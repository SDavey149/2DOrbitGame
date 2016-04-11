import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;
import Phys2d.Vector2D;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class Game {
    static int maxWidth;
    static int maxHeight;
    static double worldWidth;
    static double worldHeight;

    private World world;
    private View view;

    public static void main(String[] args) {
        maxWidth = JEasyFrame.SCREEN.width;
        maxHeight = JEasyFrame.SCREEN.height;
        double ratio = maxWidth/maxHeight;
        worldWidth = 500*ratio;
        worldHeight = 500;
        Game game = new Game(maxWidth, maxHeight, worldWidth, worldHeight);
        JEasyFrame frame = new JEasyFrame(game.getView(), "Orbit");
        frame.setSize(maxWidth, maxHeight);
        game.setup();
        game.runGame();

    }

    public Game(float maxWidth, float maxHeight, double worldWidth, double worldHeight) {
        world = new World(worldWidth,worldHeight);
        view = new View(world, (int)maxWidth, (int)maxHeight);
    }

    public World getWorld() {
        return world;
    }

    public View getView() {
        return view;
    }

    public void setup() {
        Ball ball2 = new Ball(world, 1000000000000000.0, new Vector2D(100,100), 20);
        view.addObjectView(ball2);

        Ball ball4 = new Ball(world, 5000000000000000.0, new Vector2D(300,300), 40);
        view.addObjectView(ball4);



        Ship ship = new Ship(world, view, new Vector2D(10,300), 12);
        view.addObjectView(ship);

        Ship enemyShip = new EnemyShip(world, view, new Vector2D(worldWidth-10, worldHeight-100), 12);
        enemyShip.rotate(Math.PI);
        view.addObjectView(enemyShip);



        view.addKeyListener(new BasicKeyListener());
        view.requestFocus();


    }


    public void runGame() {
        long lastTime = 0;
        while (true) {
            long currentTime = System.currentTimeMillis();
            double delta = (currentTime - lastTime)/1000.0;
            if (lastTime == 0) {
                delta = World.DELTA_T;
            }
            lastTime = currentTime;
            delta = delta/World.NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
            synchronized (view.objectViews) {
                for (GameObjectView objectView : view.objectViews) {
                    objectView.notificationOfNewTimeStep(delta);
                }
            }
            world.update(delta);
            view.repaint();
            try {
                Thread.sleep(World.DELAY);
            } catch (InterruptedException e) {
            }
        }
    }
}
