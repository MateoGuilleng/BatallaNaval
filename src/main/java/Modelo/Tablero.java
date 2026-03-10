package Modelo;

public class Tablero {
    private Casilla[][] casillas;

    public Tablero(int filas, int columnas) {
        casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(i, j, TipoCasilla.AGUA, false, null);
            }
        }
    }

    public Casilla getCasilla(int x, int y) {
        return casillas[x][y];
    }

    public void colocarBarco(Barco barco, int x, int y) {
        int tamaño = barco.getTamaño();
        boolean horizontal = barco.isHorizontal();

        for (int i = 0; i < tamaño; i++) {
            if (horizontal) {
                casillas[x][y + i].setTipo(TipoCasilla.BARCO);
                casillas[x][y + i].setBarco(barco);
            } else {
                casillas[x + i][y].setTipo(TipoCasilla.BARCO);
                casillas[x + i][y].setBarco(barco);
            }
        }
    }

    public void disparar(int x, int y) {
        Casilla casilla = getCasilla(x, y);
        if (casilla.getTipo() == TipoCasilla.BARCO) {
            casilla.setTipo(TipoCasilla.HUNDIDO);

            System.out.println("¡Tocado!");
        } else {
            System.out.println("Agua...");
        }

        casilla.setDisparada(true);

    }
}
