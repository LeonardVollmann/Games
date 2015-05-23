package nona.breakout.gamestate;

/*
 * Created by Leonard on 21/05/15.
 */

import nona.breakout.Breakout;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState {

    private static final String STRING1 = "GAME OVER!";
    private static final String STRING2 = "PRESS <ENTER> TO PLAY AGAIN!";

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics(Breakout.FONT);
        g.setColor(Breakout.COLOR_FONT);
        g.setFont(Breakout.FONT);

        g.drawString(STRING1, (int) (Breakout.WIDTH / 2 - (metrics.getStringBounds(STRING1, g).getWidth() / 2)), Breakout.HEIGHT / 2 - 50);
        g.drawString(STRING2, (int) (Breakout.WIDTH / 2 - (metrics.getStringBounds(STRING2, g).getWidth() / 2)), Breakout.HEIGHT / 2 + 50);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            System.out.println("Pressed Enter");
            gsm.setCurrentState(GameStateManager.STATE_LEVEL, true);
        }
    }

    @Override
    public void keyReleased(int k) {
    }

}
