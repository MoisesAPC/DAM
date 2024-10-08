package actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ProcesoHijo {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line.toUpperCase());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
