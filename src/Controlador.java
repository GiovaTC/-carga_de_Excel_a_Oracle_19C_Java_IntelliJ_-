import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Controlador {

    private Vista vista;
    private ExcelService service;
    private ExcelDAO dao;

    public Controlador(Vista vista) {
        this.vista = vista;
        this.service = new ExcelService();
        this.dao = new ExcelDAO();
        
        vista.btnCargar.addActionListener(this::cargarExcel);
    }

    private void cargarExcel(ActionEvent actionEvent) {
    }


}
