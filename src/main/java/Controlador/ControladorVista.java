package Controlador;

import Modelo.*;
import Vista.VentanaJuego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controla la ventana de juego y coordina los turnos.
 * - Impacto en barco → jugador sigue disparando.
 * - Impacto en agua → turno pasa al robot.
 */
public class ControladorVista implements ActionListener {

    private final VentanaJuego vista;
    private final ControladorGeneral cGeneral;
    private final ControladorJugador cJugador;
    private final ControladorRobot   cRobot;
    private final ControladorTablero cTablero;
    private final Juego juego;

    public ControladorVista(ControladorGeneral cGeneral, Juego juego,
                            ControladorJugador cJugador, ControladorRobot cRobot,
                            String nombreJugador) {
        this.cGeneral  = cGeneral;
        this.juego     = juego;
        this.cJugador  = cJugador;
        this.cRobot    = cRobot;
        this.cTablero  = new ControladorTablero();

        vista = new VentanaJuego(nombreJugador);
        cTablero.reflejarTableroPropio(juego.getTableroJugador(), vista.getPanelJugador());

        vista.getPanelRobot().setClickListener(this::manejarDisparoJugador);
        vista.getBtnSalir().addActionListener(this);
    }

    private void manejarDisparoJugador(int fila, int col) {
        if (!juego.isTurnoJugador()) return;

        TipoMisil misil = vista.getMisilSeleccionado();
        if (juego.getJugador().getMisil(misil) <= 0) {
            vista.setEstado("No tienes misiles de tipo " + misil + ".");
            return;
        }

        ControladorJugador.ResultadoTurno resultado = cJugador.ejecutarDisparo(
            fila, col, misil, vista.getPanelRobot(), vista.getPanelJugador()
        );

        switch (resultado) {
            case INVALIDO -> {
                vista.setEstado("Casilla ya disparada.");
            }
            case IMPACTO -> {
                vista.actualizarMisiles(juego.getJugador().getMisiles());
                notificarMisilJugador();
                if (cTablero.todosBarcosHundidos(juego.getTableroRobot())) { finDeJuego(); return; }
                vista.setEstado("¡Impacto! Sigue disparando.");
            }
            case AGUA -> {
                vista.actualizarMisiles(juego.getJugador().getMisiles());
                notificarMisilJugador();
                if (cTablero.todosBarcosHundidos(juego.getTableroRobot())) { finDeJuego(); return; }
                pasarTurnoAlRobot();
            }
        }
    }

    private void notificarMisilJugador() {
        TipoMisil obtenido = cJugador.getUltimoMisilObtenido();
        if (obtenido != null)
            vista.setNotificacion("¡Encontraste un misil! Recibiste: " + obtenido);
        else
            vista.setNotificacion(" ");
    }

    private void pasarTurnoAlRobot() {
        juego.setTurnoJugador(false);
        vista.setTurno("Turno del robot...", false);
        vista.setTableroInteractivo(false);

        cRobot.ejecutarTurnoAsync(
            vista.getPanelJugador(),
            () -> {
                if (cTablero.todosBarcosHundidos(juego.getTableroJugador())) { finDeJuego(); return; }
                juego.setTurnoJugador(true);
                vista.setTurno("Tu turno", true);
                vista.setTableroInteractivo(true);
                vista.setEstado("Selecciona un misil y haz clic en el tablero enemigo.");
            },
            misilRobot -> {
                if (misilRobot != null)
                    vista.setNotificacion("El robot recogió un misil: " + misilRobot);
                else
                    vista.setNotificacion(" ");
            }
        );
    }

    private void finDeJuego() {
        vista.setTableroInteractivo(false);
        boolean gano = cTablero.todosBarcosHundidos(juego.getTableroRobot());
        String msg = gano
            ? "¡Felicidades " + juego.getJugador().getNombre() + ", ganaste!"
            : "El robot ganó. ¡Mejor suerte la próxima vez!";
        vista.setEstado(msg);
        vista.setTurno("Fin del juego", false);
        javax.swing.JOptionPane.showMessageDialog(vista, msg, "Fin del juego",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnSalir()) System.exit(0);
    }
}
