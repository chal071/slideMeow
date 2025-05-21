package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInfoPanel extends JPanel {
    private String nivel = "Basic";
    private int segundos = 0;

    public GameInfoPanel() {
        setPreferredSize(new Dimension(750, 40));
        setBackground(new Color(245, 249, 251));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos++;
                repaint();
            }
        });
        timer.start();
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Poppins", Font.BOLD, 16));
        g.drawString("Nivel: " + nivel, 20, 25);
        g.drawString("Tiempo: " + segundos + "s", 180, 25);
        g.drawString("🐱 ¡Desliza hasta la meta!", 360, 25);
    }
}
