package ut2_actividadEvaluacion_package;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Vuelo implements Runnable {
    private final String aerolinea;
    private final Semaphore semaforo;
    // Recurso compartido por todos los hilos (de ahí que sea "static")
    public static ArrayList<String> colaAerolineas = new ArrayList<>();

    public void anadirVueloACola(String aerolinea) {
        colaAerolineas.add(aerolinea);
    }

    public void quitarVueloDeAerolinea(String aerolinea) {
        colaAerolineas.remove(aerolinea);
    }

    public void quitarPrimerVueloDeAerolinea() {
        colaAerolineas.remove(0);
    }

    public int obtenerPosicionEnColaDeVuelo(String aerolinea) {
        return colaAerolineas.indexOf(aerolinea);
    }

    public Boolean colaLlena() {
        return colaAerolineas.size() >= 50;
    }

    public int obtenerNumVuelosDeMismaAerolineaEsperando() {
        int numVuelos = 0;

        for (String vuelo : colaAerolineas) {
            if (vuelo.equals(aerolinea)) {
                numVuelos++;
            }
        }

        return numVuelos;
    }

    Vuelo(String nombre, Semaphore semaforo) {
        this.aerolinea = nombre;
        this.semaforo = semaforo;
    }

    public void despegar() {
        System.out.println(aerolinea + " ya despegó");
    }

    @Override
    public void run() {
        try {
            Random random = new Random();

            System.out.println(aerolinea + " pide despegar");
            Thread.sleep(random.nextInt(5000) + 1000);

            // Si la cola de aviones está llena, esperamos, sin acceder a la cola
            while (colaLlena()) {
                System.out.println("La cola ya tiene 50 plazas ocupadas. Esperaremos hasta que se desocupe la pista");
                Thread.sleep(random.nextInt(3000) + 1000);
            }

            // Si hay más de 10 vuelos de la misma aerolínea, esperamos, sin acceder a la cola
            while (obtenerNumVuelosDeMismaAerolineaEsperando() >= 10) {
                System.out.println("Ya hay 10 vuelos de esta misma aerolínea esparando. Esperaremos hasta que los que queden puedan despegar");
                Thread.sleep(random.nextInt(3000) + 1000);
            }

            anadirVueloACola(this.aerolinea);
            System.out.println("El vuelo " + aerolinea + " se encuentra en pista en la posición " + obtenerPosicionEnColaDeVuelo(aerolinea));

            // Zona crítica (acceso a recurso compartido): El avión ocupa la pista y despega.
            // Al despegar, el siguiente vuelo pasa a ocupar la pista
            semaforo.acquire();
            Thread.sleep(random.nextInt(5000) + 1000);
            despegar();
            semaforo.release();
            quitarPrimerVueloDeAerolinea();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
