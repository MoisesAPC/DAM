import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
public class Main {

    public static void main(String[] args) {
        //Con esto controlamos que solo un vuelo despegue.
        Semaphore semaforo = new Semaphore(1);
        //Aquí tenemos el string de array posibles para introducir en nuestro hilo
            String[] nAero={"Iberia","Air Berlin","Binter","Ryanair","Vueling","Spanair","Lufthansa","Condor","Swissair","Canaryfly"};
            Random r = new Random();
            //Con este for introducimos los datos de forma automática.
        for (int i = 1; i<100; i++){
            int nRandom = r.nextInt(nAero.length);
            Thread t = new Thread(new PistaDespeje(semaforo,nAero[nRandom]));
            t.start();
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
//Esta será nuestra única clase
class PistaDespeje implements Runnable{
    private static ArrayList<String> arrayListVuelos = new ArrayList<>();

    private final Semaphore semaforo;
    private String aerolinea;

    public PistaDespeje(Semaphore semaforo, String aerolinea) {
        this.semaforo = semaforo;
        this.aerolinea = aerolinea;
    }

    @Override
    public void run() {
        try {

            Random r = new Random();
            //Antes de pedir despegar haremos un número aleatorio entre los parámetros predefinidos.
            Thread.sleep(r.nextInt(3000)+1000);
            System.out.println(aerolinea + " Pide despegar");
            //Con esto controlamos que no haya más de 50 vuelos en cola.
            while (arrayListVuelos.size()>=50){
                System.out.println("La cola ha superado las 50 plazas, tienes que esperar");
                Thread.sleep(2000);
            }
            //Esta es la función para hacer esperar en caso de que una misma aerolínea tenga más de 10 vuelos en cola
            while (contarVuelosMismaAerolinea(aerolinea) >= 10) {
                System.out.println(aerolinea + "Tienes más de 10 vuelos en cola, esperando...");
                    Thread.sleep(3000);
                }
            arrayListVuelos.add(aerolinea);
            System.out.println("El vuelo " + aerolinea + " Está en pista en la posición número " + arrayListVuelos.size());
            semaforo.acquire();
            //Esperamos antes de despegar.
            Thread.sleep(r.nextInt(10000)+1000);
            System.out.println("Hilo "+ aerolinea + "Esta despegando");
            //Una vez despegamos, avisamos a los demás hilos y a parte qutiamos el hueco del array.
            semaforo.release();
            arrayListVuelos.remove(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static int contarVuelosMismaAerolinea(String aerolinea) {
        int contador = 0;
        for (String vuelo : arrayListVuelos) {
            if (vuelo.equals(aerolinea)) {
                contador++;
            }
        }
        return contador;
    }
}



