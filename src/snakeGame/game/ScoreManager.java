package snakeGame.game;

import java.io.*;

public class ScoreManager {

    private int highScore = 0;
    private final String filePath = "highscore.txt";

    public ScoreManager() {
        loadHighScore();
    }

    public int getHighScore() {
        return highScore;
    }

    public void checkAndUpdateHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
            saveHighScore();
        }
    }

    private void loadHighScore() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                if (line != null) {
                    highScore = Integer.parseInt(line);
                }
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar high score: " + e.getMessage());
        }
    }

    private void saveHighScore() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar high score: " + e.getMessage());
        }
    }
}
