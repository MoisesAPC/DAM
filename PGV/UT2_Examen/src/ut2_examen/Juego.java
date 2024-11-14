package ut2_examen;

import java.util.ArrayList;
import java.util.Random;

public class Juego {
    // Cuenta el tiempo en el que la música suena. Cuando esté a 0, la música ha parado.
    private int tiempoMusicaSonando;
    public static Boolean musicaHaParado = false;
    private Random random;
    public static ArrayList<Jugador> sillasOcupadas;
    private int numJugadores;

    public Juego(int numJugadores) {
        this.numJugadores = numJugadores;

        sillasOcupadas = new ArrayList<>(this.numJugadores);
    }

    public void suenaMusica() {
        if (musicaHaParado) {
            return;
        }

        tiempoMusicaSonando--;

        if (tiempoMusicaSonando <= 0) {
            musicaHaParado = true;
        }
    }

    public void reproducirMusica() {
        random = new Random();

        musicaHaParado = false;
        tiempoMusicaSonando = random.nextInt(2000);

        System.out.println();
        System.out.println("**---------** Música.....");
        System.out.println();
    }

    public void quitarSilla() {
        if (numJugadores > 1) {
            this.numJugadores -= 1;
            if (numJugadores != 1) {
                sillasOcupadas = new ArrayList<>(this.numJugadores);
            }
        }
    }

    public Boolean todasLasSillasEstanOcupadas() {
        int numSillasOcupadas = 0;

        for (int i = 0; i < sillasOcupadas.size(); i++) {
            if (sillasOcupadas.get(i) != null) {
                numSillasOcupadas++;
            }
        }

        return numSillasOcupadas == numJugadores - 1;
    }

    public Boolean haTerminado() {
        return (musicaHaParado) && (numJugadores == 1);
    }
}
