import Phys2d.GameObject;
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
        objectViews = new ArrayList<>(100);
    }

    @Override
    public void paintComponent(Graphics g0) {
        double xScale = getScreenXScale(), yScale = getScreenYScale();
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BG);
        g.fillRect(0, 0, width, height);
        for (GameObjectView objView : objectViews) {
            objView.draw(g, xScale, yScale);
        }
    }

    public void addObjectView(GameObjectView v) {
        objectViews.add(v);
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
