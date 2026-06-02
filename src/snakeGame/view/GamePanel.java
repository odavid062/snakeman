package snakeGame.view;

import snakeGame.controller.InputHandler;
import snakeGame.game.GameStateManager;
import snakeGame.game.GameStateManager.GameState;
import snakeGame.game.ScoreManager;
import snakeGame.model.Food;
import snakeGame.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int DELAY = 100;
    int currentDelay = DELAY;

    Snake snake;
    Food food;
    Timer timer;
    int score = 0;

    // MVC: estado do jogo e recorde persistente ficam em managers dedicados
    private final GameStateManager stateManager = new GameStateManager();
    private final ScoreManager scoreManager = new ScoreManager();

    JButton startButton;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        startButton = new JButton("Iniciar Jogo");
        startButton.setFont(new Font("Ink Free", Font.BOLD, 30));
        startButton.setFocusable(false);
        startButton.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 50, 300, 100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                startButton.setVisible(false);
            }
        });

        this.setLayout(null);
        this.add(startButton);

        // Controller desacoplado: recebe a cobra via Supplier (sempre a atual) e callbacks
        this.addKeyListener(new InputHandler(() -> snake, this::togglePause, this::restartGame));
    }

    public void startGame() {
        if (timer != null) {
            timer.stop();
        }

        snake = new Snake(WIDTH, HEIGHT, UNIT_SIZE);
        food = new Food(WIDTH, HEIGHT, UNIT_SIZE);
        score = 0;
        currentDelay = DELAY;
        stateManager.setState(GameState.RUNNING);

        timer = new Timer(currentDelay, this);
        timer.start();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (stateManager.getCurrentState() == GameState.MENU) {
            return;
        }

        if (stateManager.isGameOver()) {
            gameOver(g);
            return;
        }

        food.draw(g, UNIT_SIZE);
        snake.draw(g, UNIT_SIZE);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.drawString("Pontuação: " + score, 10, 30);

        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Recorde: " + scoreManager.getHighScore(), 10, 50);

        if (stateManager.isPaused()) {
            g.setColor(Color.yellow);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            g.drawString("Jogo Pausado - Pressione P", 50, HEIGHT / 2);
        }
    }

    public void adjustSpeed() {
        if (score % 5 == 0 && score != 0) {
            currentDelay = Math.max(30, currentDelay - 10);
            timer.setDelay(currentDelay);
        }
    }

    public void checkFood() {
        if (snake.getHeadX() == food.getX() && snake.getHeadY() == food.getY()) {
            snake.grow();
            food.spawn();
            score++;
            scoreManager.checkAndUpdateHighScore(score);
            adjustSpeed();
        }
    }

    public void checkCollisions() {
        if (snake.checkSelfCollision() || snake.checkWallCollision(WIDTH, HEIGHT)) {
            stateManager.setState(GameState.GAME_OVER);
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("GAME OVER", 100, HEIGHT / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.drawString("Pontuação Final: " + score, 150, HEIGHT / 2 + 50);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Recorde: " + scoreManager.getHighScore(), 150, HEIGHT / 2 + 80);

        g.drawString("Pressione R para jogar novamente", 120, HEIGHT / 2 + 110);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (stateManager.isRunning()) {
            snake.move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    /** Alterna entre RUNNING e PAUSED (tecla P). */
    public void togglePause() {
        if (stateManager.isRunning()) {
            stateManager.setState(GameState.PAUSED);
        } else if (stateManager.isPaused()) {
            stateManager.setState(GameState.RUNNING);
        }
    }

    /** Reinicia após game over (tecla R). */
    public void restartGame() {
        if (stateManager.isGameOver()) {
            startGame();
        }
    }
}
