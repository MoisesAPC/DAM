
public class Main {
    public static void main(String[] args) {
        Hilos tarea1 = new Hilos();
        Hilos tarea2 = new Hilos();
        Hilos tarea3 = new Hilos();
        tarea1.setName("Tarea1");
        tarea2.setName("Tarea2");
        tarea3.setName("Tarea3");
        tarea1.setPriority(Thread.MIN_PRIORITY);
        tarea2.setPriority(Thread.NORM_PRIORITY);
        tarea3.setPriority(Thread.MAX_PRIORITY);
        tarea1.start();
        tarea2.start();
        tarea3.start();

        try{
            tarea1.join();
            tarea2.join();
            tarea3.join();
            System.out.println("Todas han acabado");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Todas las tareas han terminado");
    }
}
class Hilos extends Thread  {
    public void run(){
      for (int i= 0; i<10; i++){

          System.out.println("El nombre del hilo es " +Thread.currentThread().getName() + " La propiedad es " + Thread.currentThread().getPriority());
          if(i==4){
              if(Thread.currentThread().getPriority() == Thread.MAX_PRIORITY){
                  Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
              }
              else if(Thread.currentThread().getPriority() == Thread.MIN_PRIORITY){
                  Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
              }
              
          }
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      
    }
}