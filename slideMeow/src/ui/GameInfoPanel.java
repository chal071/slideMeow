package ui;
import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInfoPanel extends JPanel {
    private String nivel;
    private int segundos;

    public GameInfoPanel(String nivel) {
        this.nivel = nivel;
        this.segundos = 0;
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, 40));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Poppins", Font.BOLD, 16));
        g.drawString("Level: " + nivel, 20, 25);
        g.drawString("Times: " + segundos + "s", 180, 25);
        g.drawString("Slide to the exit!", 360, 25);
    }
}
