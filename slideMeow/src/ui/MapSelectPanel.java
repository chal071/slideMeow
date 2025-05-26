package ui;

import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MapSelectPanel extends JPanel {
    private SlideMeowMain parent;
    private int usuarioId;

    public MapSelectPanel(SlideMeowMain parent, int usuarioId) {
        this.parent = parent;
        this.usuarioId = usuarioId;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, SlideMeowMain.gameHeight));
        setBackground(new Color(255, 249, 251));

        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("Fredoka One", Font.BOLD, 36));
        title.setForeground(new Color(130, 90, 160));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        add(title);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setFont(new Font("Poppins", Font.BOLD, 16));
        logoutButton.setBackground(new Color(210, 100, 104));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(200, 40));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        logoutButton.addActionListener(e -> {
            parent.usuarioActual = null;
            parent.usuarioId = -1;
            parent.mostrarLogin();
        });

        add(Box.createVerticalStrut(20));

        cargarMapasDesdeBD();

        add(Box.createVerticalGlue());

        add(Box.createVerticalStrut(30));
        add(logoutButton);
        add(Box.createVerticalGlue());

    }

    private void cargarMapasDesdeBD() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/slideMeow", "root", "mysql")) {
            PreparedStatement stmt = con.prepareStatement("SELECT nombre, archivo FROM mapas");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String archivo = rs.getString("archivo");
                add(crearBotonMapa(nombre, archivo));
                add(Box.createVerticalStrut(15));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Maps could not be loaded from the database\n" + e.getMessage());
        }
    }

    private JButton crearBotonMapa(String nombre, String ruta) {
        JButton btn = new JButton(nombre);
        btn.setFont(new Font("Poppins", Font.BOLD, 20));
        btn.setBackground(new Color(208, 194, 240));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 50));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addActionListener(e -> {
            parent.mostrarJuego(nombre, ruta, usuarioId);
        });
        return btn;
    }
}
