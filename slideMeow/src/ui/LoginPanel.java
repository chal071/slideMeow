package ui;

import core.SlideMeowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPanel extends JPanel {
    private SlideMeowMain parent;

    public LoginPanel(SlideMeowMain parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(SlideMeowMain.gameWidth, SlideMeowMain.gameHeight));

        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Contraseña:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Entrar");
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
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });

        JButton registerBtn = new JButton("Registrarse");
        registerBtn.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos");
                return;
            }

            if (registrarUsuario(usuario, password)) {
                JOptionPane.showMessageDialog(this, "¡Usuario registrado con éxito!");
                int idUsuario = verificarUsuario(usuario, password);
                parent.usuarioActual = usuario;
                parent.usuarioId = idUsuario;
                parent.mostrarSeleccionDeMapa();
            } else {
                JOptionPane.showMessageDialog(this, "Ese usuario ya existe");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginBtn, gbc);

        gbc.gridy = 3;
        add(registerBtn, gbc);
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
            return false; // usuario ya existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
