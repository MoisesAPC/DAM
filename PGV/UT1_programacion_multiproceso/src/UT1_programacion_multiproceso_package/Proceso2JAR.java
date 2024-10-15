package UT1_programacion_multiproceso_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Proceso2JAR {
    public static void main(String[] args) throws IOException {
        //leemos el nombre de la carpeta desde stdin
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String nombreDeCarpeta = reader.readLine();
        File carpeta = new File(nombreDeCarpeta);
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta no existe o no es un directorio.");
            return;
        }

        //añadir ficheros a lista
        List<File> filesList = new ArrayList<>();
        for (File file : carpeta.listFiles()) {
            if (file != null && file.isFile()) {
                filesList.add(file);
            }
        }

        if (!filesList.isEmpty()) {
            for (File file : filesList) {
                /**
                 * Creamos un proceso por fichero, cada uno ejecutará "proceso3.bat"
                 */
                    ProcessBuilder processBuilder3 = new ProcessBuilder("cmd.exe", "/c", "proceso3.bat");
                /**
                 * TRUCO: Ponemos el directorio de la carpeta como el
                 * working directory para que nos funcionen las rutas relativas
                 */
                processBuilder3.directory(carpeta);
                Process proceso3 = processBuilder3.start();
            }
        }
    }
}
