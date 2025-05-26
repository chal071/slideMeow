package ui;

import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPanel extends JPanel {
    private SlideMeowMain parent;

    public LoginPanel(SlideMeowMain parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, SlideMeowMain.gameHeight));
        setBackground(new Color(255, 249, 251));

        Font labelFont = new Font("Poppins", Font.BOLD, 20);
        Font fieldFont = new Font("Poppins", Font.PLAIN, 16);
        Font buttonFont = new Font("Poppins", Font.BOLD, 16);

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Fredoka One", Font.BOLD, 36));
        title.setForeground(new Color(129, 99, 200));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        JTextField userField = new JTextField(15);
        userField.setFont(fieldFont);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        JPasswordField passField = new JPasswordField(15);
        passField.setFont(fieldFont);

        JButton loginBtn = new JButton("Log In");
        estilizarBoton(loginBtn, buttonFont);

        JButton registerBtn = new JButton("Register");
        estilizarBoton(registerBtn, buttonFont);

        loginBtn.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos");
                return;
            }

            int idUsuario = verificarUsuario(usuario, password);
            if (idUsuario != -1) {
                parent.usuarioActual = usuario;
                parent.usuarioId = idUsuario;
                parent.mostrarSeleccionDeMapa();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect username or password");
            }
        });

        registerBtn.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
                return;
            }

            if (registrarUsuario(usuario, password)) {
                JOptionPane.showMessageDialog(this, "User successfully registered!");
                int idUsuario = verificarUsuario(usuario, password);
                parent.usuarioActual = usuario;
                parent.usuarioId = idUsuario;
                parent.mostrarSeleccionDeMapa();
            } else {
                JOptionPane.showMessageDialog(this, "That user already exists");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginBtn, gbc);

        gbc.gridy = 4;
        add(registerBtn, gbc);
    }

    private void estilizarBoton(JButton btn, Font font) {
        btn.setBackground(new Color(129, 99, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    }

    private int verificarUsuario(String usuario, String password) {
        String db_url = "jdbc:mysql://localhost:3306/slideMeow";
        String db_user = "root";
        String db_pass = "mysql";
        try {
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            String sql = "SELECT id FROM usuarios WHERE nombre = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean registrarUsuario(String usuario, String password) {
        String db_url = "jdbc:mysql://localhost:3306/slideMeow";
        String db_user = "root";
        String db_pass = "mysql";
        try {
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            String sql = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
