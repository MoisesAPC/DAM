package ut1_manejodeficheros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* Abrir el directorio `UT1_ManejoDeFicheros` en el IDE para que funcionen las rutas relativas */
/* Usa el JDK 23 mínimo */
public class main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        final String ficheroSecuencial = "ficheros/datosEquipos.txt";
        final String ficheroRAF = "ficheros/datosEquipos.dat";
        final String ficheroObj = "ficheros/datosEquipos.obj";
        final String ficheroAsc = "ficheros/datosEquipos.asc";
        final String ficheroJSON = "ficheros/datosEquipos.json";

        /**
         * La misma u otra clase leerá este fichero secuencial y según va leyendo los
         * registros, los vuelca en un fichero de acceso aleatorio (la estructura y dimensión
         * de los campos, la estableces inicialmente al tamaño y tipología que tú consideres
         * (indícalo en el cód fuente) y se denominará datosEquipos.dat.
         */
        FicheroUtils.leerFicheroSecuencialYVolcarEnRAF(ficheroSecuencial, ficheroRAF);

        /**
         * Otra clase/método permitirá la actualización del teléfono del club, de forma que se
         * pide el dato del número de club a modificar, se busca en el fichero de acceso aleatorio
         * directamente ese registro, y se modifica el teléfono. Debes mostrar la información
         * SOLO de ese registro después de la modificación. Y debes minimizar las
         * lecturas/escrituras a las estrictamente necesarias.
         */
        try {
            System.out.print("Introduce el número del club: ");
            int numClub = teclado.nextInt();
            System.out.print("Introduce el teléfono del club: ");
            int telefono = teclado.nextInt();
            FicheroUtils.actualizarTelefonoDelClub(ficheroRAF, numClub, telefono);

            /* Ahora lee el nombre del presidente del tercer equipo, localízalo y muestra su teléfono. */
            Equipo tercerEquipo = FicheroUtils.buscarEquipoPorIndice(ficheroRAF, 3 - 1);
            System.out.println("Teléfono del tercer equipo: " + tercerEquipo.getTelefono());

            /**
             * Crea una clase de Objetos Equipos. Leerás los datos del último fichero de acceso
             * aleatorio, creas los objetos y los guardas en un fichero de Objetos datosEquipos.obj.
             * Finamente para comprobar, lee el ficheros de objetos y muéstralo.
             */
            ObjetosEquipos.guardarEquiposEnFicheroObj(ficheroRAF, ficheroObj);
            ArrayList<Equipo> listaEquiposDesdeFicheroOBJ = ObjetosEquipos.leerEquipoDeFichero(ficheroObj);

            System.out.println("--- Fichero OBJ ---");
            for (Equipo equipo : listaEquiposDesdeFicheroOBJ) {
                System.out.println(equipo.toString());
            }
            System.out.println("--- --------- ---");

            /**
             * Lee los objetos y GUARDA los datos como datos primitivos de Java en un fichero
             * Equipos.asc. Luego lees el fichero y muestras sólo aquellos registros con número de
             * club entre 200 y 300.
             */

            // Reutilizamos el array `listaEquiposDesdeFicheroOBJ` que obtuvimos de la lectura el fichero .obj en el apartado de arriba
            FicheroUtils.guardarDatosEnFicheroASC(ficheroAsc, listaEquiposDesdeFicheroOBJ);
            ArrayList<Equipo> listaEquiposDesdeASC = FicheroUtils.leerDatosDeFicheroASCYMostrarEntradasEnRango(ficheroAsc);

            System.out.println("--- FICHEROS ASC ---");
            for (Equipo equipo : listaEquiposDesdeASC) {
                if ((equipo.getNum() >= 200) && (equipo.getNum() <= 300)) {
                    System.out.println(equipo.toString());
                }
            }
            System.out.println("--- --------- ---");

            /**
             * Del fichero de Objetos, lee todos los registros y crea el correspondiente JSON. Y para
             * finalizar, lee el fichero que acabas de crear en formato json y muestra la información
             * por pantalla adecuadamente, leyéndolo como objetos con el método que elijas.
             */
            // Reutilizamos el array `listaEquiposDesdeFicheroOBJ` que obtuvimos de la lectura el fichero .obj en el apartado de arriba
            FicheroUtils.escribirJSON(ficheroJSON, listaEquiposDesdeFicheroOBJ);

            ArrayList<Equipo> listaEquiposDesdeJSON = FicheroUtils.leerJSON(ficheroJSON);

            System.out.println("--- JSON ---");
            for (Equipo equipo : listaEquiposDesdeJSON) {
                System.out.println(equipo.toString());
            }
            System.out.println("--- --------- ---");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
