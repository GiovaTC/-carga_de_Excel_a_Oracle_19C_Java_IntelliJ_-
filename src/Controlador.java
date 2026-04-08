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

        // 🔹 Generar archivo de ejemplo automáticamente
        GeneradorExcel.crearArchivoEjemplo("datos_ejemplo.csv");

        vista.btnCargar.addActionListener(this::cargarExcel);
    }

    private void cargarExcel(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(null);

        if (opcion == JFileChooser.APPROVE_OPTION) {

            String ruta = fileChooser.getSelectedFile().getAbsolutePath();

            List<ExcelData> lista = service.leerExcel(ruta);

            vista.modelo.setRowCount(0);

            for (ExcelData d : lista) {

                vista.modelo.addRow(new Object[]{
                        d.getColumna1(),
                        d.getColumna2(),
                        d.getColumna3(),
                        d.getColumna4()
                });

                dao.insertar(d);
            }

            JOptionPane.showMessageDialog(null, "Datos cargados y guardados en Oracle");
        }
    }
}
