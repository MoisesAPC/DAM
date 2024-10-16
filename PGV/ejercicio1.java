import java.io.*;
import java.util.concurrent.*;

public class ProcesoConcurrente {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "ProcesoHijo");
                Process proceso = pb.start();
                
                OutputStream os = proceso.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.println("Hola mundo");
                pw.flush();
                
                BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("Respuesta del hijo: " + linea);
                }
                
                proceso.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("wmplayer.exe", "C:\\ruta\\a\\cancion.mp3");
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }
}

class ProcesoHijo {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String linea = br.readLine();
        System.out.println(linea.toUpperCase());
    }
}