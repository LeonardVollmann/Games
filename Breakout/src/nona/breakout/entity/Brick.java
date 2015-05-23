package nona.breakout.entity;

import java.awt.*;

/*
 * Created by Leonard on 22/05/15.
 */

public class Brick extends Entity {

    private int width;
    private int height;
    private Color color;

    public Brick(int x, int y, int width, int height, Color color) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void keyPressed(int k) {
    }

    @Override
    public void keyReleased(int k) {
    }

}
