package ut2_modelpc;

public class MiBuffer {

    int contador = 0;

    public synchronized void inc() {
        contador++;
    }

    public synchronized void dec() {
        contador--;
    }

}
