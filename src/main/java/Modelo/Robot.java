package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Robot {
    private String nombre;
    private Map<TipoMisil, Integer> misiles;

    public Robot(String nombre) {
        this.nombre = nombre;
        this.misiles = new HashMap<>();
        
        misiles.put(TipoMisil.BASICO, 10);
        misiles.put(TipoMisil.CRUZ, 1);
        misiles.put(TipoMisil.BOMBARDEO, 1);
        misiles.put(TipoMisil.NUCLEAR, 1);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void agregarMisil(TipoMisil tipoMisil) {
        int cantidadActual = misiles.getOrDefault(tipoMisil, 0);
        misiles.put(tipoMisil, cantidadActual + 1);
        System.out.println("Misil " + tipoMisil + " agregado a " + nombre);
    }

    public int getNumeroMisiles(TipoMisil tipoMisil) {
        return misiles.getOrDefault(tipoMisil, 0);
    }

    public void restarMisil(TipoMisil tipoMisil) {
        int cantidadActual = misiles.getOrDefault(tipoMisil, 0);
        if (cantidadActual > 0) {
            misiles.put(tipoMisil, cantidadActual - 1);
            System.out.println("Misil " + tipoMisil + " restado a " + nombre);
        } else {
            System.out.println("No hay misiles " + tipoMisil + " disponibles para restar a " + nombre);
        }
    }
}
