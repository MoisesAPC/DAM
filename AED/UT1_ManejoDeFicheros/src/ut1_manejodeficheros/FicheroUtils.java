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
    public static void leerFicheroSecuencialYVolcarEnRAF(String ficheroSecuencialEntrada, String ficheroRAFSalida) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ficheroSecuencialEntrada));
            RandomAccessFile raf = new RandomAccessFile(ficheroRAFSalida, "rw");
            String linea;

            while ((linea = br.readLine()) != null) {
                // Lee el contenido del fichero secuencial de entrada
                Equipo nuevoEquipo = new Equipo();
                nuevoEquipo.parsearLinea(linea, "##");

                // Vuelca los contenidos en un RAF
                escribirEquipoEnFichero(raf, nuevoEquipo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirEquipoEnFichero(RandomAccessFile raf, Equipo equipo) throws IOException {
        raf.writeInt(equipo.getNum());
        escribirStringEnFichero(raf, equipo.getNombre(), Equipo.tamanoStrings);
        escribirStringEnFichero(raf, equipo.getPresidente(), Equipo.tamanoStrings);
        raf.writeInt(equipo.getTelefono());
        escribirStringEnFichero(raf, equipo.getLocalidad(), Equipo.tamanoStrings);
    }

    private static void escribirStringEnFichero(RandomAccessFile raf, String string, int longitud) throws IOException {
        // Como cada caracter ocupa 2 bytes, realizamos la división para que
        // la String se guarde con el tamaño correcto
        longitud /= Character.BYTES;
        StringBuilder sb = new StringBuilder(string);

        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }

    public static Equipo leerEquipoDeFichero(RandomAccessFile raf) throws IOException {
        int numClub = raf.readInt();
        String nombre = leerStringDeFichero(raf, Equipo.tamanoStrings);
        String presidente = leerStringDeFichero(raf, Equipo.tamanoStrings);
        int telefono = raf.readInt();
        String localidad = leerStringDeFichero(raf, Equipo.tamanoStrings);

        return new Equipo(numClub, nombre, presidente, telefono, localidad);
    }

    private static String leerStringDeFichero(RandomAccessFile raf, int longitud) throws IOException {
        // Como cada caracter ocupa 2 bytes, realizamos la división para que
        // la String se guarde con el tamaño correcto
        longitud /= Character.BYTES;
        StringBuffer sb = new StringBuffer(longitud);

        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    /**
     * Dado el número del club a buscar, se busca en el fichero de acceso aleatorio,
     * y se cambia el teléfono.
     */
    public static void actualizarTelefonoDelClub(String ficheroRAF, int numClub, int telefono) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "rw");

        // Tamaño del fichero en bytes
        long totalBytesRAF = raf.length();
        long posicionRegistroActual = 0;

        // Nos aseguramos de empezar la búsqueda desde el principio del ficherp
        raf.seek(0);

        while (raf.getFilePointer() < totalBytesRAF) {
            posicionRegistroActual = raf.getFilePointer();
            raf.seek(posicionRegistroActual);
            // Solamente cargamos el numero del club y lo comprobamos con el que queremos cargar
            int numClubActual = raf.readInt();
            if (numClubActual == numClub) {
                /**
                 * Si coinciden, entonces ahora sí cargamos el registro entero para su modificación
                 *
                 * @note Como ya hemos hecho un `raf.readInt();` arriba,
                 *       el puntero está desplazado +4 (el tamaño de un Integer),
                 *
                 *       Volvemos a poner el puntero al principio del registro en el fichero para su correcta lectura
                 */
                raf.seek(posicionRegistroActual);
                Equipo equipo = leerEquipoDeFichero(raf);
                equipo.setTelefono(telefono);

                // `leerEquipoDeFichero` hace que el file pointer avance,
                // por lo que volvemos a hacer un seek para corregirlo
                raf.seek(posicionRegistroActual);
                escribirEquipoEnFichero(raf, equipo);
                return;
            }
            else {
                /**
                 * Leemos la siguiente entrada
                 *
                 * @note Como ya hemos hecho un `raf.readInt();` arriba,
                 *       el puntero está desplazado +4 (el tamaño de un Integer),
                 *       por lo que al hacer el skip, tenemos que restar dicho desplazamiento
                 *       de 4 para que al hacer el skipBytes, el puntero de fichero se posicione
                 *       realmente al principio del siguiente registro
                 *       (y no al principio del registro +4, lo que ocasionaría lectura erónea del refistro)
                 */
                raf.skipBytes(Equipo.tamanoBytes - Integer.BYTES);
            }
        }
    }

    // Busca en la lista un registro por índice en el fichero
    // (empezando por 0)
    public static Equipo buscarEquipoPorIndice(String ficheroRAF, int indice) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "r");

        // Tamaño del fichero en bytes
        long totalBytesRAF = raf.length();
        long posicion = (long) indice * Equipo.tamanoBytes;

        if ((posicion >= 0) && (posicion < totalBytesRAF)) {
            raf.seek(posicion);
            return leerEquipoDeFichero(raf);
        }
        else {
            // No se ha encontrado ningún registro, o la posición es inválida
            return null;
        }
    }

    public static void guardarDatosEnFicheroASC(String ficheroASC, ArrayList<Equipo> listaEquipos) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(ficheroASC));

        for (Equipo equipo : listaEquipos) {
            dos.writeInt(equipo.getNum());
            dos.writeUTF(equipo.getNombre());
            dos.writeUTF(equipo.getPresidente());
            dos.writeInt(equipo.getTelefono());
            dos.writeUTF(equipo.getLocalidad());
        }
    }

    /* Lee el fichero ASC y muestra solamente las entradas cuyo número de equipo esté en el rango
    *  delimitado por numEquipoMin y numEquipoMax (ambos inclusive)
    *  */
    public static void leerDatosDeFicheroASCYMostrarEntradasEnRango(String ficheroASC, int numEquipoMin, int numEquipoMax) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(ficheroASC));

        while (dis.available() > 0) {
            Equipo equipo = new Equipo();

            equipo.setNum(dis.readInt());
            if (!(equipo.getNum() >= numEquipoMin) || !(equipo.getNum() <= numEquipoMax)) {
                // Continúa al siguiente registro si el número del club no está en el rango indicado
                continue;
            }
            else {
                equipo.setNombre(dis.readUTF());
                equipo.setPresidente(dis.readUTF());
                equipo.setTelefono(dis.readInt());
                equipo.setLocalidad(dis.readUTF());

                System.out.println(equipo.toString());
            }
        }
    }
}
