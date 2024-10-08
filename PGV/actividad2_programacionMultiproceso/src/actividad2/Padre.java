package actividad2;

import java.io.*;

public class Padre {
    public static void main(String[] args) {
        final String dirCancion = "C:\\Users\\Lenovo\\Documents\\Moi\\Ixataka.wav";
        final String dirReproductor = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "actividad2.ProcesoHijo");
            Process procesoHijo = pb.start();

            ProcessBuilder pbReproductor = new ProcessBuilder(dirReproductor, dirCancion);
            Process procesoReproductor = pbReproductor.start();

            OutputStream out = procesoHijo.getOutputStream();
            String mensaje =
                    "Mensaje de prueba numero 1\n" +
                    "Mensaje de prueba numero 2\n";
            out.write(mensaje.getBytes());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(procesoHijo.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Hijo: " + line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
