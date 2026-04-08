import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    public List<ExcelData> leerExcel(String ruta) {

        List<ExcelData> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;
            boolean esPrimera = true;

            while ((linea = br.readLine()) != null) {

                if (esPrimera) {
                    esPrimera = false;
                    continue;
                }

                String[] columnas = linea.split(",");

                String c1 = getValor(columnas, 0);
                String c2 = getValor(columnas, 1);
                String c3 = getValor(columnas, 2);
                String c4 = getValor(columnas, 3);

                lista.add(new ExcelData(c1, c2, c3, c4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔁 Reemplazo de getValor(Cell)
    private String getValor(String[] columnas, int index) {
        if (columnas == null || index >= columnas.length) {
            return "";
        }

        return columnas[index].trim();
    }
}