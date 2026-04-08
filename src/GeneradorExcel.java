import java.io.*;

public class GeneradorExcel {

    public static void crearArchivoEjemplo(String ruta) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {

            // encabezado .
            bw.write("Nombre,Edad,Ciudad,Profesion");
            bw.newLine();

            // datos .
            bw.write("Juan,25,Bogota,Ingeniero");
            bw.newLine();

            bw.write("Ana,30,Medellin,Doctora");
            bw.newLine();

            bw.write("Luis,28,Cali,Abogado");
            bw.newLine();

            bw.write("Marta,35,Armenia,Docente");
            bw.newLine();

            System.out.println("Archivo CSV generado correctamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
