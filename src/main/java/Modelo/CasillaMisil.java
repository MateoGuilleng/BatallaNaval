package Modelo;

public class CasillaMisil {
    private int x;
    private int y;
    private TipoMisil tipo;
    private boolean encontrada;

    public CasillaMisil(int x, int y, TipoMisil tipo, boolean encontrada) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.encontrada = encontrada;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public TipoMisil getTipoMisil() {
        return tipo;
    }

    public void setTipo(TipoMisil tipo) {
        this.tipo = tipo;
    }

    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }
}
