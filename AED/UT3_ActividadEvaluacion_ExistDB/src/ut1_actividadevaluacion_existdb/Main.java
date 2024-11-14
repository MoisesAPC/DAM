package ut1_actividadevaluacion_existdb;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

/**
 * Los ficheros .JAR con las librerías de exist-DB se encuentran en el siguiente dirrectorio por defecto (Windows):
 *
 * C:\eXist-db\lib
 *
 * Si se usa IntelliJ, añadir al proyecto usando File > Project Structure > Libraries > Ícono del "+"
 */

/**
 * Ejemplo de Empleados.xml que usa esta aplicación:
 *
 * <Empleados>
 * 	<empleado>
 * 		<id>1</id>
 * 		<nombre>David</nombre>
 * 		<dep>1</dep>
 * 		<salario>1.0</salario>
 * 	</empleado>
 * 	<empleado>
 * 		<id>2</id>
 * 		<nombre>Eduardo</nombre>
 * 		<dep>2</dep>
 * 		<salario>2.0</salario>
 * 	</empleado>
 * 	<empleado>
 * 		<id>3</id>
 * 		<nombre>Joseph</nombre>
 * 		<dep>3</dep>
 * 		<salario>3.0</salario>
 * 	</empleado>
 * 	<empleado>
 * 		<id>4</id>
 * 		<nombre>Miguel</nombre>
 * 		<dep>4</dep>
 * 		<salario>4.0</salario>
 * 	</empleado>
 * </Empleados>
 */

public class Main {
    public static void main(String[] args) {
        try {
            /**
             * Iniciamos el driver
             */
            String driver = "org.exist.xmldb.DatabaseImpl";
            Class c1 = Class.forName(driver);
            Database basedatos = (Database) c1.newInstance();
            DatabaseManager.registerDatabase(basedatos);

            /**
             * Obtenemos la colección llamada "Pruebas", en el directorio "db"
             * (en el DB Manager, la ruta se ve como "/db/Pruebas/")
             *
             * NOTA: El puerto que estoy utilizando es el 3132
             */
            final String URL = "xmldb:exist://localhost:3132/exist/xmlrpc/db/Pruebas";
            final String usuario = "admin";
            final String pass = "1111";
            Collection coleccion = DatabaseManager.getCollection(URL, usuario, pass);
            XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

            /**
             * Consulta: Obtiene los salarios de cada empleado
             */
            ResourceSet resul = servicio.query("for $emp in /Empleados/empleado/salario return $emp");
            // Imprimimos el resultado por consola
            System.out.println("--- SALARIOS POR EMPLEADO: ---");
            for (int i = 0; i < resul.getSize(); i++) {
                org.xmldb.api.base.Resource resource1 = resul.getResource(i);
                System.out.println(resource1.getContent());
            }

            /**
             * Consulta: Contar el número de empleados
             */
            resul = servicio.query("count(/Empleados/empleado)");
            // Imprimimos el resultado por consola
            System.out.println();
            System.out.print("- NÚMERO DE EMPLEADOS: ");
            org.xmldb.api.base.Resource resource2 = resul.getResource(0);
            System.out.println(resource2.getContent());

            /**
             * Consulta: Obtener los nombres de los empleados ordenados por salario (de mayor a menor)
             */
            resul = servicio.query("for $emp in /Empleados/empleado\n" +
                    "order by $emp/salario descending\n" +
                    "return $emp/nombre"
            );
            // Imprimimos el resultado por consola
            System.out.println();
            System.out.println("--- EMPLADOS ORDENADOS POR SALARIO: ---");
            for (int i = 0; i < resul.getSize(); i++) {
                org.xmldb.api.base.Resource resource3 = resul.getResource(i);
                System.out.println(resource3.getContent());
            }

            coleccion.close();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }
}
