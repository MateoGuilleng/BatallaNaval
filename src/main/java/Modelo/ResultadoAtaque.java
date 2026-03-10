package Modelo;

import java.util.ArrayList;
import java.util.List;

public class ResultadoAtaque {
    private List<ResultadoDisparo> resultados;


    public ResultadoAtaque() {
        resultados = new ArrayList<>();
    }

    public void agregarResultado(ResultadoDisparo resultado) {
        resultados.add(resultado);
    }

    public List<ResultadoDisparo> getResultados(){
        return resultados;
    }
}
