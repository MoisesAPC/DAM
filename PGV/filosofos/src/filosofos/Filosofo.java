package filosofos;

import java.util.ArrayList;

public class Filosofo implements Runnable {
    public final int tenedorIzquierdo = (1 << 0);
    public final int tenedorDerecho   = (1 << 1);
    private Tenedor[] tenedores = new Tenedor[2];

    private int id;
    int tenedoresAgarrados;

    public Filosofo() {
        id = 0;
        tenedoresAgarrados = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenedoresAgarrados() {
        return tenedoresAgarrados;
    }

    public void setTenedoresAgarrados(int tenedor) {
        this.tenedoresAgarrados |= tenedor;
    }

    public void quitarTenedoresAgarrados(int tenedor) {
        this.tenedoresAgarrados &= ~tenedor;
    }

    public int tenedorIzquierdoAgarrado() {
        return (tenedoresAgarrados & tenedorIzquierdo);
    }

    public int tenedorDerechoAgarrado() {
        return (tenedoresAgarrados & tenedorDerecho);
    }

    public void agarrarTenedor() {
        for (int i = 0; i < tenedores.length; i++) {
            if (!tenedores[i].getEstaAgarrado()) {
                setTenedoresAgarrados();
            }
        }
    }

    @Override
    public void run() {

    }
}