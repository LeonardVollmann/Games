package nona.breakout.gamestate;

/*
 * Created by Leonard on 20/05/15.
 */

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {

    public static final int STATE_LEVEL = 0;
    public static final int STATE_GAME_OVER = 1;

    private ArrayList<GameState> gameStates;

    private int currentState;

    public GameStateManager() {
        currentState = STATE_LEVEL;
        gameStates = new ArrayList<GameState>();

        gameStates.add(new LevelState(this));
        gameStates.add(new GameOverState(this));

        for (GameState gs : gameStates) {
            gs.init();
        }
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void render(Graphics2D g) {
        gameStates.get(currentState).render(g);
    }

    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int state, boolean reinitialize) {
        this.currentState = state;
        if (reinitialize) {
            gameStates.get(currentState).init();
        }
    }

}
