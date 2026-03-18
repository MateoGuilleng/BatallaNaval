package Controlador;

import Modelo.*;
import Vista.VentanaNombre;
import Vista.PanelTablero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Punto de entrada y coordinador del flujo: nombre → colocación → partida.
 */
public class ControladorGeneral implements ActionListener {

    private VentanaNombre ventanaNombre;
    private String nombreJugador;

    private Tablero tableroJugador;
    private Tablero tableroRobot;
    private Jugador jugador;
    private Robot   robot;
    private Juego   juego;

    public ControladorGeneral() {
        ventanaNombre = new VentanaNombre();
        ventanaNombre.getBtnJugar().addActionListener(this);
        ventanaNombre.getCampoNombre().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = ventanaNombre.getNombreIngresado();
        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(ventanaNombre,
                "Por favor ingresa tu nombre.", "Nombre requerido",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        nombreJugador  = nombre;
        tableroJugador = new Tablero(10, 10);
        tableroRobot   = new Tablero(10, 10);
        jugador        = new Jugador(nombreJugador);
        robot          = new Robot("Robot");
        ventanaNombre.dispose();
        new ControladorColocacion(this, nombreJugador, tableroJugador);
    }

    /** Llamado por ControladorColocacion cuando el jugador presiona "¡Listo!". */
    public void iniciarPartida() {
        Thread hilo = new Thread(() -> {
            ControladorTablero ct = new ControladorTablero();
            ct.colocarFlotaAleatoria(tableroRobot, new PanelTablero());
            ct.generarMisilesAleatorios(tableroRobot);
            ct.generarMisilesAleatorios(tableroJugador);

            juego = new Juego(jugador, robot, tableroJugador, tableroRobot);

            ControladorTablero cTablero = new ControladorTablero();
            ControladorJugador cJugador = new ControladorJugador(juego, cTablero);
            ControladorRobot   cRobot   = new ControladorRobot(juego, cTablero);

            javax.swing.SwingUtilities.invokeLater(() ->
                new ControladorVista(this, juego, cJugador, cRobot, nombreJugador)
            );
        });
        hilo.setDaemon(true);
        hilo.start();
    }
}
