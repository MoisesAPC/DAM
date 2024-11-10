package ut2_actividadEvaluacion_package;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class TorreDeControl {

    private Semaphore semaforo = new Semaphore(1, true);
    private ArrayList<String> colaDespegue = new ArrayList<>();

    private static final int NUM_VUELOS_EN_COLA = 50;

    private void mostrarColaEntera() {
        for (int i = 0; i < colaDespegue.size(); i++) {
            System.out.println("El vuelo " + colaDespegue.get(i) + " se encuentra en la posición " + (i + 1));
        }
    }

    private void mostrarPosicionVuelo(String vuelo) {
        int posicion = colaDespegue.indexOf(vuelo) + 1;

        if (posicion > 0) {
            System.out.println("El vuelo " + vuelo + " se encuentra en la posición " + posicion);
        }
    }

    public synchronized void solicitarDespegue(String vuelo) throws InterruptedException {
        if (colaDespegue.size() < NUM_VUELOS_EN_COLA) {
            colaDespegue.add(vuelo);
            System.out.println("\n" + vuelo + " pide despegar");
            mostrarPosicionVuelo(vuelo);
        }
        else {
            System.out.println(vuelo + " no pudo entrar en la cola porque está llena");
            return;
        }

        while (true) {
            if (colaDespegue.get(0).equals(vuelo)) {
                semaforo.acquire();
                despegar(vuelo);
                semaforo.release();
                break;
            }

            Thread.sleep(3000);
        }
    }

    private synchronized void despegar(String vuelo) {
        System.out.println(vuelo + " ya despegó");
        colaDespegue.remove(vuelo);
    }
}
