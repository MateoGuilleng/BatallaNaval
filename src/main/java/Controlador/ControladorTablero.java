package Controlador;

import Modelo.*;
import Vista.PanelTablero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Toda la lógica de tablero: colocación, disparos, validaciones y sincronización con la vista.
 */
public class ControladorTablero {

    public static final int[] TAMANOS_FLOTA = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private static final TipoMisil[] TIPOS_MISIL_MAPA = {
        TipoMisil.CRUZ, TipoMisil.BOMBARDEO, TipoMisil.NUCLEAR
    };

    // ── Colocación ────────────────────────────────────────────────────────────

    public boolean puedeColocar(Tablero t, int tam, boolean horiz, int fila, int col) {
        for (int i = 0; i < tam; i++) {
            int f = horiz ? fila    : fila + i;
            int c = horiz ? col + i : col;
            if (f < 0 || f >= t.getFilas() || c < 0 || c >= t.getColumnas()) return false;
            if (t.getCasilla(f, c).getTipo() != TipoCasilla.AGUA)             return false;
        }
        return true;
    }

    public boolean puedeColocarIgnorando(Tablero t, int tam, boolean horiz, int fila, int col, Barco ignorar) {
        for (int i = 0; i < tam; i++) {
            int f = horiz ? fila    : fila + i;
            int c = horiz ? col + i : col;
            if (f < 0 || f >= t.getFilas() || c < 0 || c >= t.getColumnas()) return false;
            Casilla cas = t.getCasilla(f, c);
            if (cas.getTipo() != TipoCasilla.AGUA && cas.getBarco() != ignorar) return false;
        }
        return true;
    }

    public void colocarBarco(Tablero t, Barco barco, int fila, int col) {
        if (!t.getBarcos().contains(barco)) t.getBarcos().add(barco);
        for (int i = 0; i < barco.getTamaño(); i++) {
            int f = barco.isHorizontal() ? fila    : fila + i;
            int c = barco.isHorizontal() ? col + i : col;
            t.getCasilla(f, c).setTipo(TipoCasilla.BARCO);
            t.getCasilla(f, c).setBarco(barco);
        }
    }

    public void quitarBarco(Tablero t, Barco barco) {
        for (int i = 0; i < t.getFilas(); i++)
            for (int j = 0; j < t.getColumnas(); j++)
                if (t.getCasilla(i, j).getBarco() == barco) {
                    t.getCasilla(i, j).setTipo(TipoCasilla.AGUA);
                    t.getCasilla(i, j).setBarco(null);
                }
    }

    public void colocarFlotaAleatoria(Tablero t, PanelTablero panel) {
        t.inicializar();
        panel.resetear();
        for (int tam : TAMANOS_FLOTA) {
            boolean colocado = false;
            while (!colocado) {
                boolean horiz = ThreadLocalRandom.current().nextBoolean();
                int fila = ThreadLocalRandom.current().nextInt(0, t.getFilas());
                int col  = ThreadLocalRandom.current().nextInt(0, t.getColumnas());
                if (puedeColocar(t, tam, horiz, fila, col)) {
                    colocarBarco(t, new Barco(tam, horiz), fila, col);
                    colocado = true;
                }
            }
        }
        reflejarTableroPropio(t, panel);
    }

    public void generarMisilesAleatorios(Tablero t) {
        for (int k = 0; k < 5; k++) {
            int f, c;
            do {
                f = ThreadLocalRandom.current().nextInt(0, t.getFilas());
                c = ThreadLocalRandom.current().nextInt(0, t.getColumnas());
            } while (t.getCasilla(f, c).getTipo() != TipoCasilla.AGUA || t.getCasillaMisil(f, c) != null);
            TipoMisil tipo = TIPOS_MISIL_MAPA[ThreadLocalRandom.current().nextInt(TIPOS_MISIL_MAPA.length)];
            t.setCasillaMisil(f, c, new CasillaMisil(f, c, tipo, false));
        }
    }

    // ── Disparos ──────────────────────────────────────────────────────────────

    /**
     * Dispara en una casilla. Retorna el TipoCasilla visual a mostrar:
     * TOCADO (barco parcial), HUNDIDO (barco completo), BOMBA, AGUA_DISP, o AGUA si fuera de rango.
     * Retorna null si la casilla ya fue disparada (no hacer nada en la vista).
     */
    public TipoCasilla disparar(Tablero t, int fila, int col) {
        if (fila < 0 || fila >= t.getFilas() || col < 0 || col >= t.getColumnas())
            return null;

        Casilla casilla = t.getCasilla(fila, col);
        if (casilla.isDisparada()) return null; // ya disparada, ignorar

        casilla.setDisparada(true);

        if (casilla.getTipo() == TipoCasilla.BARCO) {
            Barco barco = casilla.getBarco();
            barco.setCasillasHundidas(barco.getCasillasHundidas() + 1);
            casilla.setTipo(TipoCasilla.TOCADO);

            if (barco.getCasillasHundidas() >= barco.getTamaño()) {
                marcarBarcoHundido(t, barco);
                return TipoCasilla.HUNDIDO;
            }
            return TipoCasilla.TOCADO;
        }

        CasillaMisil cm = t.getCasillaMisil(fila, col);
        if (cm != null && !cm.isEncontrada()) return TipoCasilla.BOMBA;

        return TipoCasilla.AGUA_DISP;
    }

