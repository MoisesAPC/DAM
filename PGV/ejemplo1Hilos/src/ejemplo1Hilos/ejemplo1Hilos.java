package ejemplo1Hilos;

import java.util.Scanner;

public class ejemplo1Hilos extends Thread {
    MiBuffer mb;

    public ejemplo1Hilos(MiBuffer mb) {
        this.mb = mb;
    }

    public void run() {
        System.out.println("Soy " + Thread.currentThread().getName());
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Incrementamos contador ----");
        mb.incrementar();
        System.out.println("mb.contador: " + mb.contador);

        System.out.println("---- Decrementamos contador ----");
        mb.decrementar();
        System.out.println("mb.contador: " + mb.contador);
    }

    public static void main(String[] args) {

        MiBuffer mb = new MiBuffer();

        for (int i = 0; i < 4; i++) {
            Thread hilo = new ejemplo1Hilos(mb);
            hilo.setName("Hilo " + i);
            hilo.start();
        }
    }
}
