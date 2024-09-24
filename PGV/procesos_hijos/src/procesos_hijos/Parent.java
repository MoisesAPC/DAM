package procesos_hijos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Parent {
    public static void main(String[] args) {
        try {
            Process p = new ProcessBuilder("java", "-jar", "./procesos_hijos.jar").start();
            OutputStreamWriter osr = new OutputStreamWriter(p.getOutputStream());
            String st = "Hola, soy el padre";
            osr.write(st);
            osr.flush();

            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            osr.close();
            isr.close();
        }
        catch (IOException e) {
            System.out.println("Ocurrió una excepción");
        }
    }
}
