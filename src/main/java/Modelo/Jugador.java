package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Jugador {
    private String nombre;
    private Map<TipoMisil, Integer> misiles;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.misiles = new HashMap<>();
        misiles.put(TipoMisil.BASICO,    20);
        misiles.put(TipoMisil.CRUZ,       1);
        misiles.put(TipoMisil.BOMBARDEO,  1);
        misiles.put(TipoMisil.NUCLEAR,    1);
    }

    public String getNombre()                          { return nombre; }
    public void   setNombre(String nombre)             { this.nombre = nombre; }
    public Map<TipoMisil, Integer> getMisiles()        { return misiles; }
    public void   setMisiles(Map<TipoMisil, Integer> m){ this.misiles = m; }

    public int getMisil(TipoMisil tipo) {
        return misiles.getOrDefault(tipo, 0);
    }

    public void setMisil(TipoMisil tipo, int cantidad) {
        misiles.put(tipo, cantidad);
    }
}
