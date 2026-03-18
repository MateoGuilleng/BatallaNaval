package Controlador;

import Modelo.*;
import Vista.PanelTablero;

import java.util.List;

/**
 * Lógica del turno del jugador.
 * - Impacto (TOCADO/HUNDIDO) → sigue disparando.
 * - Agua → turno pasa al robot.
 */
public class ControladorJugador {

    private final Juego juego;
    private final ControladorTablero cTablero;

    private int turnosCompletos = 0;
    private TipoMisil ultimoMisilObtenido = null;

    public ControladorJugador(Juego juego, ControladorTablero cTablero) {
        this.juego    = juego;
        this.cTablero = cTablero;
    }

    public enum ResultadoTurno { IMPACTO, AGUA, INVALIDO }

    public ResultadoTurno ejecutarDisparo(int fila, int col, TipoMisil tipo,
                                          PanelTablero panelRobot, PanelTablero panelJugador) {
        Jugador jugador = juego.getJugador();
        if (jugador.getMisil(tipo) <= 0) return ResultadoTurno.INVALIDO;

        Tablero tableroRobot = juego.getTableroRobot();
        ultimoMisilObtenido = null;
        boolean impacto = false;

        if (tipo == TipoMisil.BASICO) {
            if (tableroRobot.getCasilla(fila, col).isDisparada()) return ResultadoTurno.INVALIDO;
            TipoCasilla res = cTablero.disparar(tableroRobot, fila, col);
            aplicarResultado(tableroRobot, fila, col, res, panelRobot);
            if (res == TipoCasilla.TOCADO || res == TipoCasilla.HUNDIDO) impacto = true;
        } else {
            List<int[]> resultados = cTablero.dispararArea(tableroRobot, fila, col, tipo);
            for (int[] r : resultados) {
                TipoCasilla res = TipoCasilla.values()[r[2]];
                aplicarResultado(tableroRobot, r[0], r[1], res, panelRobot);
                if (res == TipoCasilla.TOCADO || res == TipoCasilla.HUNDIDO) impacto = true;
            }
        }

        jugador.setMisil(tipo, jugador.getMisil(tipo) - 1);

        if (impacto) return ResultadoTurno.IMPACTO;

        // Agua: turno completo
        turnosCompletos++;
        if (turnosCompletos % 2 == 0) {
            cTablero.mostrarPistaAleatoria(tableroRobot, panelRobot);
            cTablero.mostrarPistaAleatoria(juego.getTableroJugador(), panelJugador);
        }
        return ResultadoTurno.AGUA;
    }

    private void aplicarResultado(Tablero t, int f, int c, TipoCasilla res, PanelTablero panel) {
        if (res == null) return;
        if (res == TipoCasilla.HUNDIDO) {
            cTablero.reflejarBarcoHundido(t, f, c, panel);
        } else if (res == TipoCasilla.BOMBA) {
            panel.setCasilla(f, c, TipoCasilla.BOMBA);
            TipoMisil obtenido = cTablero.procesarBomba(t, f, c, juego.getJugador().getMisiles());
            if (obtenido != null) ultimoMisilObtenido = obtenido;
        } else {
            panel.setCasilla(f, c, res);
        }
    }

    public TipoMisil getUltimoMisilObtenido() { return ultimoMisilObtenido; }
}
