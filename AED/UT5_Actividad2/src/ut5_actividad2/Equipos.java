package ut5_actividad2;

public class Equipos {
    public Equipos() {}

    public Equipos(String nombre, String categoria, int grupo, String sede, String presidente, int puntos) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.grupo = grupo;
        this.sede = sede;
        this.presidente = presidente;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    private String nombre;
    private String categoria;
    private int grupo;
    private String sede;
    private String presidente;
    private int puntos;

    @Override
    public String toString() {
        return "Equipos{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", grupo=" + grupo +
                ", sede='" + sede + '\'' +
                ", presidente='" + presidente + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
