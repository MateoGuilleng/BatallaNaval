package Modelo;

public enum TipoCasilla {
    AGUA,       // sin disparar
    BARCO,      // barco colocado (solo visible en tablero propio)
    TOCADO,     // casilla de barco impactada, barco aún no hundido → amarillo
    HUNDIDO,    // barco completamente hundido → rojo
    BOMBA,      // misil oculto recogido → morado
    AGUA_DISP   // agua ya disparada → azul claro
}
