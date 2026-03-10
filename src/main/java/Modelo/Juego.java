package Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class Juego {
    private Jugador jugador;
    private Robot robot;
    private Tablero tableroJuador;
    private Tablero tableroRobot;
    private boolean turnoJugador;

    public Juego(Jugador jugador, Robot robot) {
        this.jugador = jugador;
        this.robot = robot;
        this.tableroJuador = new Tablero(10, 10);
        this.tableroRobot = new Tablero(10, 10);
        this.turnoJugador = true;
    }

    public void initializeGame() {
        // DATOS DE EJEMPLO POR EL MOMENTO
        Barco barco1 = new Barco(3, true);
        Barco barco2 = new Barco(2, false);

        Barco barco3 = new Barco(3, false);
        Barco barco4 = new Barco(2, false);

        tableroJuador.colocarBarco(barco1, 0, 0);
        tableroJuador.colocarBarco(barco2, 5, 5);
        tableroRobot.colocarBarco(barco3, 0, 0);
        tableroRobot.colocarBarco(barco4, 5, 5);
    }

    public boolean isJuegoTerminado() {
        if (tableroJuador.todosBarcosHundidos()) {
            return true;
        } else if (tableroRobot.todosBarcosHundidos()) {
            return true;
        }
        return false;
    }

    public void disparoJugador(int x, int y, TipoMisil tipoMisil) {

        if (tipoMisil == TipoMisil.BASICO) {
            ResultadoDisparo resultadoDisparo = tableroRobot.dispararSimple(x, y);
            accionPorCasilla(resultadoDisparo.getTipoCasilla());
            jugador.restarMisil(tipoMisil);
        } else if (tipoMisil == TipoMisil.CRUZ) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararCruz(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }
            jugador.restarMisil(tipoMisil);
        } else if (tipoMisil == TipoMisil.BOMBARDEO) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararBombardeo(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }
            jugador.restarMisil(tipoMisil);
        } else if (tipoMisil == TipoMisil.NUCLEAR) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararNuclear(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }
            jugador.restarMisil(tipoMisil);
        }

    }

    public void disparoRobot() {
        int x = ThreadLocalRandom.current().nextInt(0, 10);
        int y = ThreadLocalRandom.current().nextInt(0, 10);

        TipoMisil[] tipoMisiles = TipoMisil.values();

        int indiceAleatorio = ThreadLocalRandom.current().nextInt(0, tipoMisiles.length);

        TipoMisil tipoMisilADisparar = tipoMisiles[indiceAleatorio];

        if (tipoMisilADisparar == TipoMisil.BASICO) {
            ResultadoDisparo resultadoDisparo = tableroRobot.dispararSimple(x, y);
            accionPorCasilla(resultadoDisparo.getTipoCasilla());
        } else if (tipoMisilADisparar == TipoMisil.CRUZ) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararCruz(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }
        } else if (tipoMisilADisparar == TipoMisil.BOMBARDEO) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararBombardeo(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }
        } else if (tipoMisilADisparar == TipoMisil.NUCLEAR) {
            ResultadoAtaque resultadoAtaque = tableroRobot.dispararNuclear(x, y);
            for (ResultadoDisparo resultado : resultadoAtaque.getResultados()) {
                accionPorCasilla(resultado.getTipoCasilla());
            }

        }
    }

    public void accionPorCasilla(TipoCasilla tipoCasillaDisparada) { // Ejecutara cierta accion dependiento de la
                                                                     // casilla encontrada

        if (tipoCasillaDisparada == TipoCasilla.BARCO) {
            System.out.println("¡Tocado!");

        } else if (tipoCasillaDisparada == TipoCasilla.BOMBA) {

            System.out.println("¡Misil encontrado!");
        }

        else {
            System.out.println("Agua...");
        }

    }

    public void controlTurnos() {
        if (turnoJugador) {
            disparoJugador(0, 0, TipoMisil.BASICO); // falta pedir las coordenadas del ataque
            isJuegoTerminado();
            turnoJugador = false;
        } else {
            disparoRobot();
            isJuegoTerminado();
            turnoJugador = true;
        }
    }

    public void felicitarJugador() {
        if (tableroRobot.todosBarcosHundidos()) {
            System.out.println("¡Felicidades jugador" + jugador.getNombre() + ", has ganado!");
        } else {
            System.out.println("¡Lo siento jugador" + jugador.getNombre() + ", has perdido!");
        }

    }
}
