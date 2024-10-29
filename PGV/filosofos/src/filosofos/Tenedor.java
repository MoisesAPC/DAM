package filosofos;

public class Tenedor {
    private Boolean estaAgarrado;   // Un filósofo está agarrando este tenedor
    private int filosofoID;

    public int getFilosofoID() {
        return filosofoID;
    }

    public void setFilosofoID(int filosofoID) {
        this.filosofoID = filosofoID;
    }

    public Boolean getEstaAgarrado() {
        return estaAgarrado;
    }

    public void setEstaAgarrado(Boolean estaAgarrado) {
        this.estaAgarrado = estaAgarrado;
    }

    public Tenedor() {
        estaAgarrado = false;
        filosofoID = 0;
    }
}
