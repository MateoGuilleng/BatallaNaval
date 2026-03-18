package Modelo;

public class Juego {
    private Jugador jugador;
    private Robot   robot;
    private Tablero tableroJugador;
    private Tablero tableroRobot;
    private boolean turnoJugador;

    public Juego(Jugador jugador, Robot robot, Tablero tableroJugador, Tablero tableroRobot) {
        this.jugador        = jugador;
        this.robot          = robot;
        this.tableroJugador = tableroJugador;
        this.tableroRobot   = tableroRobot;
        this.turnoJugador   = true;
    }

    public Jugador  getJugador()        { return jugador; }
    public Robot    getRobot()          { return robot; }
    public Tablero  getTableroJugador() { return tableroJugador; }
    public Tablero  getTableroRobot()   { return tableroRobot; }
    public boolean  isTurnoJugador()    { return turnoJugador; }
    public void     setTurnoJugador(boolean t) { this.turnoJugador = t; }
}
