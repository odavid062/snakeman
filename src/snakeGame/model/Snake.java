package snakeGame.model;

import snakeGame.utils.Direction;

import java.awt.*;
import java.util.LinkedList;

public class Snake {


    private LinkedList<Point> body;
    private Direction direction;
    private int unitSize;

    public Snake(int width, int height, int unitSize) {
        this.unitSize = unitSize;
        body = new LinkedList<>();

        for (int i = 0; i < 6; i++) {
            body.add(new Point(100 - (i * unitSize), 100));
        }

        direction = Direction.RIGHT;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case UP:
                newHead.y -= unitSize;
                break;
            case DOWN:
                newHead.y += unitSize;
                break;
            case LEFT:
                newHead.x -= unitSize;
                break;
            case RIGHT:
                newHead.x += unitSize;
                break;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        Point tail = body.getLast();
        body.add(new Point(tail));
    }

    public void changeDirection(Direction newDirection) {
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection != Direction.UP) ||
                (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection;
        }
    }

    public boolean checkSelfCollision() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision(int width, int height) {
        Point head = body.getFirst();
        return (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height);
    }

    public void draw(Graphics g, int unitSize) {
        for (int i = 0; i < body.size(); i++) {
            if (i == 0) {
                g.setColor(Color.green);
            } else {
                g.setColor(new Color(45, 180, 0));
            }
            Point p = body.get(i);
            g.fillRect(p.x, p.y, unitSize, unitSize);
        }
    }

    public int getHeadX() {
        return body.getFirst().x;
    }

    public int getHeadY() {
        return body.getFirst().y;
    }
}

