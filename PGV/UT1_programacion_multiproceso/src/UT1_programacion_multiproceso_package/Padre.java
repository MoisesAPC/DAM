package UT1_programacion_multiproceso_package;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Padre {
    public static void main(String[] args) throws InterruptedException, IOException {
        /**
         * Ejercicio a) y b)
         *
         * @note Dado que msconfig.exe requiere permisos especiales, ha que lanzar
         * el IDE como administrador para que esta parte functione.
         */
        ProcessBuilder builder1MsconfigDirecto = new ProcessBuilder("msconfig.exe");
        Process proceso1MsConfigDirecto = builder1MsconfigDirecto.start();
        //ProcessBuilder builder1MsconfigBAT = new ProcessBuilder("cmd.exe", "/c", "msconfig.bat");
        //Process procesoMsConfigBAT = builder1MsconfigBAT.start();

        /**
         * Ejercicio c) y d)
         */
        Scanner teclado = new Scanner(System.in);
        System.out.print("Nombre de la carpeta creada: ");
        String nombreCarpeta = teclado.nextLine();

        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "./out/artifacts/UT1_programacion_multiproceso_jar/UT1_programacion_multiproceso.jar");
        Process proceso2 = builder.start();

        //enviamos el nombre de la carpeta al JAR
        OutputStream outputStream = proceso2.getOutputStream();
        outputStream.write((nombreCarpeta + "\n").getBytes());
        outputStream.flush();

        /**
         * Ejercicio e)
         */
        proceso2.waitFor();

        ProcessBuilder builder4 = new ProcessBuilder("cmd.exe", "/c", "dir", nombreCarpeta);
        Process proceso4 = builder4.start();

        //leemos la salida del comando DIR (es decir, la salida del proceso 4)
        InputStream inputStream = proceso4.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        /**
         * Ejercicio f)
         */
        proceso1MsConfigDirecto.destroy();
        //procesoMsConfigBAT.destroy();
    }
}