    /** Marca todas las casillas del barco como HUNDIDO en el modelo. */
    private void marcarBarcoHundido(Tablero t, Barco barco) {
        for (int i = 0; i < t.getFilas(); i++)
            for (int j = 0; j < t.getColumnas(); j++) {
                Casilla c = t.getCasilla(i, j);
                if (c.getBarco() == barco) c.setTipo(TipoCasilla.HUNDIDO);
            }
    }

    /** Dispara en área y retorna lista de (fila, col, resultado). Solo incluye casillas válidas y no repetidas. */
    public List<int[]> dispararArea(Tablero t, int fila, int col, TipoMisil tipo) {
        List<int[]> resultados = new ArrayList<>();

        if (tipo == TipoMisil.CRUZ) {
            dispararYAgregar(t, fila,     col,     resultados);
            dispararYAgregar(t, fila - 1, col,     resultados);
            dispararYAgregar(t, fila + 1, col,     resultados);
            dispararYAgregar(t, fila,     col - 1, resultados);
            dispararYAgregar(t, fila,     col + 1, resultados);
        } else if (tipo == TipoMisil.BOMBARDEO) {
            Set<Integer> usadas = new HashSet<>();
            while (usadas.size() < 4) {
                int f = ThreadLocalRandom.current().nextInt(0, t.getFilas());
                int c = ThreadLocalRandom.current().nextInt(0, t.getColumnas());
                if (usadas.add(f * t.getColumnas() + c))
                    dispararYAgregar(t, f, c, resultados);
            }
        } else if (tipo == TipoMisil.NUCLEAR) {
            for (int i = fila - 2; i <= fila + 2; i++)
                for (int j = col - 2; j <= col + 2; j++)
                    dispararYAgregar(t, i, j, resultados);
        }
        return resultados;
    }

    private void dispararYAgregar(Tablero t, int f, int c, List<int[]> lista) {
        TipoCasilla res = disparar(t, f, c);
        if (res != null) lista.add(new int[]{f, c, res.ordinal()});
    }

    // ── Estado ────────────────────────────────────────────────────────────────

    public boolean todosBarcosHundidos(Tablero t) {
        if (t.getBarcos().isEmpty()) return false;
        for (Barco b : t.getBarcos())
            if (b.getCasillasHundidas() < b.getTamaño()) return false;
        return true;
    }

    // ── Sincronización con la vista ───────────────────────────────────────────

    /** Pinta los barcos del tablero propio (solo BARCO). */
    public void reflejarTableroPropio(Tablero t, PanelTablero panel) {
        for (int i = 0; i < t.getFilas(); i++)
            for (int j = 0; j < t.getColumnas(); j++)
                if (t.getCasilla(i, j).getTipo() == TipoCasilla.BARCO)
                    panel.setCasilla(i, j, TipoCasilla.BARCO);
    }

    /**
     * Cuando un barco queda hundido, actualiza TODAS sus casillas en el panel a HUNDIDO.
     * Llamar solo cuando disparar() retornó HUNDIDO.
     */
    public void reflejarBarcoHundido(Tablero t, int filaImpacto, int colImpacto, PanelTablero panel) {
        Barco barco = t.getCasilla(filaImpacto, colImpacto).getBarco();
        if (barco == null) return;
        for (int i = 0; i < t.getFilas(); i++)
            for (int j = 0; j < t.getColumnas(); j++)
                if (t.getCasilla(i, j).getBarco() == barco)
                    panel.setCasilla(i, j, TipoCasilla.HUNDIDO);
    }

    /** Procesa si el disparo encontró una bomba. Retorna el TipoMisil obtenido, o null. */
    public TipoMisil procesarBomba(Tablero t, int fila, int col, java.util.Map<TipoMisil, Integer> inventario) {
        CasillaMisil cm = t.getCasillaMisil(fila, col);
        if (cm == null || cm.isEncontrada()) return null;
        cm.setEncontrada(true);
        inventario.put(cm.getTipoMisil(), inventario.getOrDefault(cm.getTipoMisil(), 0) + 1);
        return cm.getTipoMisil();
    }

    /** Muestra "?" en una casilla aleatoria con misil oculto no descubierto. */
    public void mostrarPistaAleatoria(Tablero t, PanelTablero panel) {
        for (int intentos = 0; intentos < 200; intentos++) {
            int f = ThreadLocalRandom.current().nextInt(0, t.getFilas());
            int c = ThreadLocalRandom.current().nextInt(0, t.getColumnas());
            CasillaMisil cm = t.getCasillaMisil(f, c);
            if (cm != null && !cm.isEncontrada() && !t.getCasilla(f, c).isDisparada()) {
                panel.mostrarPista(f, c);
                return;
            }
        }
    }
}
