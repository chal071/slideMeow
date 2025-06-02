package ui;

import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private int rows;
    private int cols;
    private int tile;
    private int[][] map;

    private Image catImg;
    private Image iceImg;
    private Image floorImg;

    private SlideMeowMain parent;
    private String mapaNombre;
    private String archivoMapa;

    private int playerX, playerY;
    private long startTime;

    private int usuarioId;

    public GamePanel(SlideMeowMain parent, int usuarioId, String mapaNombre, String archivoMapa) {
        this.parent = parent;
        this.usuarioId = usuarioId;
        this.mapaNombre = mapaNombre;
        this.archivoMapa = archivoMapa;

        cargarMapaDesdeArchivo(archivoMapa);
        encontrarInicioDesdeMapa();

        tile = SlideMeowMain.gameHeight / cols;
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, SlideMeowMain.gameHeight - 40));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        catImg = new ImageIcon("slideMeow/resource/neko.png").getImage();
        iceImg = new ImageIcon("slideMeow/resource/icleblock.png").getImage();
        floorImg = new ImageIcon("slideMeow/resource/floor.png").getImage();

        startTime = System.currentTimeMillis();
    }

    public void cargarMapaDesdeArchivo(String ruta) {
        ArrayList<int[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.trim().split(";");
                int[] fila = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    fila[i] = Integer.parseInt(tokens[i]);
                }
                filas.add(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al leer la mapa: " + e.getMessage());
        }

        map = new int[filas.size()][filas.get(0).length];
        for (int i = 0; i < filas.size(); i++) {
            map[i] = filas.get(i);
        }
        rows = map.length;
        cols = map[0].length;
    }

    public void encontrarInicioDesdeMapa() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 3) {
                    playerX = x;
                    playerY = y;
                    return;
                }
            }
        }
    }

    private void guardarResultadoEnBD(int usuarioId, String dificultad, String archivo, int tiempo) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/slideMeow", "root", "mysql")) {
            String insertMapa = "INSERT IGNORE INTO mapas (nombre, archivo) VALUES (?, ?)";
            PreparedStatement stmtInsert = con.prepareStatement(insertMapa);
            stmtInsert.setString(1, dificultad);
            stmtInsert.setString(2, archivo);
            stmtInsert.executeUpdate();

            String selectMapa = "SELECT id FROM mapas WHERE archivo = ?";
            PreparedStatement stmtSelect = con.prepareStatement(selectMapa);
            stmtSelect.setString(1, archivo);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                SlideMeowMain.mapaId = rs.getInt("id");
            }

            if (SlideMeowMain.mapaId != -1) {
                String insertRanking = "INSERT INTO ranking (usuario_id, mapa_id, tiempo) VALUES (?, ?, ?)";
                PreparedStatement stmtRanking = con.prepareStatement(insertRanking);
                stmtRanking.setInt(1, usuarioId);
                stmtRanking.setInt(2, SlideMeowMain.mapaId);
                stmtRanking.setInt(3, tiempo);
                stmtRanking.executeUpdate();
            } else {
                System.err.println("No se pudo obtener el ID del mapa.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("rror al guardar resultado en la base de datos.");
        }
    }

    public void startGame() {
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int totalWidth = cols * tile;
        int totalHeight = rows * tile;
        int offsetX = (getWidth() - totalWidth) / 2;
        int offsetY = (getHeight() - totalHeight) / 2;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int tile = map[y][x];
                g.drawImage(floorImg, offsetX + x * this.tile, offsetY + y * this.tile, this.tile, this.tile, this);
                if (tile == 1) g.drawImage(iceImg, offsetX + x * this.tile, offsetY + y * this.tile, this.tile, this.tile, this);
                if (tile == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(offsetX + x * this.tile + this.tile / 4, offsetY + y * this.tile + this.tile / 4, this.tile / 2, this.tile / 2);
                }
                else if (tile == 3) {
                    g.setColor(Color.cyan);
                    g.fillOval(offsetX + x * this.tile + this.tile / 4, offsetY + y * this.tile + this.tile / 4, this.tile / 2, this.tile / 2);
                }
            }
        }
        g.drawImage(catImg, offsetX + playerX * tile, offsetY + playerY * tile, tile, tile, this);
    }

    private void slide(int dx, int dy) {
        boolean deslizandose = true;
        while (deslizandose) {
            int nextX = playerX + dx;
            int nextY = playerY + dy;
            int nextTile = map[nextY][nextX];

            if (nextTile == 1 || nextTile == 4) {
                deslizandose = false;
            } else {
                playerX = nextX;
                playerY = nextY;
                repaint();

                if (nextTile == 2) {
                    long endTime = System.currentTimeMillis();
                    int tiempo = (int) ((endTime - startTime) / 1000);

                    guardarResultadoEnBD(usuarioId, mapaNombre, archivoMapa, tiempo);
                    parent.mostrarResultado();
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

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> slide(0, -1);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> slide(0, 1);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> slide(-1, 0);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> slide(1, 0);
        }
    }
}
