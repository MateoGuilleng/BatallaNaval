package Controlador;

import Modelo.*;
import Vista.PanelTablero;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * Lógica del turno del robot, ejecutada en hilo separado para no bloquear el EDT.
 */
public class ControladorRobot {

    private final Juego juego;
    private final ControladorTablero cTablero;

    public ControladorRobot(Juego juego, ControladorTablero cTablero) {
        this.juego    = juego;
        this.cTablero = cTablero;
    }

    public void ejecutarTurnoAsync(PanelTablero panelJugador, Runnable onFinish,
                                   Consumer<TipoMisil> onMisilObtenido) {
        Thread hilo = new Thread(() -> {
            try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            Robot robot = juego.getRobot();
            Tablero tableroJugador = juego.getTableroJugador();
            TipoMisil[] tipos = TipoMisil.values();

            TipoMisil tipo;
            do { tipo = tipos[ThreadLocalRandom.current().nextInt(tipos.length)]; }
            while (robot.getMisil(tipo) <= 0);

            int fila = ThreadLocalRandom.current().nextInt(0, tableroJugador.getFilas());
            int col  = ThreadLocalRandom.current().nextInt(0, tableroJugador.getColumnas());

            final TipoMisil tipoFinal = tipo;
            final TipoMisil[] misilObtenido = {null};

            // Calcular resultados en el hilo (modelo), pintar en EDT
            if (tipo == TipoMisil.BASICO) {
                TipoCasilla res = cTablero.disparar(tableroJugador, fila, col);
                final int f = fila, c = col;
                javax.swing.SwingUtilities.invokeLater(() ->
                    aplicarResultado(tableroJugador, f, c, res, panelJugador, robot, misilObtenido)
                );
            } else {
                List<int[]> resultados = cTablero.dispararArea(tableroJugador, fila, col, tipo);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    for (int[] r : resultados) {
                        TipoCasilla res = TipoCasilla.values()[r[2]];
                        aplicarResultado(tableroJugador, r[0], r[1], res, panelJugador, robot, misilObtenido);
                    }
                });
            }

            robot.setMisil(tipoFinal, robot.getMisil(tipoFinal) - 1);

            javax.swing.SwingUtilities.invokeLater(() -> {
                onMisilObtenido.accept(misilObtenido[0]);
                onFinish.run();
            });
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    private void aplicarResultado(Tablero t, int f, int c, TipoCasilla res,
                                   PanelTablero panel, Robot robot, TipoMisil[] misilObtenido) {
        if (res == null) return;
        if (res == TipoCasilla.HUNDIDO) {
            cTablero.reflejarBarcoHundido(t, f, c, panel);
        } else if (res == TipoCasilla.BOMBA) {
            panel.setCasilla(f, c, TipoCasilla.BOMBA);
            TipoMisil obtenido = cTablero.procesarBomba(t, f, c, robot.getMisiles());
            if (obtenido != null) misilObtenido[0] = obtenido;
        } else {
            panel.setCasilla(f, c, res);
        }
    }
}
