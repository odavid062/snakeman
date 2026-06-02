package snakeGame.game;

public class GameStateManager {

    public enum GameState{
        MENU,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private GameState currentState = GameState.MENU;

    public GameState getCurrentState(){
        return currentState;

    }

    public setState (GameState newState){
        currentState = newState;

    }

    public boolean isRunning(){
        return currentState == GameState.RUNNING;

    }

    public boolean isPaused(){
        return currentState == GameState.PAUSED;

    }

    public boolean isGameOver(){
        return currentState == GameState.GAME_OVER;
    }
}
