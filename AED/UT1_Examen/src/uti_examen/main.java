package uti_examen;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        /**
         * Ejercicio 1
         */

        // Directorios de cada fichero
        final String ficheroSecuencialASC = "ficheros/ficheroSecuencialASC.bin";
        final String ficheroRAF = "ficheros/ficheroRAF.bin";
        final String ficheroSecuencialTexto = "ficheros/ficheroSecuencialTexto.txt";

        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
        Scanner teclado = new Scanner(System.in);

        // Solicitamos las películas al usuario
        System.out.print("Introduce el numero de peliculas a insertar: ");
        int numPeliculas = teclado.nextInt();

        // Pedimos al usuario que inserte "numPeliculas" peliculas
        for (int i = 0; i < numPeliculas; i++) {
            System.out.println("--- Pelicula " + (i + 1) + " ---");
            listaPeliculas.add(Pelicula.crearPeliculaAPartirDeDatosDeTeclado());
        }

        // Fichero secuencial binario de salida (ASC)
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(ficheroSecuencialASC));

        // Recorre la lista, y guarda cada entrada del array en el fichero
        for (int j = 0; j < listaPeliculas.size(); j++) {
            listaPeliculas.get(j).guardarPeliculaEnFicheroSecuencial(dos);
        }

        dos.close();

        /**
         * Ejercicio 2
         */
        Pelicula.leerFicheroASCYVolcarEnRAF(ficheroSecuencialASC, ficheroRAF);

        /**
         * Ejercicio 3
         */
        Pelicula.actualizarPrecioPeliculas(ficheroRAF);
        Pelicula.mostrarRAF(ficheroRAF);

        /**
         * Ejercicio 4
         */
        Pelicula.volcarRAFEnFicheroTextoSecuencial(ficheroRAF, ficheroSecuencialTexto);
    }
}
