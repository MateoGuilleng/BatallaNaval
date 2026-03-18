package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Casilla[][]     casillas;
    private CasillaMisil[][] casillasMisil;
    private List<Barco>     barcos;
    private final int filas;
    private final int columnas;

    public Tablero(int filas, int columnas) {
        this.filas    = filas;
        this.columnas = columnas;
        inicializar();
    }

    public void inicializar() {
        casillas      = new Casilla[filas][columnas];
        casillasMisil = new CasillaMisil[filas][columnas];
        barcos        = new ArrayList<>();
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                casillas[i][j] = new Casilla(i, j, TipoCasilla.AGUA, false, null);
    }

    public int getFilas()    { return filas; }
    public int getColumnas() { return columnas; }

    public Casilla getCasilla(int fila, int col)              { return casillas[fila][col]; }
    public CasillaMisil getCasillaMisil(int fila, int col)    { return casillasMisil[fila][col]; }
    public void setCasillaMisil(int fila, int col, CasillaMisil cm) { casillasMisil[fila][col] = cm; }
    public List<Barco> getBarcos()                            { return barcos; }
}
