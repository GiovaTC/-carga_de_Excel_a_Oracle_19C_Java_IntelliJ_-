# -carga_de_Excel_a_Oracle_19C_Java_IntelliJ_- :.
📊 Carga de Excel a Oracle 19c con Java (IntelliJ):

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/7f3f95f7-a746-4770-85c9-5740c27c3e75" />  

```
Solución completa, estructurada y funcional que incluye:

✔ Cargar archivo Excel (.xlsx)
✔ Mostrar filas y celdas en JTable (Swing)
✔ Persistencia en Oracle 19c (JDBC)
✔ Arquitectura por capas (Modelo – DAO – Servicio – Controlador – Vista)
✔ Generación de archivo Excel de ejemplo

🧩 1. DEPENDENCIAS (Maven)
<dependencies>

    <!-- Apache POI (Excel) -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>

    <!-- Oracle JDBC -->
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>21.9.0.0</version>
    </dependency>

</dependencies>

🧩 2. MODELO DE DATOS (Oracle 19c)
CREATE TABLE EXCEL_DATA (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    COLUMNA1 VARCHAR2(100),
    COLUMNA2 VARCHAR2(100),
    COLUMNA3 VARCHAR2(100),
    COLUMNA4 VARCHAR2(100)
);

🧩 3. MODELO
public class ExcelData {

    private String columna1;
    private String columna2;
    private String columna3;
    private String columna4;

    public ExcelData(String c1, String c2, String c3, String c4) {
        this.columna1 = c1;
        this.columna2 = c2;
        this.columna3 = c3;
        this.columna4 = c4;
    }

    public String getColumna1() { return columna1; }
    public String getColumna2() { return columna2; }
    public String getColumna3() { return columna3; }
    public String getColumna4() { return columna4; }
}

🧩 4. CONEXIÓN BD
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "system";
    private static final String PASS = "123456";

    public static Connection getConexion() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

🧩 5. DAO
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExcelDAO {

    public void insertar(ExcelData data) {
        String sql = "INSERT INTO EXCEL_DATA (COLUMNA1, COLUMNA2, COLUMNA3, COLUMNA4) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, data.getColumna1());
            ps.setString(2, data.getColumna2());
            ps.setString(3, data.getColumna3());
            ps.setString(4, data.getColumna4());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

🧩 6. SERVICIO (LECTURA EXCEL)
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    public List<ExcelData> leerExcel(String ruta) {

        List<ExcelData> lista = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(ruta))) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue; // Saltar encabezado

                String c1 = getValor(row.getCell(0));
                String c2 = getValor(row.getCell(1));
                String c3 = getValor(row.getCell(2));
                String c4 = getValor(row.getCell(3));

                lista.add(new ExcelData(c1, c2, c3, c4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private String getValor(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }
}

🧩 7. GENERADOR DE EXCEL (DATOS DE PRUEBA)
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;

public class GeneradorExcel {

    public static void crearArchivoEjemplo(String ruta) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Datos");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Nombre");
            header.createCell(1).setCellValue("Edad");
            header.createCell(2).setCellValue("Ciudad");
            header.createCell(3).setCellValue("Profesión");

            Object[][] datos = {
                    {"Juan", 25, "Bogotá", "Ingeniero"},
                    {"Ana", 30, "Medellín", "Doctora"},
                    {"Luis", 28, "Cali", "Abogado"},
                    {"Marta", 35, "Armenia", "Docente"}
            };

            int fila = 1;

            for (Object[] d : datos) {
                Row row = sheet.createRow(fila++);
                for (int i = 0; i < d.length; i++) {
                    row.createCell(i).setCellValue(d[i].toString());
                }
            }

            FileOutputStream fos = new FileOutputStream(ruta);
            wb.write(fos);
            fos.close();

            System.out.println("Excel generado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

🧩 8. VISTA (SWING)
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;
    JButton btnCargar;

    public Vista() {

        setTitle("Carga Excel a Oracle");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{"Col1", "Col2", "Col3", "Col4"}, 0);
        tabla = new JTable(modelo);

        btnCargar = new JButton("Cargar Excel");

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(btnCargar, BorderLayout.SOUTH);
    }
}

🧩 9. CONTROLADOR
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

🧩 10. MAIN
public class Main {

    public static void main(String[] args) {

        // Generar Excel de prueba
        GeneradorExcel.crearArchivoEjemplo("datos_ejemplo.xlsx");

        Vista vista = new Vista();
        new Controlador(vista);

        vista.setVisible(true);
    }
}

🧪 RESULTADO FINAL

Al ejecutar:

Se genera automáticamente:
datos_ejemplo.xlsx
La interfaz permite:
Seleccionar el archivo Excel
Visualizarlo en JTable
Insertar los datos en Oracle 19c.

🚀 MEJORAS POSIBLES

Para llevar esta solución a nivel más profesional:

✔ Validación de tipos de datos
✔ Manejo de transacciones (commit / rollback)
✔ Soporte dinámico de columnas
✔ Exportar desde JTable → Excel
✔ Pool de conexiones (HikariCP)
✔ Patrón MVC con separación estricta :. . / .
