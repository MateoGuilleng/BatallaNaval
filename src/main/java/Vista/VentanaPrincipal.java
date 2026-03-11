package Vista;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class VentanaPrincipal extends JFrame {
    private JTextArea areaTexto;
    private JButton btnCargar;
    private JButton btnIniciar;
    private JButton btnLimpiar;
    private JButton btnSalir;
    private JScrollPane scrollPane;
    private JPanel panelDatos;
    private JPanel panelGif;
    private JLabel gifLabel;
    private JLabel gifLabel2;

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Batalla Naval");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(140, 0, 0));
        panelTitulo.setPreferredSize(new Dimension(900, 70));

        JLabel titulo = new JLabel("Batalla Naval", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        panelTitulo.add(titulo, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnSalir = crearBoton("Salir", "SALIR", new Color(200, 0, 0));

        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, String comando, Color color) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        setPreferredSize(new Dimension(200, 45));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }
}
