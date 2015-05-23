package nona.breakout.gamestate;

/*
 * Created by Leonard on 20/05/15.
 */

import nona.breakout.Breakout;
import nona.breakout.entity.Ball;
import nona.breakout.entity.Brick;
import nona.breakout.entity.Entity;
import nona.breakout.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LevelState extends GameState {

    private ArrayList<Entity> entities;
    private ArrayList<Brick> bricks;
    private Player player;
    private Ball ball;

    public LevelState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        this.entities = new ArrayList<Entity>();
        this.bricks = new ArrayList<Brick>();
        this.player = new Player();
        this.ball = new Ball(Breakout.WIDTH / 2, Breakout.HEIGHT / 2);

        entities.add(player);
        entities.add(ball);

        final int brickWidth = 35;
        final int brickHeight = 15;
        final float offset = 20;
        final float numCols = (Breakout.WIDTH - (2 * offset)) / (brickWidth) - 2;
        final float numRows = 25;
        final float dist = 2;
        Random random = new Random();
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                float r = random.nextFloat();
                float g = random.nextFloat();
                float b = random.nextFloat();
                Color color = new Color(r, g, b);

                bricks.add(new Brick((int) (x * brickWidth + offset + (x * dist)), (int) (y * brickHeight + offset + (y * dist)), brickWidth, brickHeight, color));
            }
        }
    }

    @Override
    public void update() {
        if (ball.getY() >= Breakout.HEIGHT - player.getHeight() && ball.getX() >= player.getX() && ball.getX() <= player.getX() + player.getWidth()) {
            float speedChange = ((float) (ball.getX() - player.getX()) / (float) player.getWidth() - 0.5f) * 2.0f;

            ball.setXSpeed(ball.getXSpeed() + (speedChange * Ball.MAX_SPEED_CHANGE));
            ball.setYSpeed(ball.getYSpeed() * -1);
            ball.accelerate();
        } else if (ball.getY() >= Breakout.HEIGHT) {
            gsm.setCurrentState(GameStateManager.STATE_GAME_OVER, false);
        }

        for (Entity e : entities) {
            e.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (Entity e : entities) {
            e.render(g);
        }

        for (Brick b : bricks) {
            b.render(g);
        }
    }

    @Override
    public void keyPressed(int k) {
        for (Entity e : entities) {
            e.keyPressed(k);
        }
    }

    @Override
    public void keyReleased(int k)
    {
        for (Entity e : entities) {
            e.keyReleased(k);
        }
    }
}
