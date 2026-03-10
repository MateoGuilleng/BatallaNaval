package Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class Tablero {
    private Casilla[][] casillas;
    private CasillaMisil[][] casillasMisil;
    private Barco[] barcos;

    public Tablero(int filas, int columnas) {
        casillas = new Casilla[filas][columnas];
        casillasMisil = new CasillaMisil[filas][columnas];
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

    public void generarMisilesAleatorios() {
        int cantidadMisiles = 5;
        for (int i = 0; i < cantidadMisiles; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, casillas.length);
            int y = ThreadLocalRandom.current().nextInt(0, casillas[0].length);

            TipoMisil[] tipoMisiles = TipoMisil.values();

            int indiceAleatorio = ThreadLocalRandom.current().nextInt(0, tipoMisiles.length);

            TipoMisil tipoMisilACrear = tipoMisiles[indiceAleatorio];

            casillasMisil[x][y] = new CasillaMisil(x, y, tipoMisilACrear, false);
        }
    }

    public ResultadoDisparo dispararSimple(int x, int y) {
        Casilla casilla = getCasilla(x, y);
        TipoCasilla tipoCasilla = casilla.getTipo();
        if (!casilla.isDisparada()) {
            if (casilla.getTipo() == TipoCasilla.BARCO) {

                casilla.setTipo(TipoCasilla.HUNDIDO);
                casilla.getBarco().registrarImpacto();

                tipoCasilla = TipoCasilla.BARCO;

            } else if (casilla.getTipo() == TipoCasilla.BOMBA) {
                System.out.println("¡Misil encontrado!");

                tipoCasilla = TipoCasilla.BOMBA;

            } else {
                System.out.println("Agua...");

                tipoCasilla = TipoCasilla.AGUA;
            }

            casilla.setDisparada(true);

        } else {
            System.out.println("¡Ya has disparado a esta casilla!");
            tipoCasilla = casilla.getTipo();
        }

        return new ResultadoDisparo(x, y, tipoCasilla);

    }

    public ResultadoAtaque dispararCruz(int x, int y) {

        ResultadoAtaque resultado = new ResultadoAtaque();


        resultado.agregarResultado(dispararSimple(x, y));
        resultado.agregarResultado(dispararSimple(x - 1, y));
        resultado.agregarResultado(dispararSimple(x + 1, y));
        resultado.agregarResultado(dispararSimple(x, y - 1));
        resultado.agregarResultado(dispararSimple(x, y + 1));
        return resultado;
    }

    public ResultadoAtaque dispararBombardeo(int x, int y) {
        ResultadoAtaque resultado = new ResultadoAtaque();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                resultado.agregarResultado(dispararSimple(i, j));
            }
        }
        return resultado;
    }

    public ResultadoAtaque dispararNuclear(int x, int y) {
        ResultadoAtaque resultado = new ResultadoAtaque();
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                resultado.agregarResultado(dispararSimple(i, j));
            }
        }
        return resultado;
    }

    public boolean todosBarcosHundidos() {
        for (Barco barco : barcos) {
            if (!barco.isHundido()) {
                return false;
            }
        }
        return true;
    }
}
