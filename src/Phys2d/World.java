package Phys2d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scottdavey on 08/02/2016.
 */
public class World {
    static double gravity = 9.8;

    private double width;
    private double height;

    // sleep time between two drawn frames in milliseconds - NEEDS TO BE CHANGED
    public static final int DELAY = 10;
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
    // estimate for time between two frames in seconds
    public static final double DELTA_T = DELAY / 1000.0 / NUM_EULER_UPDATES_PER_SCREEN_REFRESH ;

    List<GameObject> gameObjects;

    public World(double width, double height) {
        gameObjects = new ArrayList<>(100);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void update() {
        for (int i = 0; i < NUM_EULER_UPDATES_PER_SCREEN_REFRESH; i++) {
            for (GameObject obj : gameObjects) {
                obj.update();
            }
        }

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
