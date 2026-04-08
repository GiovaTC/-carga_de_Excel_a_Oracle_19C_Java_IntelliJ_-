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
