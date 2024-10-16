import java.io.*;

public class ListarServiciosSvchost {

    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "tasklist /SVC /FO LIST");
            Process proceso = pb.start();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            String pid = "";
            String servicios = "";
            
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Image Name:") && linea.contains("svchost.exe")) {
                    if (!pid.isEmpty()) {
                        System.out.println("PID: " + pid + ", Servicios: " + servicios);
                    }
                    pid = "";
                    servicios = "";
                } else if (linea.startsWith("PID:")) {
                    pid = linea.split(":")[1].trim();
                } else if (linea.startsWith("Services:")) {
                    servicios = linea.split(":")[1].trim();
                }
            }
            
            if (!pid.isEmpty()) {
                System.out.println("PID: " + pid + ", Servicios: " + servicios);
            }
            
            proceso.waitFor();
            
            // Ejecutar sc qc para un servicio específico
            String servicio = servicios.split(",")[0].trim();
            ProcessBuilder pb2 = new ProcessBuilder("cmd.exe", "/c", "sc qc " + servicio);
            Process proceso2 = pb2.start();
            
            BufferedReader br2 = new BufferedReader(new InputStreamReader(proceso2.getInputStream()));
            while ((linea = br2.readLine()) != null) {
                System.out.println(linea);
            }
            
            proceso2.waitFor();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}