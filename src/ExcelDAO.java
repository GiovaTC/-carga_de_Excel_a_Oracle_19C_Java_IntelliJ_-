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
