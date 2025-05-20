import javax.swing.JFrame;
import game.GamePanel;
import info.GameInfoPanel;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Slide Meow!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GameInfoPanel infoPanel = new GameInfoPanel();
        GamePanel gamePanel = new GamePanel();

        frame.setLayout(new BorderLayout());
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGame();
    }
}

