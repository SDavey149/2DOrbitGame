import Phys2d.World;
import Phys2d.Vector2D;

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

    List<ObjectView> objectViews;
    List<ObjectView> pending;
    List<Vector2D> stars;

    public View(World w, int width, int height) {
        world = w;
        this.width = width;
        this.height = height;
        objectViews = new ArrayList<>(100);
        random = new Random();
        pending = new ArrayList<>(3);

        //star background
        stars = new ArrayList<Vector2D>(200);
        for (int i = 0; i < 100; i++) {
            stars.add(new Vector2D(random.nextInt(width), random.nextInt(height)));
        }

    }

    @Override
    public void paintComponent(Graphics g0) {
        double xScale = getScreenXScale(), yScale = getScreenYScale();
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BG);
        g.fillRect(0, 0, width, height);
        drawStars(g);
        for (ObjectView objView : objectViews) {
            objView.draw(g, xScale, yScale);
        }
        synchronized (objectViews) {
            objectViews.addAll(pending);
        }
        pending.clear();
    }

    public void addObjectView(ObjectView v) {
        pending.add(v);
    }

    public void drawStars(Graphics2D g) {
        g.setColor(Color.WHITE);
        for (Vector2D pos : stars) {
            g.fillOval((int) pos.x - 1, (int) pos.y - 1,
                    2 * 1, 2 * 1);
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
