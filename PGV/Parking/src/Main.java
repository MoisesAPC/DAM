import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        int nCoches = 80;
        Semaphore parking = new Semaphore(5);
        for (int i=1; i<nCoches; i++  ){
            Thread t = new Thread(new Coches(parking,i));
            t.start();
        }



    }
}
class Coches implements Runnable {
    private final Semaphore parking;
    private final int nCoche;
    public Coches(Semaphore parking, int nCoche) {
        this.parking = parking;
        this.nCoche=nCoche;
    }

    public void run(){
        try {
            System.out.println("El coche  " + nCoche + " Está esperando");
            parking.acquire();
            System.out.println("El coche " + nCoche + " Está APARCADOOOOOO");
            Thread.sleep(8000);
            System.out.println("El coche " + nCoche + " SE HA IDO DEL PARKING");
            parking.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


