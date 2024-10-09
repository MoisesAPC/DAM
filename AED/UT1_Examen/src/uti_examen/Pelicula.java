package uti_examen;

import java.io.*;
import java.util.Scanner;

public class Pelicula {
    private double Precio;
    private String Nombre;
    private int cod_peli;

    // Tamaño del nombre en caracteres
    public static final int NombreCaracteres = 30;
    // Tamaño del nombre en bytes
    public static final int NombreBYTES = NombreCaracteres * Character.BYTES;
    // Tamaño del registro "Pelicula" en bytes
    public static final int BYTES = Integer.BYTES + NombreBYTES + Double.BYTES;

    public Pelicula(double precio, String nombre, int cod_peli) {
        Precio = precio;
        Nombre = nombre;
        this.cod_peli = cod_peli;
    }

    public Pelicula() {}

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCod_peli() {
        return cod_peli;
    }

    public void setCod_peli(int cod_peli) {
        this.cod_peli = cod_peli;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "cod_peli=" + cod_peli +
                ", Nombre='" + Nombre + '\'' +
                ", Precio=" + Precio +
                '}';
    }

    /**
     * Guarda el contenido de una película en un fichero ASC (usando datos primitivos de Java)
     *
     * @param fichero
     * @throws IOException
     */
    public void guardarPeliculaEnFicheroSecuencial(DataOutputStream fichero) throws IOException {
        fichero.writeDouble(getPrecio());
        fichero.writeUTF(getNombre());
        fichero.writeInt(getCod_peli());
    }

    /**
     * Método de ayuda para corregir un pequeño error en donde al pedir una String
     * por teclado hacía que no se leyese la String correctamente
     *
     * @return
     */
    private static String pedirStringDeTeclado() {
        Scanner teclado = new Scanner(System.in);

        String miString = teclado.nextLine();

        return miString;
    }

    /**
     * Pide al usuario los datos de un palícula a introducir en fichero, y retorna un objeto "Pelicula"
     * con dichos datos
     *
     * @return
     */
    public static Pelicula crearPeliculaAPartirDeDatosDeTeclado() {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce el CODIGO de la pelicula: ");
        int codigo = teclado.nextInt();

        System.out.print("Introduce el NOMBRE de la pelicula: ");
        String nombre = pedirStringDeTeclado();

        System.out.print("Introduce el PRECIO de la pelicula: ");
        double precio = teclado.nextDouble();

        System.out.println();

        return new Pelicula(precio, nombre, codigo);
    }

    /**
     * Método de ayuda que escribe una String the "longitud" bytes en un fichero de acceso aleatorio
     *
     * @param raf
     * @param string
     * @param longitud
     * @throws IOException
     */
    private static void escribirStringEnFichero(RandomAccessFile raf, String string, int longitud) throws IOException {
        // Como cada caracter ocupa 2 bytes, realizamos la división para que
        // la String se guarde con el tamaño correcto
        longitud /= Character.BYTES;
        StringBuilder sb = new StringBuilder(string);

        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }

    /**
     * Escribe los contenidos de un registro entero de "Pelicula" en un fichero de acceso aleatorio
     *
     * @param raf
     * @param precio
     * @param nombre
     * @param cod_peli
     * @throws IOException
     */
    public static void escribirPeliculaEnFicheroAccesoAleatorio(RandomAccessFile raf, double precio, String nombre, int cod_peli) throws IOException {
        raf.writeDouble(precio);
        escribirStringEnFichero(raf, nombre, Pelicula.NombreBYTES);
        raf.writeInt(cod_peli);
    }

