package nona.breakout.entity;/*
 * Created by Leonard on 20/05/15.
 */

import nona.breakout.Breakout;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_SPEED = 10;

    private int width;
    private int height;
    private int speed;
    private boolean right;
    private boolean left;

    public Player() {
        super((Breakout.WIDTH / 2) - (DEFAULT_WIDTH / 2), Breakout.HEIGHT - DEFAULT_HEIGHT - 1);
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.speed = DEFAULT_SPEED;
    }

    @Override
    public void update() {
        if (left) {
            if (x - speed < 0) {
                x = 0;
            } else {
                x -= speed;
            }
        }
        if (right) {
            if (x + speed + width > Breakout.WIDTH) {
                x = Breakout.WIDTH - width;
            } else {
                x += speed;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(DEFAULT_COLOR);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) {
            left = true;
        } else if (k == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            left = false;
        } else if (k == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
