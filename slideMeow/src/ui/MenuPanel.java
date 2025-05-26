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

        ImageIcon icon = new ImageIcon("slideMeow/resource/logotip.png");
        Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaled));

        JLabel title = new JLabel("Slide Meow!");
        title.setFont(new Font("Fredoka One", Font.BOLD, 36));
        title.setForeground(new Color(210, 100, 104));

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        startButton.setPreferredSize(new Dimension(200, 40));
        startButton.setFont(new Font("Poppins", Font.BOLD ,16));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.setBackground(new Color(129, 101, 184));

        exitButton.setPreferredSize(new Dimension(200, 40));
        exitButton.setFont(new Font("Poppins",Font.BOLD, 16));
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.setBackground(new Color(129, 101, 184));

        startButton.addActionListener(e -> parent.mostrarLogin());
        exitButton.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridy = 0;
        add(logo, gbc);

        gbc.gridy = 1;
        add(title, gbc);

        gbc.gridy = 2;
        add(startButton, gbc);

        gbc.gridy = 3;
        add(exitButton, gbc);

    }

}
