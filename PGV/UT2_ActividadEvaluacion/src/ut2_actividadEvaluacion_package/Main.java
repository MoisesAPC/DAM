package ut2_actividadEvaluacion_package;

import java.util.Random;

public class Main {
    private static final String[] LINEAS_AEREAS = {
            "Iberia",
            "Air Berlin",
            "Binter",
            "Ryanair",
            "Vueling",
            "Spanair",
            "Lufthansa",
            "Condor",
            "SwissAir",
            "Canaryfly"
    };

    public static void main(String[] args) {
        TorreDeControl torreDeControl = new TorreDeControl();
        Random random = new Random();

        while (true) {
            int aerolineaAleatoria = random.nextInt(LINEAS_AEREAS.length);
            String lineaAerea = LINEAS_AEREAS[aerolineaAleatoria];
            Vuelo vuelo = new Vuelo(torreDeControl, lineaAerea);
            vuelo.start();

            try {
                Thread.sleep(random.nextInt(3000) + 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
