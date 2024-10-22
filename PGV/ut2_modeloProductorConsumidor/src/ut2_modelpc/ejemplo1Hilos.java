package ut2_modelpc;

public class ejemplo1Hilos extends Thread {

    MiBuffer mb;

    public ejemplo1Hilos(MiBuffer mb) {
        this.mb = mb;
    }

    public void run() {
        int n = (int) Math.random() * 20;

        if (n % 2 == 0) {
            mb.inc();
        }
        else {
            mb.dec();
        }

        System.out.println("Hola " + Thread.currentThread().getName() + " y contador vale " + mb.contador);
    }

    public static void main(String[] args) {
        MiBuffer mb = new MiBuffer();

        for (int i = 0; i < 10; i++) {
            Thread hilo = new ejemplo1Hilos(mb);
            hilo.setName("Dam2 hilo " + i);
            hilo.start();
            try {
                Thread.sleep((int) Math.random() * 200);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
