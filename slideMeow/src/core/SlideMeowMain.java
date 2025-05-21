package core;

import ui.GameInfoPanel;
import ui.GamePanel;
import ui.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class SlideMeowMain {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    private JFrame frame;
    private MenuPanel menuPanel;
    private JPanel juegoPanel;

    public SlideMeowMain() {
        frame = new JFrame("Slide Meow!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // 主菜单
        menuPanel = new MenuPanel(this);
        menuPanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        frame.setContentPane(menuPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void mostrarJuego() {
        // 游戏界面
        juegoPanel = new JPanel(new BorderLayout());

        GameInfoPanel infoPanel = new GameInfoPanel();
        infoPanel.setPreferredSize(new Dimension(GAME_WIDTH, 40));

        GamePanel gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT - 40));

        juegoPanel.add(infoPanel, BorderLayout.NORTH);
        juegoPanel.add(gamePanel, BorderLayout.CENTER);

        frame.setContentPane(juegoPanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        new SlideMeowMain();
    }
}