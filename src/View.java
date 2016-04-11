import Phys2d.GameObject;
import Phys2d.World;
import Phys2d.Vector2D;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by scottdavey on 02/03/2016.
 */
public class View extends JComponent {
    public static final Color BG = Color.BLACK;
    Random random;

    private World world;

    private int width;
    private int height;

    final List<GameObjectView> objectViews;
    final List<GameObjectView> pending;
    final List<Vector2D> stars;

    public View(World w, int width, int height) {
        world = w;
        this.width = width;
        this.height = height;
        objectViews = new ArrayList<>(100);
        random = new Random();
        pending = new ArrayList<>(3);
        //star background
        stars = new ArrayList<>(200);
        for (int i = 0; i < 100; i++) {
            stars.add(new Vector2D(random.nextInt(width), random.nextInt(height)));
        }

    }

    @Override
    public void paintComponent(Graphics g0) {
        List<GameObjectView> pendingRemoval = new ArrayList<>();
        double xScale = getScreenXScale(), yScale = getScreenYScale();
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BG);
        g.fillRect(0, 0, width, height);
        drawStars(g);
        for (GameObjectView objView : objectViews) {
            if (objView.isActive()) {
                objView.draw(g, xScale, yScale);
            } else {
                pendingRemoval.add(objView);
            }

        }
        synchronized (objectViews) {
            objectViews.removeAll(pendingRemoval);
            objectViews.addAll(pending);
        }
        pending.clear();
    }

    public void addObjectView(GameObjectView v) {
        pending.add(v);
    }

    public void drawStars(Graphics2D g) {
        g.setColor(Color.WHITE);
        for (Vector2D pos : stars) {
            g.fillOval((int) pos.x - 1, (int) pos.y - 1,
                    2, 2);
        }

    }

    public double getScreenXScale() {
        return (1/world.getWidth()*this.width);
    }

    public double getScreenYScale() {
        return (this.height/world.getHeight());
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }


}
