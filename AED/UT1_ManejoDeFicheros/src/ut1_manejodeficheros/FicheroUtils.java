package ut1_manejodeficheros;

import java.io.*;
import java.util.ArrayList;

public class FicheroUtils {
    /**
     * Lee el contenido del fichero secuencial de entrada,
     * y mientras lo hace, vuelca, los contenidos en un fichero de acceso aleatorio (RAF)
     *
     * También se guarda cada equipo parseado en la lista
     */
    public static void leerFicheroSecuencialYVolcarEnRAF(String ficheroSecuencialEntrada, String ficheroRAFSalida, ArrayList<Equipo> listaEquipos) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ficheroSecuencialEntrada));
            String linea;

            while ((linea = br.readLine()) != null) {
                // Lee el contenido del fichero secuencial de entrada
                Equipo nuevoEquipo = new Equipo();
                nuevoEquipo.parsearLinea(linea, "##");

                // Vuelca los contenidos en un RAF
                RandomAccessFile raf = new RandomAccessFile(ficheroRAFSalida, "rw");
                escribirEquipoEnFichero(raf, nuevoEquipo);

                // Guarda cada equipo parseado en la lista
                listaEquipos.add(nuevoEquipo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void escribirEquipoEnFichero(RandomAccessFile raf, Equipo equipo) throws IOException {
        raf.writeInt(equipo.getNum());
        escribirStringEnFichero(raf, equipo.getNombre(), Equipo.tamanoStrings);
        escribirStringEnFichero(raf, equipo.getPresidente(), Equipo.tamanoStrings);
        raf.writeLong(equipo.getTelefono());
        escribirStringEnFichero(raf, equipo.getLocalidad(), Equipo.tamanoStrings);
    }

    private static void escribirStringEnFichero(RandomAccessFile raf, String string, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(string);
        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }

    /**
     * Dado el número del club a buscar, se busca en el fichero de acceso aleatorio,
     * y se cambia el teléfono.
     */
    public static void actualizarTelefonoDelClub(String ficheroRAF, int numClub, int telefono) {

    }
}
