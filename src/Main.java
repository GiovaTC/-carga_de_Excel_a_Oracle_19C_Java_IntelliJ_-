public class Main {

    public static void main(String[] args) {

        // Generar Excel de prueba
        GeneradorExcel.crearArchivoEjemplo("datos_ejemplo.xlsx");

        Vista vista = new Vista();
        new Controlador(vista);

        vista.setVisible(true);
    }
}   