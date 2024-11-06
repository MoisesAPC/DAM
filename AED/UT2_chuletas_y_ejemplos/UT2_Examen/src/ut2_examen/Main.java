package ut2_examen;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final int tipoBD = BaseDeDatos.ORACLE;
        String usuarioOracle = "MOISES";
        String passwordOracle = "1111";

        final String ficheroEntradaSentenciasSQLRuta = "ficheros/sentencias.sql";

        BaseDeDatos baseDeDatos = new BaseDeDatos(tipoBD, usuarioOracle, passwordOracle);
        baseDeDatos.cargarDriver();
        baseDeDatos.conectar();

        /**
         * Mostramos todas las entradas de la tabla "Oficinas"
         */
        System.out.println("----- Tabla Oficinas - Así se ve al principio, tras insertar los datos de Oficinas -----");

        baseDeDatos.ejecutarSentenciaSQL("INSERT INTO Oficinas VALUES (202, 'Orange Vecindario', 'Av. Canarias', 'Santa Lucía')");
        baseDeDatos.ejecutarSentenciaSQL("INSERT INTO Oficinas VALUES (500, 'VodafoneSUR', 'Alcorac 50', 'Aguimes')");
        baseDeDatos.ejecutarSentenciaSQL("INSERT INTO Oficinas VALUES (666, 'Movistar', 'Pancho 33', 'Telde')");
        baseDeDatos.ejecutarSentenciaSQL("INSERT INTO Oficinas VALUES (358, 'Empresa1', 'Mi calle 1', 'Arucas')");
        baseDeDatos.ejecutarSentenciaSQL("INSERT INTO Oficinas VALUES (987, 'Empresa2', 'Mi calle 2', 'Arucas')");

        baseDeDatos.ejecutarSentenciaSQL("SELECT * FROM Oficinas");
        System.out.println("----- ---------------------------------------------------------- -----");

        /**
         * Ejercicio 4.
         *
         * Ejecutamos todas las sentencias SQL guardadas en el fichero "ficheros/sentencias.sql"
         *
         * @note Para el correcto funcionamiento del fichero, cada sentencia SQL debe estar en una sola línea,
         * y además no pueden haber comentarios en el SQL
         */
        Fichero ficheroEntradaSentenciasSQL = new Fichero(Fichero.LECTURA, ficheroEntradaSentenciasSQLRuta);
        ArrayList<String> sentenciasSQL = ficheroEntradaSentenciasSQL.leerLineas();
        baseDeDatos.ejecutarMultiplesSentenciasSQL(sentenciasSQL);

        /**
         * Ejercicio 5.
         *
         * Ejecutamos el procedimiento almacenado
         */
        System.out.println("----- Tabla Oficinas - Antes del cambio de localidad Arucas -> Gáldar -----");
        baseDeDatos.ejecutarSentenciaSQL("SELECT * FROM Oficinas");
        System.out.println("----- ---------------------------------------------------------- -----");

        baseDeDatos.ejecutarProcedimientoAlmacenadoEjercicio5("Arucas", "Gáldar");

        System.out.println("----- Despúes -----");
        baseDeDatos.ejecutarSentenciaSQL("SELECT * FROM Oficinas");
        System.out.println("----- ---------------------------------------------------------- -----");

        ficheroEntradaSentenciasSQL.close();
        baseDeDatos.cerrarConexion();
    }
}
