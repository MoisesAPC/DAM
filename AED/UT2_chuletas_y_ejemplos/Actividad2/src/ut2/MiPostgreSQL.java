package ut2;

import java.sql.*;

public class MiPostgreSQL {
    public static void main(String[] args) {
        try {
            // Sentencias para la conexión
            Class.forName("org.postgresql.Driver"); // Cargamos el driver (postgresql)

            final String host = "localhost";
            final String port = "5432";
            final String database = "postgres";
            final String user = "postgres";
            final String password = "1111";

            Connection con = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            Statement sentence = con.createStatement();

            // Insertamos una entrada de ejemplo
            sentence.executeUpdate("INSERT INTO Departamentos VALUES (50, 'minom', 'milocal');");

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
