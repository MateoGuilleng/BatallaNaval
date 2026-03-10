package Modelo;

public class CasillaMisil {
    private int x;
    private int y;
    private TipoMisil tipo;
    private boolean disparada;

    public CasillaMisil(int x, int y, TipoMisil tipo, boolean disparada) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.disparada = disparada;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TipoMisil getTipo() {
        return tipo;
    }

    public void setTipo(TipoMisil tipo) {
        this.tipo = tipo;
    }

    public boolean isDisparada() {
        return disparada;
    }

    public void setDisparada(boolean disparada) {
        this.disparada = disparada;
    }
}
