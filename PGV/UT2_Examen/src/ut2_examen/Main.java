package ut2_examen;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    private static String pedirStringDeTeclado() {
        Scanner teclado = new Scanner(System.in);

        String miString = teclado.nextLine();

        return miString;
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<String> nombreJugadoresArray = new ArrayList<>();

        System.out.print("Introduce el número de jugadores: ");
        int numeroJugadores = teclado.nextInt();

        while (numeroJugadores < 2) {
            System.out.println("ERROR: Debe haber un mínimo de 2 jugadores");
            System.out.print("Introduce el número de jugadores: ");
            numeroJugadores = teclado.nextInt();
        }

        for (int i = 0; i < numeroJugadores; i++) {
            System.out.print("¿Cómo se llama el jugador?: ");
            String nombreJugador = pedirStringDeTeclado();
            nombreJugadoresArray.add(nombreJugador);
        }

        Juego juego = new Juego(numeroJugadores);

        Semaphore semaforo = new Semaphore(1);

        for (int i = 0; i < numeroJugadores; i++) {
            Jugador jugador = new Jugador(nombreJugadoresArray.get(i), semaforo, juego);

            Thread hilo = new Thread(jugador);
            hilo.start();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        juego.reproducirMusica();

        while (!juego.haTerminado()) {
            juego.suenaMusica();
        }

        Jugador ganador = juego.sillasOcupadas.get(0);

        System.out.println("Acabó el juego y el ganador es: " + ganador.getNombre());
        ganador.terminado = true;
    }
}
