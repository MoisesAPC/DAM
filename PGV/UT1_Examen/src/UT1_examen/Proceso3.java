package UT1_examen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @note Ésta es la clase que se deberá compilar como un JAR
 *
 *       El JAR debe de aparecer en la siguiente ruta relativa a la raíz del proyecto (véase la variable "ficheroJAR" en "Padre.java"):
 *       "./out/artifacts/Proceso3_jar/UT1_Examen.jar"
 */
public class Proceso3 {
    public static void main(String[] args) throws IOException {
        final String ping_comando = "ping";

        // Leemos la IP desde el stdin
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String ip = reader.readLine();
        // Lanzamos el comando "ping direccion_ip"
        Process ping_proceso = new ProcessBuilder(ping_comando, ip).start();

        // Leemos la salida del proceso "ping_proceso"
        BufferedReader br = new BufferedReader(new InputStreamReader(ping_proceso.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
