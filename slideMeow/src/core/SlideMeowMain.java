package core;

import ui.*;

import javax.swing.*;
import java.awt.*;

public class SlideMeowMain {
    public static int gameWidth = 800;
    public static int gameHeight = 600;
    public JFrame frame;
    public MenuPanel menuPanel;
    public JPanel juegoPanel;
    private LoginPanel loginPanel;
    public static String usuarioActual;
    public int usuarioId;
    public static int mapaId;



    public SlideMeowMain() {
        frame = new JFrame("Slide Meow!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        menuPanel = new MenuPanel(this);
        loginPanel = new LoginPanel(this);
        menuPanel.setPreferredSize(new Dimension(gameWidth, gameHeight));

        frame.setContentPane(menuPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Image icon = new ImageIcon("slideMeow/resource/logotip.png").getImage();
        frame.setIconImage(icon);
    }



    public void mostrarJuego(String nombreMapa ,String ruta, int usuarioId) {
        juegoPanel = new JPanel(new BorderLayout());

        GameInfoPanel infoPanel = new GameInfoPanel(nombreMapa);
        infoPanel.setPreferredSize(new Dimension(gameWidth, 40));

        GamePanel gamePanel = new GamePanel(this, usuarioId, nombreMapa, ruta);
        gamePanel.setPreferredSize(new Dimension(gameWidth, gameHeight - 40));

        juegoPanel.add(infoPanel, BorderLayout.NORTH);
        juegoPanel.add(gamePanel, BorderLayout.CENTER);

        frame.setContentPane(juegoPanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.cargarMapaDesdeArchivo(ruta);
        gamePanel.encontrarInicioDesdeMapa();
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        new SlideMeowMain();
    }


    public void mostrarLogin() {
        frame.setContentPane(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarResultado() {
        frame.setContentPane(new ResultPanel(this, mapaId));
        frame.revalidate();
        frame.repaint();
    }

    public void volverAlMenu() {
        frame.setContentPane(new MapSelectPanel(this, usuarioId));
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarSeleccionDeMapa() {
        frame.setContentPane(new MapSelectPanel(this, usuarioId));
        frame.revalidate();
    }





}