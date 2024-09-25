package act1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Child {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String comando = sc.nextLine();

        try {
            Runtime rt = Runtime.getRuntime();
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", comando);
            Process process = pb.start();
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
