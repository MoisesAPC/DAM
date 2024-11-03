package act3;

import java.sql.*;

public class MiOracle11g {
    public static void main(String[] args) {
        try {
            // Sentencias para la conexión
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Cargamos el driver (Oracle 11g)

            final String host = "localhost";
            final String port = "1521";
            // Para averiguar el SID, ejecutar SELECT sys_context('USERENV', 'INSTANCE_NAME') FROM dual;
            final String SID = "XE";
            final String user = "MOISESDB";
            final String password = "1111";

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + SID, user, password);
            Statement sentence = con.createStatement();

            // Insertamos una entrada de ejemplo
            sentence.executeUpdate("INSERT INTO Departamentos VALUES (50, 'minom', 'milocal')");

            // Mostramos la tabla "Departamentos"
            ResultSet resul = sentence.executeQuery("SELECT * FROM Departamentos");

            while(resul.next()) {
                System.out.println(resul.getInt(1) + " " + resul.getString(2) + " " + resul.getString(3));
            }

            if (resul != null) resul.close();
            if (sentence != null) sentence.close();
            if (con != null) con.close();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Error clase JDBC");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
        }
    }
}
