package nona.breakout.entity;/*
 * Created by Leonard on 20/05/15.
 */

import nona.breakout.Breakout;

import java.awt.*;
import java.util.Random;

public class Ball extends Entity {

    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final float DEFAULT_SPEED = 5.0f;
    public static final float DEFAULT_ACCELERATION = 0.1f;
    public static final int DEFAULT_SIZE = 4;
    public static final float MAX_SPEED_CHANGE = Player.DEFAULT_WIDTH / 12;

    private float xSpeed;
    private float ySpeed;
    private int size;

    public Ball(int x, int y) {
        super(x, y);

        Random random = new Random();
        if (random.nextBoolean()) {
            this.xSpeed = DEFAULT_SPEED;
        } else {
            this.xSpeed = -DEFAULT_SPEED;
        }
        this.ySpeed = -DEFAULT_SPEED;
        this.size = DEFAULT_SIZE;
    }

    @Override
    public void update() {
        if (x - (size / 2) < 0 || x + (size / 2) > Breakout.WIDTH) {
            xSpeed *= -1;
            accelerate();
        }
        if (y - (size / 2) < 0) {
            ySpeed *= -1;
            accelerate();
        }

        x += xSpeed;
        y += ySpeed;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(DEFAULT_COLOR);
        g.fillOval(x - (size / 2), y - (size / 2), size, size);
    }

    @Override
    public void keyPressed(int k) {
    }

    @Override
    public void keyReleased(int k) {
    }

    public void accelerate() {
        xSpeed += DEFAULT_ACCELERATION;
        ySpeed += DEFAULT_ACCELERATION;
    }

    public float getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
