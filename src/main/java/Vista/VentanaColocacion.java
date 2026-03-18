package Vista;

import Modelo.TipoCasilla;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana donde el jugador organiza sus barcos antes de iniciar la partida.
 * Muestra el tablero generado aleatoriamente y permite reordenar barcos.
 */
public class VentanaColocacion extends JFrame {

    private PanelTablero panelTablero;
    private JButton btnAleatorio;
    private JButton btnListo;
    private JLabel lblInfo;
    private JLabel lblNombre;

    public VentanaColocacion(String nombreJugador) {
        setTitle("Batalla Naval - Coloca tus barcos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Panel título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(10, 40, 100));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblNombre = new JLabel("Capitán: " + nombreJugador, JLabel.LEFT);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setForeground(new Color(180, 220, 255));

        JLabel titulo = new JLabel("COLOCA TUS BARCOS", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        panelTitulo.add(lblNombre, BorderLayout.WEST);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel tablero
        panelTablero = new PanelTablero();
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelCentro.setBackground(new Color(20, 60, 140));
        panelCentro.add(panelTablero);
        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.setBackground(new Color(10, 40, 100));
        panelSur.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        lblInfo = new JLabel("Haz clic en una casilla para seleccionar un barco, luego haz clic en el destino.", JLabel.CENTER);
        lblInfo.setForeground(new Color(200, 230, 255));
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelBotones.setBackground(new Color(10, 40, 100));

        btnAleatorio = crearBoton("Aleatorio", new Color(0, 100, 180));
        btnListo = crearBoton("¡Listo!", new Color(0, 150, 50));

        panelBotones.add(btnAleatorio);
        panelBotones.add(btnListo);

        panelSur.add(lblInfo, BorderLayout.NORTH);
        panelSur.add(panelBotones, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 38));
        return btn;
    }

    public void setMensaje(String msg) {
        lblInfo.setText(msg);
    }

    public PanelTablero getPanelTablero() { return panelTablero; }
    public JButton getBtnAleatorio()      { return btnAleatorio; }
    public JButton getBtnListo()          { return btnListo; }
}
