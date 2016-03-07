package utilities;

import java.awt.*;

import javax.swing.JFrame;

public class JEasyFrame extends JFrame {
    public Component comp;

    public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle SCREEN = device.getDefaultConfiguration().getBounds();
    public static final double SCREEN_RATIO = SCREEN.width/SCREEN.height;

    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
