package Modelo;

public class Barco {
    private int tamaño;
    private boolean horizontal;

    private int casillasHundidas;

    public Barco(int tamaño, boolean horizontal) {
        this.tamaño = tamaño;
        this.horizontal = horizontal;
    }

    public int getTamaño() {
        return tamaño;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public boolean isHundido() {
        return casillasHundidas >= tamaño;
    }

    public int getCasillasHundidas() {
        return casillasHundidas;
    }

    public void registrarImpacto() {
        casillasHundidas++;
    }

}
