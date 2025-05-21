package ui;

import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private final int ROWS = 14;
    private final int COLS = 16;
    private final int TILE_SIZE = SlideMeowMain.GAME_HEIGHT / COLS;
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
        setPreferredSize(new Dimension(SlideMeowMain.GAME_WIDTH, SlideMeowMain.GAME_HEIGHT - 40));
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

        int totalWidth = COLS * TILE_SIZE;
        int totalHeight = ROWS * TILE_SIZE;

        int offsetX = (getWidth() - totalWidth) / 2;
        int offsetY = (getHeight() - totalHeight) / 2;

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                int tile = map[y][x];

                // 画地板
                g.drawImage(floorImg, offsetX + x * TILE_SIZE, offsetY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

                // 冰块或墙壁
                if (tile == 1) {
                    g.drawImage(iceImg, offsetX + x * TILE_SIZE, offsetY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }

                // 起点/终点提示可选
                if (tile == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(offsetX + x * TILE_SIZE + TILE_SIZE/4, offsetY + y * TILE_SIZE + TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
                }

                if (tile == 3) {
                    g.setColor(Color.CYAN);
                    g.fillOval(offsetX + x * TILE_SIZE + TILE_SIZE/4, offsetY + y * TILE_SIZE + TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
                }
            }
        }

        // 画猫猫
        g.drawImage(catImg, offsetX + playerX * TILE_SIZE, offsetY + playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
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

