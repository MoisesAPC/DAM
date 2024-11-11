package ut2_actividadEvaluacion_package;

import java.util.Random;
import java.util.concurrent.Semaphore;

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
        Random random = new Random();
        // Dado que el único recurso compartido es la pista de despegue, ponemos un 1
        Semaphore semaforo = new Semaphore(1);

        // Creamos 100 vuelos de ejemplo. Cada uno se creará cada 500 milisegundos
        for (int i = 0; i < 100; i++) {
            int aerolineaAleatoria = random.nextInt(LINEAS_AEREAS.length);
            String lineaAerea = LINEAS_AEREAS[aerolineaAleatoria];

            Vuelo vuelo = new Vuelo(lineaAerea, semaforo);

            // Ejecutamos todos los hilos de manera concurrente creando una nueva clase Thread y llamando a su
            // método start()
            Thread hilo = new Thread(vuelo);
            hilo.start();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