    /**
     * Lee un fichero secuencial binario como un ASC (es decir, leyendo los datos mediante tipos de datos primitivos de Java),
     * y los vuelca en un fichero de acceso aleatorio
     *
     * @param ficheroASCEntrada
     * @param ficheroRAFSalida
     * @throws IOException
     */
    public static void leerFicheroASCYVolcarEnRAF(String ficheroASCEntrada, String ficheroRAFSalida) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(ficheroASCEntrada));
        RandomAccessFile raf = new RandomAccessFile(ficheroRAFSalida, "rw");

        while (dis.available() > 0) {
            Pelicula pelicula = new Pelicula();

            double precio = dis.readDouble();
            String nombre = dis.readUTF();
            int cod_peli = dis.readInt();

            escribirPeliculaEnFicheroAccesoAleatorio(raf, precio, nombre, cod_peli);
        }

        dis.close();
        raf.close();
    }

    /**
     * Lee un registro "Pelicula" de un fichero de acceso aleatorio y lo retorna.
     *
     * Se asume que el filePointer ya apunta al registro que se quiera leer antes de llamar a esta función.
     *
     * @param raf
     * @return
     * @throws IOException
     */
    public static Pelicula leerPeliculaDeFicheroRAF(RandomAccessFile raf) throws IOException {
        double precio = raf.readDouble();
        String nombre = leerStringDeFichero(raf, Pelicula.NombreBYTES);
        int cod_peli = raf.readInt();

        return new Pelicula(precio, nombre, cod_peli);
    }

    /**
     * Método de ayuda que lee una String de tamaño "longitud" bytes de un fichero de acceso aleatorio
     *
     * @param raf
     * @param longitud
     * @return
     * @throws IOException
     */
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
     * Dado un fichero de acceso aleatorio, suma el 5% al precio de aquellas películas cuyo
     * código de película sea múltiplo de 5
     *
     * @param ficheroRAF
     * @throws IOException
     */
    public static void actualizarPrecioPeliculas(String ficheroRAF) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "rw");

        // Tamaño del fichero en bytes
        long totalBytesRAF = raf.length();
        long posicionRegistroActual = 0;

        // Nos aseguramos de empezar la búsqueda desde el principio del fichero
        raf.seek(0);

        while (raf.getFilePointer() < totalBytesRAF) {
            posicionRegistroActual = raf.getFilePointer();

            /**
             * @note
             *
             * En el fichero RAF, el orden de guardado de las variables de cada registro es:
             * salario, nombre, cod_peli
             *
             * Por este motivo, para poder cargar "cod_peli", sumamos el contenido de "posicionRegistroActual"
             * (el cual apunta a "salario") con el suficiente desplazamiento para desplazar al puntero hacia "cod_peli"
             */
            posicionRegistroActual += (Double.BYTES + Pelicula.NombreBYTES);
            raf.seek(posicionRegistroActual);

            // Solamente cargamos el código de la película y lo comprobamos con el que queremos cargar
            int codPeliActual = raf.readInt();

            // Comprobamos que el código sea múltiplo de 5
            if ((codPeliActual % 5) == 0) {
                /**
                 * @note
                 *
                 * Respecto a la @note de la línea 225:
                 * Aquí restamos la cantidad de bytes para que ahora el fichero vuelva a apuntar al principio del registro,
                 * y que así pueda cargar el registro correctamente
                 */
                posicionRegistroActual -= (Double.BYTES + Pelicula.NombreBYTES);

                /**
                 * Si coinciden, entonces ahora sí cargamos el registro entero para su modificación
                 *
                 * @note Como ya hemos hecho un `raf.readInt();` de la línea 237,
                 *       el puntero está desplazado +4 (el tamaño de un Integer),
                 *
                 *       Volvemos a poner el puntero al principio del registro en el fichero para su correcta lectura
                 */
                raf.seek(posicionRegistroActual);

                Pelicula pelicula = leerPeliculaDeFicheroRAF(raf);
                // Aumentamos el precio en un 5%
                double nuevoPrecio = pelicula.getPrecio() + (pelicula.getPrecio() * 0.5);
                pelicula.setPrecio(nuevoPrecio);

                // `leerPeliculaDeFicheroRAF` hace que el filePointer avance,
                // por lo que volvemos a hacer un seek para corregirlo
                raf.seek(posicionRegistroActual);
                escribirPeliculaEnFicheroAccesoAleatorio(raf, pelicula.getPrecio(), pelicula.getNombre(), pelicula.getCod_peli());
                return;
            }
        }

        raf.close();
    }

    /**
     * Lee los contenidos de un fichero RAF de Películas y muestra el contenido de cada registro
     *
     * @param ficheroRAF
     * @throws IOException
     */
    public static void mostrarRAF(String ficheroRAF) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "r");

        // Tamaño del fichero en bytes
        long totalBytesRAF = raf.length();
        long posicionRegistroActual = 0;

        // Nos aseguramos de empezar la búsqueda desde el principio del fichero
        raf.seek(0);

        while (raf.getFilePointer() < totalBytesRAF) {
            Pelicula pelicula = leerPeliculaDeFicheroRAF(raf);
            System.out.println(pelicula.toString());
        }

        raf.close();
    }

    /**
     * Vuelca los contenidos de un fichero de acceso aleatorio en un fichero de texto secuencial
     *
     * @param ficheroRAF
     * @param ficheroSecuencialTexto
     * @throws IOException
     */
    public static void volcarRAFEnFicheroTextoSecuencial(String ficheroRAF, String ficheroSecuencialTexto) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "r");
        BufferedWriter bufout =new BufferedWriter(new FileWriter(ficheroSecuencialTexto));

        // Tamaño del fichero en bytes
        long totalBytesRAF = raf.length();
        long posicionRegistroActual = 0;

        // Nos aseguramos de empezar la búsqueda desde el principio del fichero
        raf.seek(0);

        /**
         * @note
         *
         * Al realizar las lecturas de los registros en "leerPeliculaDeFicheroRAF()"
         * el filePointer avanza automáticamente, por lo quer no hace falta avanzar el puntero aquí
         */
        while (raf.getFilePointer() < totalBytesRAF) {
            Pelicula pelicula = leerPeliculaDeFicheroRAF(raf);
            pelicula.escribirEntradaEnFicheroTextoSecuencial(bufout);
        }

        raf.close();
        bufout.close();
    }

    /**
     * Escribe una entrada de "Pelicula" en un fichero de texto secuencial, utilizando un separador
     * por cada variable (en este caso, un "&")
     *
     * @param bufout
     * @throws IOException
     */
    private void escribirEntradaEnFicheroTextoSecuencial(BufferedWriter bufout) throws IOException {
        final String separador = "&";
        final String entradaFicheroTexto =
                Double.toString(Precio) + separador
                + Nombre + separador
                + Integer.toString(cod_peli);

        bufout.write(entradaFicheroTexto);
        bufout.newLine();
    }
}
