package ui;
import core.SlideMeowMain;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private SlideMeowMain parent;

    public MenuPanel(SlideMeowMain parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(750, 800));
        setBackground(new Color(255, 249, 251));
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("🐱 Slide Meow!");
        title.setFont(new Font("Fredoka One", Font.BOLD, 36));
        title.setForeground(new Color(210, 98, 104));

        JButton startButton = new JButton("Comenzar");
        JButton exitButton = new JButton("Salir");

        startButton.setPreferredSize(new Dimension(200, 40));
        exitButton.setPreferredSize(new Dimension(200, 40));

        startButton.addActionListener(e -> parent.mostrarJuego());
        exitButton.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;

        add(title, gbc);
        gbc.gridy = 1; add(startButton, gbc);
        gbc.gridy = 2; add(exitButton, gbc);
    }

}
