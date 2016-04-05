package utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeyListener extends KeyAdapter {
    /* Author: Michael Fairbank
     * Creation Date: 2016-01-28
     * Significant changes applied:
     */
    private static boolean rotateRightKeyPressed, rotateLeftKeyPressed, upKeyPressed, downKeyPressed;

    public static boolean isRotateRightKeyPressed() {
        return rotateRightKeyPressed;
    }

    public static boolean isRotateLeftKeyPressed() {
        return rotateLeftKeyPressed;
    }

    public static boolean isMoveUpPressed() {
        return upKeyPressed;
    }

    public static boolean isMoveDownPressed() { return downKeyPressed; }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                upKeyPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                rotateLeftKeyPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rotateRightKeyPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downKeyPressed = true;
                break;

        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                upKeyPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                rotateLeftKeyPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rotateRightKeyPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downKeyPressed = false;
                break;
        }
    }
}
