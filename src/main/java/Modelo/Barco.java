package Modelo;

public class Barco {
    private int tamaño;
    private boolean horizontal;
    private int casillasHundidas;

    public Barco(int tamaño, boolean horizontal) {
        this.tamaño = tamaño;
        this.horizontal = horizontal;
        this.casillasHundidas = 0;
    }

    public int     getTamaño()                       { return tamaño; }
    public void    setTamaño(int tamaño)             { this.tamaño = tamaño; }
    public boolean isHorizontal()                    { return horizontal; }
    public void    setHorizontal(boolean horizontal) { this.horizontal = horizontal; }
    public int     getCasillasHundidas()             { return casillasHundidas; }
    public void    setCasillasHundidas(int n)        { this.casillasHundidas = n; }
}
