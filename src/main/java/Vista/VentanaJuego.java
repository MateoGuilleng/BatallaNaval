package Vista;

import Modelo.TipoMisil;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Ventana principal de la partida.
 * Muestra el tablero del jugador (izquierda) y el del robot (derecha).
 */
public class VentanaJuego extends JFrame {

    private PanelTablero panelJugador;
    private PanelTablero panelRobot;

    private JLabel lblTurno;
    private JLabel lblMisilesJugador;
    private JLabel lblEstado;
    private JLabel lblNotificacion;

    private JComboBox<TipoMisil> comboMisil;
    private JButton btnSalir;

    public VentanaJuego(String nombreJugador) {
        setTitle("Batalla Naval - " + nombreJugador);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(10, 30, 80));

        // ── Título ──────────────────────────────────────────────────────────
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(10, 40, 100));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel titulo = new JLabel("BATALLA NAVAL", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        lblTurno = new JLabel("Tu turno", JLabel.RIGHT);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 14));
        lblTurno.setForeground(new Color(100, 255, 100));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(lblTurno, BorderLayout.EAST);
        add(panelTitulo, BorderLayout.NORTH);

        // ── Tableros ─────────────────────────────────────────────────────────
        panelJugador = new PanelTablero();   // muestra barcos propios
        panelRobot   = new PanelTablero();   // oculta barcos enemigos

        JPanel panelTableros = new JPanel(new GridLayout(1, 2, 20, 0));
        panelTableros.setBackground(new Color(10, 30, 80));
        panelTableros.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JPanel wrapJugador = wrapTablero(panelJugador, "Tu tablero");
        JPanel wrapRobot   = wrapTablero(panelRobot,   "Tablero enemigo");

        panelTableros.add(wrapJugador);
        panelTableros.add(wrapRobot);
        add(panelTableros, BorderLayout.CENTER);

        // ── Panel inferior ────────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.setBackground(new Color(10, 40, 100));
        panelSur.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Estado
        lblEstado = new JLabel("Selecciona un misil y haz clic en el tablero enemigo.", JLabel.CENTER);
        lblEstado.setForeground(new Color(200, 230, 255));
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));

        lblNotificacion = new JLabel(" ", JLabel.CENTER);
        lblNotificacion.setForeground(new Color(200, 100, 255));
        lblNotificacion.setFont(new Font("Arial", Font.BOLD, 12));

        // Misiles + botón salir
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelControles.setBackground(new Color(10, 40, 100));

        JLabel lblMisilLabel = new JLabel("Misil:");
        lblMisilLabel.setForeground(Color.WHITE);
        lblMisilLabel.setFont(new Font("Arial", Font.BOLD, 13));

        comboMisil = new JComboBox<>(TipoMisil.values());
        comboMisil.setFont(new Font("Arial", Font.PLAIN, 13));
        comboMisil.setPreferredSize(new Dimension(130, 30));

        lblMisilesJugador = new JLabel("Misiles: B:10 C:1 Bo:1 N:1");
        lblMisilesJugador.setForeground(new Color(180, 220, 255));
        lblMisilesJugador.setFont(new Font("Arial", Font.PLAIN, 12));

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setBackground(new Color(180, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelControles.add(lblMisilLabel);
        panelControles.add(comboMisil);
        panelControles.add(lblMisilesJugador);
        panelControles.add(btnSalir);

        panelSur.add(lblEstado, BorderLayout.NORTH);
        panelSur.add(lblNotificacion, BorderLayout.CENTER);
        panelSur.add(panelControles, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel wrapTablero(PanelTablero panel, String etiqueta) {
        JPanel wrap = new JPanel(new BorderLayout(0, 5));
        wrap.setBackground(new Color(10, 30, 80));
        JLabel lbl = new JLabel(etiqueta, JLabel.CENTER);
        lbl.setForeground(new Color(180, 220, 255));
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        wrap.add(lbl, BorderLayout.NORTH);
        wrap.add(panel, BorderLayout.CENTER);
        return wrap;
    }

    // ── Métodos de actualización ──────────────────────────────────────────────

    public void setTurno(String texto, boolean esTurnoJugador) {
        lblTurno.setText(texto);
        lblTurno.setForeground(esTurnoJugador ? new Color(100, 255, 100) : new Color(255, 150, 50));
    }

    public void setEstado(String msg) {
        lblEstado.setText(msg);
    }

    public void setNotificacion(String msg) {
        lblNotificacion.setText(msg);
    }

    public void actualizarMisiles(Map<TipoMisil, Integer> misiles) {
        lblMisilesJugador.setText(String.format(
            "B:%d  Cruz:%d  Bomb:%d  Nuc:%d",
            misiles.getOrDefault(TipoMisil.BASICO, 0),
            misiles.getOrDefault(TipoMisil.CRUZ, 0),
            misiles.getOrDefault(TipoMisil.BOMBARDEO, 0),
            misiles.getOrDefault(TipoMisil.NUCLEAR, 0)
        ));
    }

    public void setTableroInteractivo(boolean activo) {
        panelRobot.setEnabled(activo);
        comboMisil.setEnabled(activo);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public PanelTablero getPanelJugador() { return panelJugador; }
    public PanelTablero getPanelRobot()   { return panelRobot; }
    public TipoMisil getMisilSeleccionado() { return (TipoMisil) comboMisil.getSelectedItem(); }
    public JButton getBtnSalir()          { return btnSalir; }
    public JLabel getLblEstado()          { return lblEstado; }
}
