package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private final int TILE_SIZE = 48;
    private final int ROWS = 14;
    private final int COLS = 16;
    private final Image catImg;
    private final Image iceImg;
    private final Image floorImg;


    private final int[][] map = {
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
        catImg = new ImageIcon("slideMeow/resource/neko.png").getImage();
        iceImg = new ImageIcon("slideMeow/resource/icleblock.png").getImage();
        floorImg = new ImageIcon("slideMeow/resource/floor.png").getImage();
    }

    public void startGame() {
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                int tile = map[y][x];

                if (tile == 1) {
                    g.drawImage(iceImg, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                } else {
                    g.drawImage(floorImg, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }

                if (tile == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(x * TILE_SIZE + 16, y * TILE_SIZE + 16, TILE_SIZE / 2, TILE_SIZE / 2);
                }
                if (tile == 3) {
                    g.setColor(Color.CYAN);
                    g.fillOval(x * TILE_SIZE + 16, y * TILE_SIZE + 16, TILE_SIZE / 2, TILE_SIZE / 2);
                }

            }
        }
        int catWidth = TILE_SIZE + 10;
        int catHeight = TILE_SIZE + 10;
        int offsetX = (TILE_SIZE - catWidth) / 2;
        int offsetY = (TILE_SIZE - catHeight) / 2;

        g.drawImage(catImg, playerX * TILE_SIZE + offsetX, playerY * TILE_SIZE + offsetY, catWidth, catHeight, this);
    }

    private void slide(int dx, int dy) {
        boolean deslizandose = true;

        while (deslizandose) {
            int nextX = playerX + dx;
            int nextY = playerY + dy;

            int nextTile = map[nextY][nextX];

            if (nextTile == 1) {
                deslizandose = false;
            } else {
                playerX = nextX;
                playerY = nextY;
                repaint();

                if (nextTile == 2) {
                    JOptionPane.showMessageDialog(this, "😺 ¡Has llegado a la meta!");
                    deslizandose = false;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

