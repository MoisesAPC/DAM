package ut2_act_eval;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase wrapper que maneja la conexión y operaciones con bases de datos
 */
public class BaseDeDatos {
    private int tipoBD = 0;
    public final static int POSTGRESQL = 0;
    public final static int ORACLE = 1;

    private String hostname = "";
    private String port = "";
    private String user = "";
    private String password = "";
    // Para averiguar el SID, ejecutar SELECT sys_context('USERENV', 'INSTANCE_NAME') FROM dual;
    private String SID = "";
    private String database = "";
    private String driver = "";

    private String url = "";

    // Necesarios para realizar consultas SQL
    public Connection connection = null;
    private Statement statement = null;

    /**
     * @param tipoBD_: 0 (PostgreSQL), 1 (Oracle)
     */
    public BaseDeDatos(int tipoBD_, String userdb, String passworddb) {
        tipoBD = tipoBD_;

        switch(tipoBD) {
            case 0:
                driver = "org.postgresql.Driver";
                hostname = "localhost";
                port = "5432";
                database = "postgres";
                url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;
                break;

            case 1:
                driver = "oracle.jdbc.driver.OracleDriver";
                hostname = "localhost";
                port = "1521";
                SID = "XE";
                url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + SID;
                break;
        }

        user = userdb;
        password = passworddb;
    }

    public void cargarDriver() {
        try {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Error Clase JDBC");
            e.printStackTrace();
        }
    }

    public Statement conectar() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String sentencia) {
        try {
            return statement.executeUpdate(sentencia);
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet executeQuery(String sentencia) {
        try {
            return statement.executeQuery(sentencia);
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
            return null;
        }
    }

    public boolean execute(String sentencia) {
        try {
            return statement.execute(sentencia);
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
            return false;
        }
    }

    public void cerrarConexion() {
        try {
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
        }
    }

    /**
     * Retorna un ResultSet que se usa para mostrar los resultados del SELECT por pantalla
     * @param sentencia
     * @return
     */
    public void ejecutarSentenciaSQL(String sentencia) {
        if (sentencia.isEmpty()) {
            return;
        }

        /**
         * Quitamos los ";" si nos encontramos en una base de datos Oracle. Sino, se
         * producen error de sintaxis
         */
        // Check if the string ends with a semicolon
        if (tipoBD == ORACLE && sentencia.endsWith(";")) {
            // Remove the semicolon
            sentencia = sentencia.substring(0, sentencia.length() - 1);
        }

        /**
         * Use executeQuery() for SELECT statements
         * Use executeUpdate() for INSERT, UPDATE, DELETE, and DDL statements
         * Use execute() when you need the flexibility to handle any type of statement or multiple result sets
         */
        if (sentencia.startsWith("SELECT")) {
            try {
                ResultSet resultadoSelect = executeQuery(sentencia);
                imprimirResultadoSelect(resultadoSelect);
                resultadoSelect.close();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if (sentencia.startsWith("INSERT") ||
                sentencia.startsWith("UPDATE") ||
                sentencia.startsWith("DELETE")) {
            executeUpdate(sentencia);
        }
        else {
            execute(sentencia);
        }
    }

    public void ejecutarMultiplesSentenciasSQL(ArrayList<String> sentenciasSQL) {
        for (String sentencia : sentenciasSQL) {
            ejecutarSentenciaSQL(sentencia);
        }
    }

    public void insertar(String nombreTabla, String parametros) {
        final String sentenciaSQL = "INSERT INTO " + nombreTabla + " VALUES (" + parametros + ")";
        ejecutarSentenciaSQL(sentenciaSQL);
    }

    /**
     * Dado un ResultSet obtenido al ejecutar una sentencia SELECT,
     * este método lo imprime por pantalla, no importa cuáles son los parámetros
     * o condiciones de dicho SELECT
     */
    public void imprimirResultadoSelect(ResultSet resultSet) {
        try {
            if (resultSet == null) {
                System.out.println("resultSet == null");
                return;
            }

            /**
             * Con "ResultSetMetaData", podemos obtener las columnas del ResultSet
             */
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Imprimimos las cabeceras de cada columna
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", metaData.getColumnLabel(i));
            }
            System.out.println();

            // Líneas separadoras
            for (int i = 1; i <= columnCount; i++) {
                System.out.print("--------------------- ");
            }
            System.out.println();

            // Imprimimos los datos
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    // Usando printf, podemos formatear la String de salida para que se vea mejor.
                    // "%-20s" indica un espaciado de 20 con justificación por la izquierda
                    System.out.printf("%-20s  ", resultSet.getString(i));
                }
                System.out.println();
            }
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
        }
    }

    public void ejecutarProcedimientoAlmacenadoEjercicio4(String codigoProveedor, int porcentaje) {
        try {
            String sql = "CALL ejercicio4(?, ?)";
            CallableStatement proc = connection.prepareCall(sql);

            // Establecemos los parámetros
            proc.setString(1, codigoProveedor);  // Código del proveedor
            proc.setInt(2, porcentaje);          // Porcentaje de modificación

            proc.execute();
        }
        catch (SQLException e) {
            System.err.println("Error SQL");
            e.printStackTrace();
        }
    }
}
