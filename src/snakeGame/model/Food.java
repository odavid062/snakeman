package snakeGame.model;

import java.awt.*;
import java.util.Random;

public class Food {


        private int x;
        private int y;
        private int width;
        private int height;
        private int unitSize;
        private Random random;

        public Food(int width, int height, int unitSize){
            this.width = width;
            this.height = height;
            this.unitSize = unitSize;
            random = new Random();
            spawn();

        }

        public void spawn(){
            x = random.nextInt(width / unitSize) * unitSize;
            y = random.nextInt(height / unitSize) * unitSize;
        }

        public void draw(Graphics g, int unitSize) {
            g.setColor(Color.red);
            g.fillOval(x, y, unitSize, unitSize);
        }

        public int getX (){
            return x;

        }

        public int getY(){
            return y;
        }

    }

