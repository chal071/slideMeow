package ui;

import core.SlideMeowMain;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.*;

public class ResultPanel extends JPanel {
    private SlideMeowMain parent;
    private JTextPane area;
    private int mapaId;

    public ResultPanel(SlideMeowMain parent, int mapaId) {
        this.parent = parent;
        this.mapaId = mapaId;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, SlideMeowMain.gameHeight));
        setBackground(new Color(255, 249, 251));

        JLabel title = new JLabel("Results", SwingConstants.CENTER);
        title.setFont(new Font("Fredoka One", Font.BOLD, 40));
        title.setForeground(new Color(130, 90, 160));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        area = new JTextPane();
        area.setEditable(false);
        area.setFont(new Font("Poppins", Font.PLAIN, 18));
        area.setBackground(new Color(255, 249, 251));
        area.setForeground(new Color(80, 60, 100));
        area.setBorder(null);
        area.setOpaque(false);
        area.setAlignmentX(Component.CENTER_ALIGNMENT);

        StyledDocument doc = area.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        cargarResultado();
        centerPanel.add(area);

        add(centerPanel, BorderLayout.CENTER);

        JButton volverBtn = new JButton("Return to the menu");
        volverBtn.setFont(new Font("Poppins", Font.BOLD, 18));
        volverBtn.setBackground(new Color(208, 194, 240));
        volverBtn.setForeground(Color.WHITE);
        volverBtn.setFocusPainted(false);
        volverBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        volverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        volverBtn.addActionListener(e -> parent.volverAlMenu());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(volverBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void cargarResultado() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPlayer: ").append(SlideMeowMain.usuarioActual).append("\n\n");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/slideMeow", "root", "mysql")) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT r.tiempo, m.nombre AS mapa_nombre FROM ranking r JOIN mapas m ON r.mapa_id = m.id WHERE r.usuario_id = ? AND r.mapa_id = ? ORDER BY r.id DESC LIMIT 1");
            stmt.setInt(1, parent.usuarioId);
            stmt.setInt(2, mapaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sb.append("Time: ").append(rs.getInt("tiempo")).append(" seconds\n");
                sb.append("Map: ").append(rs.getString("mapa_nombre")).append("\n\n");
            }

            sb.append("Top 10 of this map:\n\n");
            stmt = con.prepareStatement("SELECT u.nombre, r.tiempo FROM ranking r JOIN usuarios u ON r.usuario_id = u.id WHERE r.mapa_id = ? ORDER BY r.tiempo ASC LIMIT 10");
            stmt.setInt(1, mapaId);
            rs = stmt.executeQuery();
            int pos = 1;
            while (rs.next()) {
                sb.append(pos++).append(". ").append(rs.getString("nombre"))
                        .append(" - ").append(rs.getInt("tiempo")).append("s\n");
            }

        } catch (Exception e) {
            sb.append("\nError loading results");
        }

        area.setText(sb.toString());
    }
}
