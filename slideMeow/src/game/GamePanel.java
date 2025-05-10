package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private final int TILE_SIZE = 48;
    private final int ROWS = 14;
    private final int COLS = 16;

    private int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1}
    };


    private int playerX = 14, playerY = 13;

    public GamePanel() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
    }

    public void startGame() {
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                switch (map[y][x]) {
                    case 1 -> g.setColor(Color.DARK_GRAY); // Pared
                    case 2 -> g.setColor(Color.YELLOW);    // Meta
                    case 3 -> g.setColor(Color.CYAN);      // Inicio
                    default -> g.setColor(Color.LIGHT_GRAY); // Camino
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        g.setColor(Color.PINK);
        g.fillOval(playerX * TILE_SIZE + 8, playerY * TILE_SIZE + 8, TILE_SIZE - 16, TILE_SIZE - 16);
    }

    private void slide(int dx, int dy) {
        while (true) {
            int nextX = playerX + dx;
            int nextY = playerY + dy;

            if (map[nextY][nextX] == 1) break;

            playerX = nextX;
            playerY = nextY;
            repaint();

            if (map[playerY][playerX] == 2) {
                JOptionPane.showMessageDialog(this, "\uD83D\uDE3A ¡Has llegado a la meta!");
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> slide(0, -1);
            case KeyEvent.VK_DOWN -> slide(0, 1);
            case KeyEvent.VK_LEFT -> slide(-1, 0);
            case KeyEvent.VK_RIGHT -> slide(1, 0);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}

