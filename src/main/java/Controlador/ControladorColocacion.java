package Controlador;

import Modelo.*;
import Vista.VentanaColocacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controla la pantalla de colocación de barcos antes de iniciar la partida.
 */
public class ControladorColocacion implements ActionListener {

    private final VentanaColocacion vista;
    private final Tablero tableroJugador;
    private final ControladorTablero cTablero;
    private final ControladorGeneral cGeneral;

    private Barco barcoSeleccionado = null;

    public ControladorColocacion(ControladorGeneral cGeneral, String nombreJugador, Tablero tableroJugador) {
        this.cGeneral       = cGeneral;
        this.tableroJugador = tableroJugador;
        this.cTablero       = new ControladorTablero();

        vista = new VentanaColocacion(nombreJugador);
        generarAleatorioAsync();

        vista.getBtnAleatorio().addActionListener(this);
        vista.getBtnListo().addActionListener(this);
        vista.getPanelTablero().setClickListener(this::manejarClicTablero);
    }

    private void generarAleatorioAsync() {
        vista.getBtnAleatorio().setEnabled(false);
        vista.getBtnListo().setEnabled(false);
        vista.setMensaje("Generando flota...");

        Thread hilo = new Thread(() -> {
            cTablero.colocarFlotaAleatoria(tableroJugador, vista.getPanelTablero());
            javax.swing.SwingUtilities.invokeLater(() -> {
                vista.getBtnAleatorio().setEnabled(true);
                vista.getBtnListo().setEnabled(true);
                vista.setMensaje("Haz clic en un barco para seleccionarlo, luego clic en el destino.");
            });
        });
        hilo.start();
    }

    private void manejarClicTablero(int fila, int col) {
        Casilla casilla = tableroJugador.getCasilla(fila, col);

        if (barcoSeleccionado == null) {
            if (casilla.getTipo() == TipoCasilla.BARCO) {
                barcoSeleccionado = casilla.getBarco();
                vista.setMensaje("Barco seleccionado. Haz clic en el destino para moverlo.");
            }
        } else {
            int tam   = barcoSeleccionado.getTamaño();
            boolean h = barcoSeleccionado.isHorizontal();
            if (cTablero.puedeColocarIgnorando(tableroJugador, tam, h, fila, col, barcoSeleccionado)) {
                cTablero.quitarBarco(tableroJugador, barcoSeleccionado);
                cTablero.colocarBarco(tableroJugador, barcoSeleccionado, fila, col);
                vista.getPanelTablero().resetear();
                cTablero.reflejarTableroPropio(tableroJugador, vista.getPanelTablero());
                vista.setMensaje("Barco movido. Selecciona otro o presiona ¡Listo!");
            } else {
                vista.setMensaje("No se puede colocar ahí. Elige otra posición.");
            }
            barcoSeleccionado = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAleatorio()) {
            barcoSeleccionado = null;
            generarAleatorioAsync();
        } else if (e.getSource() == vista.getBtnListo()) {
            vista.dispose();
            cGeneral.iniciarPartida();
        }
    }
}
