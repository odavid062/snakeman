package snakeGame.controller;

import snakeGame.model.Snake;
import snakeGame.utils.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Supplier;

public class InputHandler extends KeyAdapter {

    // Fornecedor da cobra atual: como a cobra é recriada a cada partida,
    // usamos um Supplier para sempre pegar a instância vigente (evita NPE).
    private final Supplier<Snake> snakeSupplier;
    private final Runnable pauseAction;
    private final Runnable restartAction;

    public InputHandler(Supplier<Snake> snakeSupplier, Runnable pauseAction, Runnable restartAction) {
        this.snakeSupplier = snakeSupplier;
        this.pauseAction = pauseAction;
        this.restartAction = restartAction;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Snake snake = snakeSupplier.get();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (snake != null) snake.changeDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                if (snake != null) snake.changeDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                if (snake != null) snake.changeDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                if (snake != null) snake.changeDirection(Direction.DOWN);
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
