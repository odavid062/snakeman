package snakeGame.view;

import snakeGame.controller.InputHandler;
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
    boolean running = false;
    int score = 0;
    boolean paused = false;
    boolean gameStarted = false;
    int highScore = 0;

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
                gameStarted = true;
            }
        });

        this.setLayout(null);
        this.add(startButton);

        this.addKeyListener(new InputHandler(snake, this::togglePause, this::restartGame));
    }

    public void startGame() {
        if (timer != null) {
            timer.stop();
        }

        snake = new Snake(WIDTH, HEIGHT, UNIT_SIZE);
        food = new Food(WIDTH, HEIGHT, UNIT_SIZE);
        running = true;
        paused = false;
        score = 0;
        currentDelay = DELAY;

        timer = new Timer(currentDelay, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (!gameStarted) {
            return;
        }

        if (running) {
            food.draw(g, UNIT_SIZE);
            snake.draw(g, UNIT_SIZE);

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("Pontuação: " + score, 10, 30);

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("Recorde: " + highScore, 10, 50);

            if (paused) {
                g.setColor(Color.yellow);
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                g.drawString("Jogo Pausado - Pressione P", 50, HEIGHT / 2);
            }
        } else {
            gameOver(g);
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

            if (score > highScore) {
                highScore = score;
            }

            adjustSpeed();
        }
    }

    public void checkCollisions() {
        if (snake.checkSelfCollision() || snake.checkWallCollision(WIDTH, HEIGHT)) {
            running = false;
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
        g.drawString("Recorde: " + highScore, 150, HEIGHT / 2 + 80);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Pressione R para jogar novamente", 120, HEIGHT / 2 + 110);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            snake.move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    public void togglePause() {
        paused = !paused;
    }

    public void restartGame() {
        if (!running && gameStarted) {
            startGame();
        }
    }
}
