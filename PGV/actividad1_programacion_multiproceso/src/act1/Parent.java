package act1;

import java.io.*;
import java.util.Scanner;

public class Parent {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Qué comando quieres ejecutar?");
        String comando = sc.nextLine();
        System.out.println(comando);

        try {
            Process p = new ProcessBuilder("java", "-jar", "./out/artifacts/actividad1_programacion_multiproceso_jar/actividad1_programacion_multiproceso.jar").start();

            OutputStream osr = p.getOutputStream();
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            osr.write((comando + "\n").getBytes());
            osr.flush();

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            osr.close();
            isr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
