package snakeGame.controller;

import snakeGame.model.Snake;
import snakeGame.utils.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    private Snake snake;
    private Runnable pauseAction;
    private Runnable restartAction;

    public InputHandler(Snake snake, Runnable pauseAction, Runnable restartAction) {
        this.snake = snake;
        this.pauseAction = pauseAction;
        this.restartAction = restartAction;

    }

    @Override
    public void KeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                snake.changeDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT:
                snake.changeDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                snake.changeDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                snake.ChangeDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_P:
                pauseAction.run();
                break;
            case KeyEvent.VK_R:
                restartAction.run();
                break;

        }
    }


}
