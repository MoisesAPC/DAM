package ut2_examen;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Jugador implements Runnable {
    private String nombre;
    private Semaphore semaforo;
    public Boolean terminado;
    public Boolean sentado;

    private Juego juego;

    public Jugador(String nombre, Semaphore semaforo, Juego juego) {
        this.nombre = nombre;
        this.semaforo = semaforo;
        this.juego = juego;
        terminado = false;
        sentado = false;
    }

    public void sentarse() {
        Random random = new Random();

        if (juego.todasLasSillasEstanOcupadas() == false) {
            juego.sillasOcupadas.add(this);
            System.out.println("El jugador " + nombre + " se sentó");
            sentado = true;
        }
        else {
            jugadorPierde();
        }

        try {
            Thread.sleep(random.nextInt(1000) + 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void jugadorPierde() {
        System.out.println("El jugador " + nombre + " ha perdido. Nueva ronda");
        // Empieza una nueva ronda
        juego.quitarSilla();
        juego.reproducirMusica();
        terminado = true;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();

            Thread.sleep(random.nextInt(3000) + 1000);
            while (!terminado) {
                if (juego.musicaHaParado && !sentado) {
                    semaforo.acquire();
                    sentarse();
                    semaforo.release();
                }

                if (juego.musicaHaParado == false) {
                    sentado = false;
                }
            }
            Thread.sleep(random.nextInt(3000) + 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
