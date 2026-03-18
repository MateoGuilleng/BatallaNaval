package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaNombre extends JFrame {

    private JTextField campoNombre;
    private JButton btnJugar;

    public VentanaNombre() {
        setTitle("Batalla Naval - Ingresa tu nombre");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(10, 40, 100));
        JLabel titulo = new JLabel("BATALLA NAVAL", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(20, 60, 140));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel lblNombre = new JLabel("Ingresa tu nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentro.add(lblNombre, gbc);

        campoNombre = new JTextField(15);
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        panelCentro.add(campoNombre, gbc);

        btnJugar = new JButton("¡Jugar!");
        btnJugar.setFont(new Font("Arial", Font.BOLD, 14));
        btnJugar.setBackground(new Color(0, 150, 50));
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFocusPainted(false);
        btnJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentro.add(btnJugar, gbc);

        add(panelCentro, BorderLayout.CENTER);
        setVisible(true);
    }

    public String getNombreIngresado() {
        return campoNombre.getText().trim();
    }

    public JButton getBtnJugar() {
        return btnJugar;
    }

    public JTextField getCampoNombre() {
        return campoNombre;
    }
}
