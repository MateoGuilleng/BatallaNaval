package Controlador;


public class ControladorGeneral {

    private ControladorVista cVista;

    public ControladorGeneral() {

        this.cVista = new ControladorVista(this);
    }
}
