package Modelo;

public class Barco {
    private int tamaño;
    private boolean horizontal;

    private int CasillasHundidas;

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
        return CasillasHundidas >= tamaño;
    }

    public int getCasillasHundidas() {
        return CasillasHundidas;
    }

    public void setCasillasHundidas(int CasillasHundidas) {
        this.CasillasHundidas = CasillasHundidas;
    }

}
