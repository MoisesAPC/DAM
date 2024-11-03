package ut2_act_eval;

import java.util.ArrayList;

/**
 * El ejercicio 1 se encuentra en el fichero "Pestano_Castro_Moises_Antonio_UT2_ActividadIntegradora.pdf" en la raíz del proyecto
 */
public class Main {
    public static void main(String[] args) {
        int tipoBD = BaseDeDatos.ORACLE;
        String usuarioPostgreSQL = "postgres";
        String passwordPostgreSQL = "1111";
        String usuarioOracle = "MOISESDB";
        String passwordOracle = "1111";

        final String nombreTablaProveedores = "Proveedores";
        final String ficheroEntradasProveedoresRuta = "ficheros/proveedores.txt";
        final String nombreTablaProductos = "Productos";
        final String ficheroEntradasProductosRuta = "ficheros/productos.txt";
        ArrayList<String> entradasDeTabla = null;

        final String ficheroEntradaSentenciasSQLRuta = "ficheros/sentencias.sql";

        BaseDeDatos baseDeDatos = new BaseDeDatos(tipoBD, usuarioOracle, passwordOracle);
        baseDeDatos.cargarDriver();
        baseDeDatos.conectar();

        /// EJERCICIO 2 ///
        /**
         * Insertamos las entradas en la tabla Proveedores
         */
        Fichero ficheroEntradasProveedores = new Fichero(Fichero.LECTURA, ficheroEntradasProveedoresRuta);
        entradasDeTabla = ficheroEntradasProveedores.leerLineas();
        for (String entrada : entradasDeTabla) {
            baseDeDatos.insertar(nombreTablaProveedores, entrada);
        }
        /**
         * Insertamos las entradas en la tabla Productos
         */
        Fichero ficheroEntradasProductos = new Fichero(Fichero.LECTURA, ficheroEntradasProductosRuta);
        entradasDeTabla = ficheroEntradasProductos.leerLineas();
        for (String entrada : entradasDeTabla) {
            baseDeDatos.insertar(nombreTablaProductos, entrada);
        }

        /// EJERCICIO 3 ///
        Fichero ficheroEntradaSentenciasSQL = new Fichero(Fichero.LECTURA, ficheroEntradaSentenciasSQLRuta);
        ArrayList<String> sentenciasSQL = ficheroEntradaSentenciasSQL.leerLineas();
        baseDeDatos.ejecutarMultiplesSentenciasSQL(sentenciasSQL);

        /// EJERCICIO 4 ///
        System.out.println("--- Ejercicio 4 ---");
        System.out.println("- ANTES -");
        baseDeDatos.ejecutarSentenciaSQL("SELECT prov.Nombre_prov, prov.Bonifica FROM Proveedores prov ORDER BY prov.Cod_prov");

        baseDeDatos.ejecutarProcedimientoAlmacenadoEjercicio4("P005", 10);

        System.out.println("- Después -");
        baseDeDatos.ejecutarSentenciaSQL("SELECT prov.Nombre_prov, prov.Bonifica FROM Proveedores prov ORDER BY prov.Cod_prov");

        /// EJERCICIO 5 ///
        /**
         * Actualización de datos de ejemplo, los cuales harán que se active el trigger y los respectivos datos
         * pasen a la tabla "pedidos_pendientes"
         */
        baseDeDatos.ejecutarSentenciaSQL("UPDATE Productos SET Stock = Stock - 1");
        baseDeDatos.ejecutarSentenciaSQL("SELECT * FROM pedidos_pendientes");

        ficheroEntradasProveedores.close();
        ficheroEntradasProductos.close();
        baseDeDatos.cerrarConexion();
    }
}
