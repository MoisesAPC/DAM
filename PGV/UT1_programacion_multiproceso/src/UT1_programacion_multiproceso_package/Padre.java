package UT1_programacion_multiproceso_package;

import java.io.*;
import java.util.Scanner;

public class Padre {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Nombre de la carpeta creada: ");
        String nombreCarpeta = teclado.nextLine();
        /**
         * Ejercicio a) y b)
         *
         * @note Dado que msconfig.exe requiere permisos especiales, hay que lanzar
         * el IDE como administrador para que esta parte functione.
         */
        //ProcessBuilder procesoBuilder1 = new ProcessBuilder("./ficheros/msconfig.bat");
        //Process proceso1 = procesoBuilder1.start();
        ProcessBuilder procesoBuilder1 = new ProcessBuilder("msconfig.exe");
        ProcessBuilder procesoBuilder2 = new ProcessBuilder("java", "-jar", "./out/artifacts/UT1_programacion_multiproceso_jar/UT1_programacion_multiproceso.jar");
        Process proceso1 = procesoBuilder1.start();
        Process proceso2 = procesoBuilder2.start();

        try (OutputStream outputStream = proceso2.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream)) {
            writer.println(nombreCarpeta);
            writer.flush();
        }

        // Luego, leemos la salida del JAR
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso2.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }


        /**
         * Ejercicio e)
         */
        proceso2.waitFor();

        ProcessBuilder procesoBuilder24 = new ProcessBuilder("cmd.exe", "/c", "dir", nombreCarpeta);
        Process proceso4 = procesoBuilder24.start();

        //leemos la salida del comando DIR (es decir, la salida del proceso 4)
        InputStream inputStreamDir = proceso4.getInputStream();
        Scanner scanner = new Scanner(inputStreamDir);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        /**
         * Ejercicio f)
         */
        if (proceso1.isAlive()) {
            System.out.println("msconfig se abrió correctamente");
        }
        proceso1.destroy();
        //proceso1.destroy();
    }
}