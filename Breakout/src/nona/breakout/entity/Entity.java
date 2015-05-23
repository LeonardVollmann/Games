package nona.breakout.entity;

/*
 * Created by Leonard on 20/05/15.
 */

import java.awt.*;

public abstract class Entity {

    protected int x;
    protected int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity() {
        this(0, 0);
    }

    public abstract void update();
    public abstract void render(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
