import Phys2d.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class View extends JComponent {
    public static final Color BG = Color.BLACK;

    private World world;

    private int width;
    private int height;

    List<GameObjectView> objectViews;

    public View(World w, int width, int height) {
        world = w;
        this.width = width;
        this.height = height;
        objectViews = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setBackground(BG);
        for (Phys2d.GameObject obj : world.getGameObjects()) {

        }
    }

    public double getScreenXScale() {
        return (1/world.getWidth()*this.width);
    }

    public double getScreenYScale() {
        return (1/world.getHeight()*this.height);
    }


}
