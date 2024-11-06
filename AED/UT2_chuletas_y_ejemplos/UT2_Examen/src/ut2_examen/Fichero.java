package ut2_examen;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Clase wrapper para manejar operaciones con ficheros de lectura y escritura
 * *utilizando únicamente Strings*
 */
public class Fichero {
    final static int LECTURA = 0;
    final static int ESCRITURA = 1;

    String rutaFichero = "";
    private BufferedReader br = null;
    private BufferedWriter bw = null;

    public Fichero(int tipo, String rutaFichero_) {
        try {
            rutaFichero = rutaFichero_;

            switch (tipo) {
                case LECTURA:
                    br = new BufferedReader(new FileReader(rutaFichero));
                    break;

                case ESCRITURA:
                    bw = new BufferedWriter(new FileWriter(rutaFichero));
                    break;
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee todas las líneas del fichero como Strings y las inserta en orden
     * en un ArrayList
     */
    public ArrayList<String> leerLineas() {
        ArrayList<String> lineasFichero = new ArrayList<>();

        String linea;
        while ((linea = leerLinea()) != null) {
            lineasFichero.add(linea);
        }

        return lineasFichero;
    }

    public String leerLinea() {
        try {
            String linea = br.readLine();
            return linea;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribirLineas(ArrayList<String> lineas) {
        for (String linea : lineas) {
            escribirLinea(linea);
        }
    }

    public void escribirLinea(String linea) {
        try {
            bw.write(linea);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Dada una línea con el siguiente formato:
     *
     * P001,Suministros Rápidos,Calle Principal 123,555123456,5
     *
     * Separa cada subcadena entre comas en distintas entradas del array
     * @return
     */
    private ArrayList<String> leerEntradasDeTablaSeparadoPorComas(String cadenaCompleta) {
        String[] entradas = cadenaCompleta.split(",");

        return new ArrayList<>(Arrays.asList(entradas));
    }

    public void close() {
        try {
            br.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
