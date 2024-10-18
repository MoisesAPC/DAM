package UT1_examen;

import java.io.*;

/**
 * @note Se debe ejecutar el IDE como administrador para poder ejecutar los comandos del sistema.
 *
 * Este examen se hizo con el IntelliJ.
 */
public class Padre {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String nslookup_comando = "nslookup";
        final String nslookup_argumento = "host";

        final String notepad_comando = "notepad.exe";

        final String ficheroJAR = "./out/artifacts/Proceso3_jar/UT1_Examen.jar";

        String ip = "";

        // Apartado b) Ejecutamos de manera concurrente
        Process proceso1 = new ProcessBuilder(nslookup_comando, nslookup_argumento).start();
        Process proceso2 = new ProcessBuilder(notepad_comando).start(); // Apartado d) Ejecutamos "notepad.exe"

        // Apartado c) Ejecutamos "nslookup host" y obtenemos la dirección IP mostrada
        BufferedReader br = new BufferedReader(new InputStreamReader(proceso1.getInputStream()));
        String linea;

        // Buscamos en el stdout del proceso1
        //
        // La IP está contenida en la línea que empieza por "Address". Usando un "split" con el
        // separador ":", obtendremos la dirección IP (la IP está a la derecha del ":")
        //
        // La guardamos en la variable "ip"
        while ((linea = br.readLine()) != null) {
            if (linea.startsWith("Address:")) {
                ip = linea.split(":")[1].trim();
            }
            // Mostramos por pantalla la salida del proceso 1
            System.out.println(linea);
        }

        // Apartado e)
        // Esperamos a que el usuario cierre el proceso 2 (el notepad.exe)
        proceso2.waitFor();

        // Lanzamos el JAR como un nuevo proceso
        Process proceso3 = new ProcessBuilder("java", "-jar", ficheroJAR).start();

        // Le enviamos la IP al proceso3
        OutputStream outputStream = proceso3.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        writer.print(ip);
        writer.flush();

        outputStream.close();
        writer.close();

        // Leemos la salida del JAR
        BufferedReader br2 = new BufferedReader(new InputStreamReader(proceso3.getInputStream()));
        String line;
        while ((line = br2.readLine()) != null) {
            System.out.println(line);
        }
    }
}
