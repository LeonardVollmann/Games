package nona.breakout;

/*
 * Created by Leonard on 20/05/15.
 */

import nona.breakout.gamestate.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Breakout extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final String TITLE = "nona.breakout.Breakout";
    public static final double FPS = 60;
    public static final double SECOND = 1000000000;

    public static final Font FONT = new Font("Arial", Font.BOLD, 32);
    public static final Color COLOR_FONT = Color.WHITE;

    private GameStateManager gsm;

    public Breakout() {
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        requestFocus();
        setVisible(true);
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(TITLE);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();
        frame.setVisible(true);
        addKeyListener(this);

        this.gsm = new GameStateManager();

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        double unprocessed = 0;
        double nsPerUpdate = SECOND / FPS;
        long lastTimeMillis = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        boolean shouldRender = false;

        while (true) {
            now = System.nanoTime();

            unprocessed += (now - lastTime) / nsPerUpdate;

            while (unprocessed > 1) {
                unprocessed--;
                update();
                updates++;
                shouldRender = true;
            }

            if (shouldRender) {
                render();
                frames++;
                shouldRender = false;
            }

            if (System.currentTimeMillis() >= lastTimeMillis) {
                lastTimeMillis += 1000;
                System.out.println(1000.0 / frames + " ms per frame (" + frames + " fps, " + updates + " ups)");
                updates = 0;
                frames = 0;
            }

            lastTime = now;
        }
    }

    public void update() {
        gsm.update();
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        gsm.render(g);

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }

    public static void main(String[] args) {
        new Breakout();
    }

}
