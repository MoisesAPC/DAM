package ut1_manejodeficheros;

import java.util.ArrayList;
import java.util.Scanner;

/* Abrir el directorio `UT1_ManejoDeFicheros` en el IDE para que funcionen las rutas relativas */
public class main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Equipo> listaEquipos = new ArrayList<Equipo>();
        final String ficheroSecuencial = "ficheros/datosEquipos.txt";
        final String ficheroRAF = "ficheros/datosEquipos.dat";

        /**
         * La misma u otra clase leerá este fichero secuencial y según va leyendo los
         * registros, los vuelca en un fichero de acceso aleatorio (la estructura y dimensión
         * de los campos, la estableces inicialmente al tamaño y tipología que tú consideres
         * (indícalo en el cód fuente) y se denominará datosEquipos.dat.
         */
        FicheroUtils.leerFicheroSecuencialYVolcarEnRAF(ficheroSecuencial, ficheroRAF, listaEquipos);

        /**
         * Otra clase/método permitirá la actualización del teléfono del club, de forma que se
         * pide el dato del número de club a modificar, se busca en el fichero de acceso aleatorio
         * directamente ese registro, y se modifica el teléfono. Debes mostrar la información
         * SOLO de ese registro después de la modificación. Y debes minimizar las
         * lecturas/escrituras a las estrictamente necesarias.
         *
         * Ahora lee el nombre del presidente del tercer equipo, localízalo y muestra su teléfono.
         */
        System.out.println("Introduce el número del club: ");
        int numClub = teclado.nextInt();
        System.out.println("Introduce el teléfono del club: ");
        int telefono = teclado.nextInt();
        FicheroUtils.actualizarTelefonoDelClub(ficheroRAF, numClub, telefono);
    }
}
