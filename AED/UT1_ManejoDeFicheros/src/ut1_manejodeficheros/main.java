package ut1_manejodeficheros;

import java.util.ArrayList;

/* Abrir el directorio `UT1_ManejoDeFicheros` en el IDE para que funcionen las rutas relativas */
public class main {
    public static void main(String[] args) {
        ArrayList<Equipo> listaEquipos = new ArrayList<Equipo>();
        final String ficheroSecuencialEntrada = "ficheros/datosEquipos.txt";
        final String ficheroRAFSalida = "ficheros/datosEquipos.txt";

        FicheroUtils.leerFicheroSecuencialYVolcarEnRAF(ficheroSecuencialEntrada, ficheroRAFSalida, listaEquipos);
    }
}
