package ut2_actividadEvaluacion_package;

class Vuelo extends Thread {
    public TorreDeControl getTorreDeControl() {
        return torreDeControl;
    }

    public void setTorreDeControl(TorreDeControl torreDeControl) {
        this.torreDeControl = torreDeControl;
    }

    public String getNombre() {
        return this.getName();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private TorreDeControl torreDeControl;
    private String nombre;

    public Vuelo(TorreDeControl torreDeControl, String nombre) {
        this.torreDeControl = torreDeControl;
        this.nombre = nombre;
        this.setName(nombre);
    }

    @Override
    public void run() {
        try {
            torreDeControl.solicitarDespegue(getNombre());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
