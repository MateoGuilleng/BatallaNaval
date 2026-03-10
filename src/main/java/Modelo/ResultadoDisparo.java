package Modelo;

public class ResultadoDisparo {
    private int x;
    private int y;
    private TipoCasilla tipo;


    public ResultadoDisparo(int x, int y, TipoCasilla tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TipoCasilla getTipoCasilla() {
        return tipo;
    }


}
