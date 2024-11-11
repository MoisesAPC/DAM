
public class Main {
    public static void main(String[] args) {
            for (int i=1; i<=4; i++) {
                new Thread(new MiHilo(), "hilo" + i).start();


            }
        System.out.println("El hilo principal ha terminado su ejecución");
    }

}

class  MiHilo implements Runnable{
    @Override
    public void run() {
        for (int i= 1; i<=5; i++) {
            System.out.println(Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado su ejecución");
    }
   
}