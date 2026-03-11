package Controlador;

import Vista.VentanaPrincipal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControladorVista implements ActionListener {
    private VentanaPrincipal vista;
    private ControladorGeneral controlGeneral;

    public ControladorVista(ControladorGeneral general) {
        this.controlGeneral = general;
        this.vista = new VentanaPrincipal();

        this.vista.getBtnSalir().addActionListener(this);

    }

    private void saliAplicacion() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        if (a == vista.getBtnSalir()) {
            saliAplicacion();
        }
    }
}
