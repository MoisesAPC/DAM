package ut2_actividadCreacionYGestionDeHilos;

import java.util.ArrayList;

public class MiHilo implements Runnable {
    public static void main(String[] args) {
        // Creamos una lista de hilos para que después les podamos hacer un "join" para
        // terminarlos y unirlos a la ejecución principal
        ArrayList<Thread> listaHilos = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Thread hilo = new Thread(new MiHilo(), "Hilo " + i);
            listaHilos.add(hilo);
            hilo.start();
        }

        // Unimos todos los hilos en uno. Al hacerlo, y tener solamente 1 solo hilo,
        // terminamos con la ejecución del hilo principal (y la ejecución del programa)
        for (Thread hilo : listaHilos) {
            try {
                hilo.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("El hilo principal ha finalizado");
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Nombre del hilo: " + Thread.currentThread().getName());
        }

        // Aquí finaliza cada hilo
        System.out.println(Thread.currentThread().getName() + " ha finalizado.");
    }
}
