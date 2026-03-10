package Modelo;

public class Casilla {
    private int x;
    private int y;
    private TipoCasilla tipo;
    private boolean disparada;
    private Barco barco;

    public Casilla(int x, int y, TipoCasilla tipo, boolean disparada, Barco barco) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.disparada = disparada;
        this.barco = barco;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }

    public void setTipo(TipoCasilla tipo) {
        this.tipo = tipo;
    }

    public boolean isDisparada() {
        return disparada;
    }

    public void setDisparada(boolean disparada) {
        this.disparada = disparada;
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }

}
