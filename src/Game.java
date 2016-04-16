import Phys2d.*;
import utilities.BasicKeyListener;
import utilities.JEasyFrame;
import Phys2d.Vector2D;

import javax.swing.*;
import java.util.Random;

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
    private boolean moveToNextLevel = false;
    private boolean gameOver = false;
    private Random random;
    private int level = 1;

    public static void main(String[] args) {
        maxWidth = (int)(JEasyFrame.SCREEN.width*0.9);
        maxHeight = (int)(JEasyFrame.SCREEN.height*0.9);
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
        random = new Random();
    }

    public World getWorld() {
        return world;
    }

    public View getView() {
        return view;
    }

    public void setup() {

        Ball ball2 = new Ball(world, 1000000000000000.0, new Vector2D(100,100), 10);
        view.addObjectView(ball2);

        Ball ball4 = new Ball(world, 5000000000000000.0, new Vector2D(300,300), 50);
        view.addObjectView(ball4);

        Ship ship = new Ship(this, new Vector2D(10,250), 12);
        view.addObjectView(ship);

        Ship enemyShip = new EnemyShip(this, new Vector2D(worldWidth-10, worldHeight-100), 12);
        enemyShip.rotate(Math.PI);
        view.addObjectView(enemyShip);

        addBarriers();

        view.addKeyListener(new BasicKeyListener());
        view.requestFocus();


    }

    private void addBarriers() {
        Barrier bottom = new Barrier(this, new Vector2D(worldWidth/2,0), worldWidth, 0);
        view.addObjectView(bottom);

        Barrier top = new Barrier(this, new Vector2D(worldWidth/2, worldHeight),worldWidth,0);
        view.addObjectView(top);
    }


    public void runGame() {
        long lastTime = 0;
        while (true) {
            view.setLevel(level);
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
            if (moveToNextLevel) {
                nextLevel();
                moveToNextLevel = false;
            }
            if (isGameOver()) {
                String[] options = {"Play Again", "Quit"};
                int n = JOptionPane.showOptionDialog(null,
                        "Game over. You got to level " + level + ". Would you like to play again?",
                        "Game over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[1]); //default button title
                System.out.println(n);
                if (n == 1) {
                    System.exit(0);
                } else {
                    //play again
                    world.reset();
                    view.reset();
                    setup();
                    gameOver = false;
                }

            }
            try {
                Thread.sleep(World.DELAY);
            } catch (InterruptedException e) {
            }
        }
    }


    public void requestNextLevel() {
        moveToNextLevel = true;
    }

    public void requestGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void nextLevel() {
        //levels after first are randomised
        world.reset();
        view.reset();
        level++;
        //masses between 1000000000000000.0, 6000000000000000.0
        double mass1 = random.nextDouble() * 5000000000000000.0 + 1000000000000000.0;
        double mass2 = random.nextDouble() * 5000000000000000.0 + 1000000000000000.0;

        Vector2D pos1 = new Vector2D(random.nextInt(200)+50, random.nextInt(300)+100);
        Vector2D pos2 = new Vector2D(random.nextInt(150)+300, random.nextInt(300)+100);

        Ball ball2 = new Ball(world, mass1, pos1, mass1/100000000000000.0);
        view.addObjectView(ball2);

        Ball ball4 = new Ball(world, mass2, pos2, mass2/100000000000000.0);
        view.addObjectView(ball4);


        Ship ship = new Ship(this, new Vector2D(10,250), 12);
        view.addObjectView(ship);

        addBarriers();

        double yPos = random.nextInt((int)worldHeight-80)+40; //get y pos, not too close to edges
        Ship enemyShip = new EnemyShip(this, new Vector2D(worldWidth-10, yPos), 12);
        enemyShip.rotate(Math.PI);
        view.addObjectView(enemyShip);
    }
}
