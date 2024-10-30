package act1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MiSQLite {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC"); // Cargamos el driver (sqlite)

            final String databasePath = "C:/Users/Alumnadomañana/Desktop/DAM/AED/UT2_chuletas_y_ejemplos/Actividad1/bd/ejemplo.db";

            // Se establece conexión con BD
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            Statement sentencia = conexion.createStatement(); // Se prepara una consulta
            ResultSet resul = sentencia.executeQuery("SELECT * FROM Departamentos");
            while (resul.next()) {
                System.out.println(resul.getInt(1) + " " + resul.getString(2) + " " + resul.getString(3));
            }

            resul.close();
            sentencia.close();
            conexion.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
